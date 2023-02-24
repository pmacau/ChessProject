package model;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
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
        totalStat.add("Difficulty");
        totalStat.add("N/A");
        totalStat.add("Size");
        totalStat.add("N/A");
        totalStat.add("Streak");
        totalStat.add("0");
        totalStat.add("Most Guessed Piece");
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

    public void streak() {
        this.streak++;
        String stringStreak = Integer.toString(this.streak);
        totalStat.set(5, stringStreak);
    }

    public void addGuess(String pieceNotation) {
        String piece = pieceNotation.substring(pieceNotation.length() - 5, pieceNotation.length() - 4);
        piecesGuessed.add(piece);
        updateGuess();
    }

    public void updateGuess() {
        Integer k = 0;
        Integer n = 0;
        Integer q = 0;
        Integer b = 0;
        Integer p = 0;
        Integer r = 0;
        for (String piece : piecesGuessed) {
            if (piece.equals("K")) {
                k += 1;
            } else if (piece.equals("N")) {
                n += 1;
            } else if (piece.equals("Q")) {
                q += 1;
            } else if (piece.equals("B")) {
                b += 1;
            } else if (piece.equals("P")) {
                p += 1;
            } else {
                r += 1;
            }
        }
        sortGuessByMax(k, n, q, b, p, r);
    }

    public void sortGuessByMax(Integer k, Integer n, Integer q, Integer b, Integer p, Integer r) {
        List<Integer> freqPiece = new ArrayList<>();
        freqPiece.add(k);
        freqPiece.add(n);
        freqPiece.add(q);
        freqPiece.add(b);
        freqPiece.add(p);
        freqPiece.add(r);
        Integer maxOcc = Collections.max(freqPiece);
        Integer indexOfMax = freqPiece.indexOf(maxOcc);
        mostGuessedPiece(indexOfMax);
    }

    public void mostGuessedPiece(Integer index) {
        if (index == 0) {
            totalStat.set(7, "King");
        } else if (index == 1) {
            totalStat.set(7, "Knight");
        } else if (index == 2) {
            totalStat.set(7, "Queen");
        } else if (index == 3) {
            totalStat.set(7, "Bishop");
        } else if (index == 4) {
            totalStat.set(7, "Pawn");
        } else {
            totalStat.set(7, "Rook");
        }
    }


    public List<String> getGuess() {
        return piecesGuessed;
    }


    public List<String> getTotalStat() {
        return totalStat;
    }
}
