package br.com.rafael.ranking.modelo;

@FunctionalInterface
public interface RankingObserver {
	
	void updateRanking(ToScore eventScore);
}