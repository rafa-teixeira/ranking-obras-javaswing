package br.com.rafael.ranking.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import br.com.rafael.ranking.modelo.*;

@SuppressWarnings("serial")
public class MainScreen extends JFrame {
	
	private Ranking ranking;
	
	public MainScreen() {
		ranking = new Ranking();
		initializeLayout();
		configureWindow();
	}
	
	private void initializeLayout() {
		setLayout(new BorderLayout());
		
		Display display = new Display(ranking);
		display.setPreferredSize(new Dimension(600, 400));
		add(display, BorderLayout.NORTH);
		
		ranking.addObserver(display);
		
		Keyboard keyboard = new Keyboard(ranking, display);
		keyboard.setPreferredSize(new Dimension (600, 100));
		add(keyboard, BorderLayout.SOUTH);
	}
	
    private void configureWindow() {
        setTitle("Ranking");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

	public static void main(String[] args) {
		new MainScreen();
	}
}