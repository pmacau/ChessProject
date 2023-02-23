package model;

import java.util.ArrayList;
import java.util.List;

public class BoardStats {
    private Integer tiles;
    private Integer streak;
    private String difficulty;
    private String mostGuessedPiece;
    private List<String> totalStat;

    public BoardStats() {
        this.streak = 0;
        totalStat = new ArrayList<>();
        totalStat.add("Difficulty:");
        totalStat.add("Size:");
        totalStat.add("Streak:");
        totalStat.add("Most Guessed Piece:");
    }

    public void difficulty(String difficulty){
        this.difficulty = difficulty;
    }

    public void boardSize(Integer tiles){
        this.tiles = tiles;
    }

    public void streak(Integer count){

    }

    public void updateStats(Integer boardSize, Integer highestStreak, String mostGuessedPiece) {
    }
}
