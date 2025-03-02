package br.com.rafael.ranking.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ranking {

	private final List<Piece> allPieces;
	private List<Piece> currentPieces;
	private final List<RankingObserver> observers;
	private List<String> combinations;
	private int totalCombinations;
	private boolean isFinalStage = false;
	private boolean hasFinished = false;
	private boolean hasFinised;

	public Ranking() {
		this.allPieces = new ArrayList<>();
		this.currentPieces = new ArrayList<>();
		this.observers = new ArrayList<>();
		this.combinations = new ArrayList<>();
		initializePieces();
		initializeCombinations();
	}

	private void initializePieces() {
		allPieces.add(new Piece("Norman fucking Rockwell"));
		allPieces.add(new Piece("Mariners Apartment Complex"));
		allPieces.add(new Piece("Venice Bitch"));
		allPieces.add(new Piece("Fuck it I love you"));
		allPieces.add(new Piece("Doin' Time"));
		allPieces.add(new Piece("Love song"));
		allPieces.add(new Piece("Cinnamon Girl"));
		allPieces.add(new Piece("How to disappear"));
		allPieces.add(new Piece("California"));
		allPieces.add(new Piece("The next best American record"));
		allPieces.add(new Piece("The greatest"));
		allPieces.add(new Piece("Bartender"));
		allPieces.add(new Piece("Happiness is a butterfly"));
		allPieces.add(new Piece("hope is a dangerous thing for a woman like me to have - but i have it"));

		currentPieces = new ArrayList<>(allPieces);
	}

	private void initializeCombinations() {
		combinations = new ArrayList<>();
		
		for (int i = 0; i < currentPieces.size(); i++) {
			for (int j = i + 1; j < currentPieces.size(); j++) {
				String combination = currentPieces.get(i).getTitle() + " vs "
						+ currentPieces.get(j).getTitle();
				combinations.add(combination);
			}
		}
		
		Collections.shuffle(combinations);

        totalCombinations = combinations.size();
        System.out.println("Total de combinações geradas: " + totalCombinations);
	}

	public List<String> getCombinationsLeft() {
		return combinations;
	}

	public int getTotalCombinations() {
		return totalCombinations;
	}

	public void addObserver(RankingObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(RankingObserver observer) {
		observers.remove(observer);
	}

	public void vote(Piece pieceVoted, Piece opponent) {
	    if (currentPieces == null || pieceVoted == null || opponent == null || !currentPieces.contains(pieceVoted) || !currentPieces.contains(opponent)) {
	        return;
	    }
	    
	    pieceVoted.increaseScore();
		notifyObservers(pieceVoted.equals(getRanking().get(0)) ? ToScore.FIRST : ToScore.SECOND);
	}

	public void notifyObservers(ToScore event) {
		observers.stream().forEach(o -> o.updateRanking(event));
	}

	public List<Piece> getRanking() {
	    List<Piece> sortedList = new ArrayList<>(allPieces);
	    Collections.sort(sortedList);
	    
	    return sortedList;
	}

	public void resetRanking() {
		hasFinised = false;
		isFinalStage = false;
		allPieces.forEach(Piece::resetScore);
		currentPieces = new ArrayList<>(allPieces);
		combinations.clear();
		initializeCombinations();
		notifyObservers(ToScore.REBOOT);
	}

	public Piece getPieceByTitle(String title) {
		return currentPieces.stream().filter(p -> p.getTitle().equals(title)).findFirst().orElse(null);
	}

	public List<Piece> getTiedPieces() {
		List<Piece> sortedPieces = getRanking();

		if (sortedPieces.isEmpty()) {
			return Collections.emptyList();
		}
		
		Map<Integer, List<Piece>> groupedByVotes = sortedPieces.stream()
				.collect(Collectors.groupingBy(Piece::getScore));
		
		List<Piece> allTiedPieces = new ArrayList<>();
		for (List<Piece> group : groupedByVotes.values()) {
			if (group.size() > 1) {
				allTiedPieces.addAll(group);
			}
		}
	    System.out.println("Peças empatadas encontradas: " + allTiedPieces.toString());
	    return allTiedPieces;
	}

	public void startNewVotingCycle() {
		
		while(true) {
			List<Piece> tiedPieces = getTiedPieces();
			if (tiedPieces.size() <= 1) {  
				finishVoting();
				return;
			}
			int minScore = tiedPieces.stream()
					.mapToInt(Piece::getScore)
					.min()
					.orElse(0);
			List<Piece> lowestScoringPieces = tiedPieces.stream()
					.filter(p -> p.getScore() == minScore)
					.collect(Collectors.toList());
			
			if (lowestScoringPieces.size() <= 1) {
				finishVoting();
				return;
			}
			
			isFinalStage = false;
			currentPieces = new ArrayList<>(lowestScoringPieces);
			initializeCombinations();
			notifyObservers(ToScore.REBOOT);
			
			System.out.println("Novo ciclo de votação iniciado com as peças empatadas: " +
					lowestScoringPieces.toString());
			return;
	    }
		
	}
	
	public boolean isFinalStage() {
	    return isFinalStage;
	}
	
	public void finishVoting() {
	    if (hasFinished) return;
	    
	    hasFinished = true;
	    isFinalStage = true;
	    notifyObservers(ToScore.FINAL_RANKING);
	}

	public boolean isHasFinised() {
		return hasFinised;
	}
}