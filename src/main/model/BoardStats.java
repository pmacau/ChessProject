package model;

import java.util.ArrayList;
import java.util.List;

public class BoardStats {
    private Integer tiles;
    private Integer streak;
    private String difficulty;
    private List<String> piecesGuessed;
    private List<String> totalStat;

    public BoardStats() {
        this.streak = 0;
        this.piecesGuessed = new ArrayList<>();
        totalStat = new ArrayList<>();
        totalStat.add("Difficulty:");
        totalStat.add("N/A");
        totalStat.add("Size:");
        totalStat.add("N/A");
        totalStat.add("Streak:");
        totalStat.add("0");
        totalStat.add("Most Guessed Piece:");
        totalStat.add("N/A");
    }

    public void difficulty(String difficulty) {
        this.difficulty = difficulty;
        totalStat.set(1, difficulty);
    }

    public void boardSize(Integer tiles) {
        this.tiles = tiles;
        String stringTiles = Integer.toString(tiles);
        totalStat.set(3, stringTiles);
    }

    public void streak(Integer count) {
        this.streak += count;
        String stringStreak = Integer.toString(count);
        totalStat.set(5, stringStreak);
    }

    public void addGuess(String pieceNotation) {
        String piece = pieceNotation.substring(pieceNotation.length() - 5, pieceNotation.length() - 4);
        piecesGuessed.add(piece);
    }

    public List<String> getGuess() {
        return piecesGuessed;
    }

    public void updateStats(Integer boardSize, Integer highestStreak, String mostGuessedPiece) {
    }

    public List<String> getTotalStat(){
        return totalStat;
    }
}
