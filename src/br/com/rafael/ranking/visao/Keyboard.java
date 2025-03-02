package br.com.rafael.ranking.visao;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.rafael.ranking.modelo.Piece;
import br.com.rafael.ranking.modelo.Ranking;

@SuppressWarnings("serial")
public class Keyboard extends JPanel implements ActionListener {
	
	private final Ranking ranking;
	private final Display display;
	private JButton button1;
	private JButton button2;
	
	public Keyboard(Ranking ranking, Display display) {
		this.ranking = ranking;
		this.display = display;
		initalizeButtons();
		arrangeLayout();
	}
	
	private void initalizeButtons() {
		button1 = new JButton();
		button1.setFont(new Font("SansSerif", Font.BOLD, 20));
		button2 = new JButton();
		button2.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		button1.addActionListener(this);
		button2.addActionListener(this);
		
		updateButtonLabels();
	}
	
	private void arrangeLayout() {
		setLayout(new GridLayout(1, 3));
		add(button1);
		add(button2);
	}
	
	private void updateButtonLabels() {
		Piece piece1 = display.getCurrentPiece1();
		Piece piece2 = display.getCurrentPiece2();
		
	    if (piece1 != null && piece2 != null) {
	        button1.setText("<html><center>1. " + piece1.getTitle() + "</center></html>");
	        button2.setText("<html><center>2. " + piece2.getTitle() + "</center></html>");
	    } else {
	        button1.setText("Votação concluída");
	        button2.setText("Votação concluída");
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		Piece piece1 = display.getCurrentPiece1();
		Piece piece2 = display.getCurrentPiece2();
		
		if (source == button1) {
			ranking.vote(piece1, piece2);
		} else if (source == button2) {
			ranking.vote(piece2, piece1);
		}
		
		display.updateDisplayRanking();;
		display.nextMatch();
		updateButtonLabels();
	}
}