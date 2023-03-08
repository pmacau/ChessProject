package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Contains the board, the positions of pieces, and generates next board state, as well as decides what pieces
// can be randomly put on a board.
public class Board implements Writable {
    private final Integer slots;
    private List<String> board;
    private List<String> pieces;
    private List<String> side;
    private List<String> pieceSet;
    private Boolean complete;
    private Integer difficulty;

    // Effects: Constructs a board with the dimensions given, for example, the input 8, would produce a
    // board of 8^2, 64 Tiles.
    public Board(Integer numTiles) {
        pieces();
        sides();
        slots = (int) Math.pow(numTiles, 2);
        this.complete = false;
    }


    // Effects: Creates the set of chess pieces in rotation
    // Modifies: This
    public void pieces() {
        pieces = new ArrayList<>();
        pieces.add("P"); // pawn
        pieces.add("B"); // bishop
        pieces.add("R"); // rook
        pieces.add("N"); // knight, N instead of Kn for chess convention, and convenience for 1 letter.
        pieces.add("Q"); // queen
        pieces.add("K"); // king
        pieceSet = new ArrayList<>();
    }

    // Effects: Creates black and white, which is the two sides the pieces can be on.
    // Modifies: This
    public void sides() {
        side = new ArrayList<>();
        side.add("w"); // white
        side.add("b"); // black
    }

    public int getSlots() {
        return slots;
    }

    // Effects: Generates blank board.
    // Modifies: This
    public void genRowsAndColumns() {
        board = new ArrayList();
        for (int i = 0; i < this.getSlots(); i++) {
            board.add("X");
        }
    }

    // Effects: Generates next board state by adding a random piece onto a board.
    // Modifies: This
    // Requires: Cannot add piece onto an already taken slot.
    public void genNextPos() {
        Random random = new Random();
        int x;
        int y;
        int indexPiece;
        int indexCord;
        int indexTeam;
        String team;
        String piece;
        x = random.nextInt((int) Math.sqrt(slots));
        y = random.nextInt((int) Math.sqrt(slots));
        indexPiece = random.nextInt((pieces.size()));
        piece = pieces.get(indexPiece);
        indexTeam = random.nextInt((side.size()));
        team = side.get(indexTeam);
        indexCord = y * (int) Math.sqrt(slots) + x;
        if (duplicateCord(x, y)) {
            genNextPos();
        } else {
            board.set(indexCord, team + "." + piece);
            genSet(x, y, piece, team);
        }


    }

    // Effects: Checks to see if a proposed next state has a duplicate tile assignment.
    // Requires: y * sqrt(slots) + x to have equal or less size than board.
    public boolean duplicateCord(Integer x, Integer y) {
        return (!("X".equals(board.get(y * (int) Math.sqrt(slots) + x))));
    }

    // Effects: Generates board
    public void genBoard() {
        genRowsAndColumns();
        genNextPos();


    }

    // Effects: stores all locations of pieces.
    // Modifies: this
    // Requires: x, and y must be in the domain of the board, and should be a valid piece and team
    // meaning one of the specified.
    public void genSet(Integer x, Integer y, String piece, String team) {
        String pieceCord = team + "." + piece + ";" + x + "." + y;
        pieceSet.add(pieceCord);
    }

    // Effects: Checks if the recall is correct.
    public Boolean check(List<String> proposedSet) {
        boolean equivalence = true;

        if (!proposedSet.containsAll(pieceSet)) {
            equivalence = false;

        }


        return equivalence;
    }

    // Getter
    public List<String> getBoard() {
        return board;
    }

    // Getter
    public List<String> getPieceSet() {
        return pieceSet;
    }


    // Effects: Checks if the board is solved (full)
    public Boolean solved() {
        boolean full = true;
        for (String tile : board) {
            if (tile.equals("X")) {
                full = false;
                break;
            }
        }
        if (full == true) {
            boardSetComplete();
        }
        return full;
    }


    // Effects: Used for testing without random, allows modification of board list. Creates empty
    // or full board.
    // Modifies this.
    public void boardCreate(String fill) {
        this.board = new ArrayList<>();
        if (fill.equals("empty")) {
            board.add("X");
            board.add("X");
            board.add("X");
            board.add("X");
        } else {
            board.add("b.B;0.0");
            board.add("b.B;1.0");
            board.add("b.B;0.1");
            board.add("b.B;1.1");
        }
    }

    // Effects: Loads the board.
    // Modifies: This
    public void boardLoad(List<String> pieceSet) {
        genRowsAndColumns();
        int indexCord;
        int y;
        int x;
        String stringX;
        String stringY;
        for (String piece : pieceSet) {
            stringX = piece.substring(piece.length() - 3, piece.length() - 2);
            stringY = piece.substring(piece.length() - 1);
            String parsedPiece = piece.substring(0, 3);
            x = Integer.parseInt(stringX);
            y = Integer.parseInt(stringY);
            indexCord = y * (int) Math.sqrt(slots) + x;
            board.set(indexCord, parsedPiece);
        }


    }

    // Setter
    public void boardSetComplete() {
        this.complete = true;
    }

    public void boardSetCompleteFalse() {
        this.complete = false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("boardState", board);
        json.put("difficulty", difficulty);
        json.put("complete", this.complete);
        json.put("slots", (int) Math.sqrt(slots));
        json.put("positions", pieceSet);
        return json;
    }

    public void setDifficulty(Integer difficultyChoice) {
        this.difficulty = difficultyChoice;
    }

    public Integer getDifficulty() {
        return this.difficulty;
    }
}
