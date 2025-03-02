package br.com.rafael.ranking.modelo;

public class Piece implements Comparable<Piece> {
	
	private final String title;
	private int score;
	
	public Piece(String nome) {
		this.title = nome;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getScore() {
		return score;
	}
	
	public void increaseScore() {
		score++;
	}
	
	public void resetScore() {
		this.score = 0;
	}
	
	@Override
	public int compareTo(Piece o) {
		return o.getScore() - this.score;
	}
	
	@Override
	public String toString() {
	    return title;
	}
}