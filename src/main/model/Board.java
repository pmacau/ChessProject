package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Board {
    private Integer slots;
    private List<String> board;
    private List<String> pieces;
    private List<String> side;
    private List<String> pieceSet;

    // Effects: Constructs a board with the dimensions given, for example, the input 8, would produce a
    // board of 8^2, 64 Tiles.
    // Requires: dimensions > 0, and must be a perfect square.
    public Board(Integer numTiles) {
        pieces();
        sides();
        slots = (int) Math.pow(numTiles, 2);

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
        Integer x;
        Integer y;
        Integer indexPiece;
        Integer indexCord;
        Integer indexTeam;
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
        return (!("X" == (board.get(y * (int) Math.sqrt(slots) + x))));
    }

    // Effects: Generates board
    public void genBoard() {
        genRowsAndColumns();
        genNextPos();
        // getBoard();

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
        Boolean equivalence = true;

        if (!proposedSet.containsAll(pieceSet)) {
            equivalence = false;

        }


        return equivalence;
    }


    public List<String> getBoard() {
        return board;
    }

    // Effects: Checks if the board is solved (full)
    public Boolean solved() {
        Boolean full = true;
        for (String tile : board) {
            if (tile.equals("X")) {
                full = false;
            }
        }
        return full;
    }

}
