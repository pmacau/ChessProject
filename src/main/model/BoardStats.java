package model;


import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Contains the statistics for each board played in the user's session, this includes their streak
// (how far they progressed through the board), their selected difficulty, and their most guessed piece.

public class BoardStats implements Writable {
    private Integer streak;
    private final List<String> piecesGuessed;
    private final List<String> totalStat;
    private List<String> allGuesses;

    // Effects: Constructs board statistics, with empty placeholders.
    // Modifies: This
    public BoardStats() {
        this.streak = 0;
        this.piecesGuessed = new ArrayList<>();
        this.allGuesses = new ArrayList<>();
        totalStat = new ArrayList<>();
        totalStat.add("Difficulty");
        totalStat.add("N/A");
        totalStat.add("Size");
        totalStat.add("0");
        totalStat.add("Streak");
        totalStat.add("0");
        totalStat.add("Most Guessed Piece");
        totalStat.add("N/A");
        EventLog.getInstance().logEvent(new Event("New BoardStats created"));
    }

    // Effects: Changes difficulty statistic for board.
    // Modifies: This
    // Requires: difficulty should be easy, medium, or hard.
    public void difficulty(String difficulty) {
        totalStat.set(1, difficulty);
        EventLog.getInstance().logEvent(new Event("Difficulty set: " + difficulty));
    }

    // Effects: Changes size statistic for board.
    // Modifies: This
    // Requires: Legal tiles, meaning integer > 1.
    public void boardSize(Integer tiles) {
        String stringTiles = Integer.toString(tiles);
        totalStat.set(3, stringTiles);
        EventLog.getInstance().logEvent(new Event("boardSize: " + tiles));
    }

    // Effects: Sets most guess for testing purposes.
    // Modifies: This
    public void setGuess(String piece) {
        totalStat.set(7, piece);
        EventLog.getInstance().logEvent(new Event("Set guess: " + piece));
    }


    // Effects: Changes streak statistic for board, allowing user to see how many they got right on a board.
    // Modifies: This
    public void streak() {
        this.streak++;
        String stringStreak = Integer.toString(this.streak);
        totalStat.set(5, stringStreak);
        EventLog.getInstance().logEvent(new Event("Streak updated"));
    }

    // Modifies: This
    // Effects: Adds guesses from user notation and converts it to the piece itself, e.g k.B;0.0 -> B. Used to
    // update most guessed piece.
    // Requires: Must have valid pieceNotation, e.g "team"."piece";"x-cord"."y-cord"
    public void addGuess(String pieceNotation) {
        String piece = pieceNotation.substring(pieceNotation.length() - 5, pieceNotation.length() - 4);
        EventLog.getInstance().logEvent(new Event("Guess: " + piece + " added"));
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
            } else {
                r += 1;
            }
        }
        EventLog.getInstance().logEvent(new Event("Updated guess"));
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
        EventLog.getInstance().logEvent(new Event("Sorted guess"));
    }

    // Effects: processes index of most guessed piece and converts it to the piece itself and adds it to statistics,
    // if tie chooses from the order king, knight, queen, bishop....
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
        EventLog.getInstance().logEvent(new Event("Most guessed piece"));
    }

    // Effects: Adds guess to user guesses.
    // Modifies: This
    public void userGuesses(String guess) {
        allGuesses.add(guess);
        EventLog.getInstance().logEvent(new Event("Guess added to user guesses"));
    }

    // Getter
    public List<String> getUserGuesses() {
        EventLog.getInstance().logEvent(new Event("Returning user guesses"));
        return allGuesses;
    }


    // Getter
    public List<String> getGuess() {
        EventLog.getInstance().logEvent(new Event("Returning pieces guessed"));
        return piecesGuessed;
    }

    // Getter
    public List<String> getTotalStat() {
        EventLog.getInstance().logEvent(new Event("Returning total stat"));
        return totalStat;
    }

    // Getter
    public Integer getStreak() {
        EventLog.getInstance().logEvent(new Event("Getting streak"));
        return this.streak;
    }

    // Getter
    public Integer getSize() {
        EventLog.getInstance().logEvent(new Event("Getting size"));
        return Integer.parseInt(totalStat.get(3));
    }

    // Getter
    public String getMostGuess() {
        EventLog.getInstance().logEvent(new Event("Getting most guessed"));
        return totalStat.get(7);
    }

    //Effects: Adds pieces guessed
    // Modifies: This
    public void addPiecesGuessed(String piece) {
        EventLog.getInstance().logEvent(new Event("Adding piece guessed"));
        piecesGuessed.add(piece);
    }

    public List<String> getPiecesGuessed() {
        EventLog.getInstance().logEvent(new Event("Getting pieces guessed"));
        return piecesGuessed;
    }

    // Effects: Creates Json, with current statistics of the board.
    @Override
    public JSONObject toJson() {
        EventLog.getInstance().logEvent(new Event("Creating Json with current BoardStats"));
        JSONObject json = new JSONObject();
        json.put("Difficulty", totalStat.get(1));
        json.put("Difficulty", totalStat.get(1));
        json.put("Size", totalStat.get(3));
        json.put("Streak", totalStat.get(5));
        json.put("Most Guessed Piece", totalStat.get(7));
        json.put("Guesses", this.allGuesses);
        json.put("ForMost", piecesGuessed);

        return json;
    }

}
