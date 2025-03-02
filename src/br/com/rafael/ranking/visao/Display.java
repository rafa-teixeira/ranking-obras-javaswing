package br.com.rafael.ranking.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import br.com.rafael.ranking.modelo.Piece;
import br.com.rafael.ranking.modelo.Ranking;
import br.com.rafael.ranking.modelo.RankingObserver;
import br.com.rafael.ranking.modelo.ToScore;

@SuppressWarnings("serial")
public class Display extends JPanel implements RankingObserver {
	
	private JLabel piece1Label;
	private JLabel piece2Label;
	private JTextArea rankingArea;
	private JProgressBar progressVote;
	
	private Ranking ranking;
	
	private Piece currentPiece1;
	private Piece currentPiece2;
	
	public Piece getCurrentPiece1() {
		return currentPiece1;
	}

	public Piece getCurrentPiece2() {
		return currentPiece2;
	}

	public Display(Ranking ranking) {
		this.ranking = ranking;
		initializeComponents();
		arrangeLayout();
		updateHomeView();
	}
	
	private void initializeComponents() {
	    piece1Label = new JLabel("", SwingConstants.CENTER);
	    piece2Label = new JLabel("", SwingConstants.CENTER);
	    
	    rankingArea = new JTextArea(5, 10);
	    rankingArea.setEditable(false);
	    rankingArea.setLineWrap(true);
	    rankingArea.setWrapStyleWord(true);
	    rankingArea.setFont(new Font("Arial", Font.BOLD, 14));

	    JScrollPane rankingScrollPane = new JScrollPane(rankingArea);
	    rankingScrollPane.setPreferredSize(new Dimension(600, 400));
	    rankingScrollPane.setMinimumSize(new Dimension(500, 300));
	    rankingScrollPane.setMaximumSize(new Dimension(700, 500));

	    progressVote = new JProgressBar(0, 100);
	    progressVote.setStringPainted(true);
	    progressVote.setFont(new Font("Arial", Font.PLAIN, 14));

	    Font labelFont = new Font("SansSerif", Font.BOLD, 20);
	    piece1Label.setFont(labelFont);
	    piece2Label.setFont(labelFont);

	    add(rankingScrollPane, BorderLayout.CENTER);
	}
	
	private void arrangeLayout() {
		setLayout(new BorderLayout());
		
		JPanel piecesPanel = new JPanel(new GridLayout(2, 1));
		piecesPanel.add(piece1Label);
		piecesPanel.add(piece2Label);
		add(piecesPanel, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane(rankingArea);
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel progressPanel = new JPanel();
		progressPanel.add(progressVote);
		add(progressPanel, BorderLayout.NORTH);
	}
	
	private void updateHomeView() {
		if(!ranking.getCombinationsLeft().isEmpty()) {
			nextMatch();
		}
		
		updateDisplayRanking();
		updateProgress(0);
		System.out.println("Combinações restantes: \n" + ranking.getCombinationsLeft() + "\n");
	}

	public void updatePieces(Piece piece1, Piece piece2) {
	    currentPiece1 = piece1;
	    currentPiece2 = piece2;
	    
	    piece1Label.setText("1. " + piece1.getTitle());
	    piece2Label.setText("2. " + piece2.getTitle());
	}
	
	public void updateDisplayRanking() {
		String rankingText = ranking.getRanking().stream()
			.map(piece -> piece.getTitle() + ": " + piece.getScore() + " votos")
			.collect(Collectors.joining("\n", "Ranking:\n", ""));
		rankingArea.setText(rankingText);
	}
	
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public void updateProgress(int progress) {
		progressVote.setValue(progress);
		progressVote.setString(progress + "% concluído");
	}

	@Override
	public void updateRanking(ToScore eventScore) {
		
	    updateDisplayRanking();
	    updateProgress(calculateProgress());

	        if (eventScore == ToScore.FINAL_RANKING) {  
	            
	            showFinalRanking();
	            ranking.resetRanking();
	            return;
	        }
	        
	        if (ranking.getCombinationsLeft().isEmpty() && !ranking.isFinalStage()) {
	        	ranking.startNewVotingCycle();
	            nextMatch();
	        } else if(!ranking.getCombinationsLeft().isEmpty()) {
	        nextMatch();
	    }
	}

	private int calculateProgress() {
		int totalVotes = ranking.getTotalCombinations();
		if (totalVotes == 0) return 0;
		
		int votingComplete = totalVotes - ranking.getCombinationsLeft().size();
		return (int) ((double) votingComplete / totalVotes * 100);
	}
	
	public void nextMatch() {
		
		 if (ranking.isFinalStage() || ranking.getCombinationsLeft().isEmpty()) {
		        return;
		    }
		    
		    int index = new Random().nextInt(ranking.getCombinationsLeft().size());
		    String combination = ranking.getCombinationsLeft().remove(index);
		    
		    String[] parts = combination.split(" vs ");
		    Piece piece1 = ranking.getPieceByTitle(parts[0]);
		    Piece piece2 = ranking.getPieceByTitle(parts[1]);
		    
		    if (piece1 != null && piece2 != null) {
		        updatePieces(piece1, piece2);
		    }
	}

    public void showFinalRanking() {
        List<Piece> sortedRanking = ranking.getRanking();
        boolean [] showTop3 = {false};
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Ranking Final");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        
        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new BorderLayout());
        
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 16));
        rankingPanel.add(label, BorderLayout.CENTER);
        
        JButton toggleButton = new JButton("Ver Top 3");
        
        toggleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
        		showTop3[0] = !showTop3[0];
        		toggleButton.setText(showTop3[0] ? "Voltar ao Ranking Completo" : "Ver Top 3");
        		updateRankingText(label, sortedRanking, showTop3[0]);
			}
		});
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(toggleButton);
        
        updateRankingText(label, sortedRanking, showTop3[0]);
        
        dialog.add(rankingPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setSize(550, Math.min(800, 200 + (sortedRanking.size() * 30)));
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    
    private void updateRankingText(JLabel label, List<Piece> sortedRanking, boolean showTop3) {
    	StringBuilder rankingText = new StringBuilder("<html><body style='width: 500px; font-size:14px;'>");
    	
        if (showTop3 && sortedRanking.size() >= 3) {
            rankingText.append("<h2 style='color:blue;'>Este é o seu Top 3!</h2>");
            for (int i = 0; i < 3; i++) {
                Piece piece = sortedRanking.get(i);
                rankingText.append("<b>").append((i + 1)).append(". ").append(piece.getTitle())
                        .append(": ").append(piece.getScore()).append(" votos</b><br>");
            }
            rankingText.append("<br>");
            
            for (int i = 3; i < sortedRanking.size(); i++) {
                Piece piece = sortedRanking.get(i);
                rankingText.append((i + 1)).append(". ").append(piece.getTitle())
                        .append(": ").append(piece.getScore()).append(" votos<br>");
            }
        } else {
            for (int i = 0; i < sortedRanking.size(); i++) {
                Piece piece = sortedRanking.get(i);
                rankingText.append((i + 1)).append(". ").append(piece.getTitle())
                        .append(": ").append(piece.getScore()).append(" votos<br>");
            }
        }
        rankingText.append("</body></html>");
        label.setText(rankingText.toString());
    }
}