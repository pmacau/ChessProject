package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardStats {
    private Integer streak;
    private final List<String> piecesGuessed;
    private final List<String> totalStat;

    // Effects: Constructs board statistics, with empty placeholders.
    // Modifies: This
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

    // Effects: Changes difficulty statistic for board.
    // Modifies: This
    // Requires: difficulty should be easy, medium, or hard.
    public void difficulty(String difficulty) {
        totalStat.set(1, difficulty);
    }

    // Effects: Changes size statistic for board.
    // Modifies: This
    // Requires: Legal tiles, meaning integer > 1.
    public void boardSize(Integer tiles) {
        String stringTiles = Integer.toString(tiles);
        totalStat.set(3, stringTiles);
    }

    // Effects: Changes streak statistic for board, allowing user to see how many they got right on a board.
    // Modifies: This
    public void streak() {
        this.streak++;
        String stringStreak = Integer.toString(this.streak);
        totalStat.set(5, stringStreak);
    }

    // Modifies: This
    // Effects: Adds guesses from user notation and converts it to the piece itself, e.g k.B;0.0 -> B. Used to
    // update most guessed piece.
    // Requires: Must have valid pieceNotation, e.g "team"."piece";"x-cord"."y-cord"
    public void addGuess(String pieceNotation) {
        String piece = pieceNotation.substring(pieceNotation.length() - 5, pieceNotation.length() - 4);
        piecesGuessed.add(piece);
        updateGuess();
    }

    // Modifies: This
    // Effects: Updates guess counter for each piece to compare which piece is guessed most.
    public void updateGuess() {
        int k = 0;
        int n = 0;
        int q = 0;
        int b = 0;
        int p = 0;
        int r = 0;
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
            } else if (piece.equals("R")) {
                r += 1;
            }
        }

        sortGuessByMax(k, n, q, b, p, r);
    }

    // Effects: Obtains index of most guessed piece.
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
        if (!((k == 0 && n == 0) && (q == 0 && b == 0) && (p == 0 && r == 0))) {
            mostGuessedPiece(indexOfMax);
        }
    }

    // Effects: processes index of most guessed piece and converts it to the piece itself and adds it to statistics.
    // Modifies: this
    // Requires: Index less than 5.
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

    // Getter
    public List<String> getGuess() {
        return piecesGuessed;
    }

    // Getter
    public List<String> getTotalStat() {
        return totalStat;
    }
}
