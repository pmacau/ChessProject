package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Board {
    private List<String> boardGeneration;
    private Integer slots;
    private List<String> board;
    private List<String> pieces;
    private List<String> side;
    private List<String> pieceSet;

    // Effects: Constructs a board with the dimensions given, for example, the input 8, would produce a
    // board of 8^2, 64 Tiles.
    // Requires: dimensions > 0, and must be a perfect square.
    public Board(Integer numTiles) {
        pieces = new ArrayList<>();
        pieces.add("P"); // pawn
        pieces.add("B"); // bishop
        pieces.add("R"); // rook
        pieces.add("N"); // knight, N instead of Kn for chess convention, and convenience for 1 letter.
        pieces.add("Q"); // queen
        pieces.add("K"); // king
        side = new ArrayList<>();
        side.add("w"); // white
        side.add("b"); // black
        slots = (int) Math.pow(numTiles, 2);
        pieceSet = new ArrayList<>();

    }


    public int getSlots() {
        return slots;
    }

    public void genRowsAndColumns() {
        board = new ArrayList();
        for (int i = 0; i < this.getSlots(); i++) {
            board.add("X");
        }
    }

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

    public boolean duplicateCord(Integer x, Integer y) {
        return (!("X" == (board.get(y * (int) Math.sqrt(slots) + x))));
    }

    public void genBoard() {
        genRowsAndColumns();
        genNextPos();
        getBoard();
    }

    public void nextBoard() {

    }

    // stores all locations of pieces.
    public void genSet(Integer x, Integer y, String piece, String team) {
        String pieceCord = team + "." + piece + ";" + x + "." + y;
        pieceSet.add(pieceCord);
    }

    public Boolean check(List<String> proposedSet) {
        Boolean equivalence = true;
        for (String coordinate : proposedSet) {
            if (!pieceSet.contains(coordinate)) {
                equivalence = false;
            }

        }
        return equivalence;
    }

    //Effects: Generates the board with the given Tiles
    //Requires: Tiles > 0
    //Modifies: This
    public List<String> getBoard() {
        return board;
    }

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

