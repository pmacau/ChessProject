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

    public void genBoard() {
        genRowsAndColumns();
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
        board.set(indexCord, team + "." + piece);
        getBoard();
    }


    //Effects: Generates the board with the given Tiles
    //Requires: Tiles > 0
    //Modifies: This
    public List<String> getBoard() {
        return board;
    }

    public Boolean check(List<String> cords) {
        return true;
    }


}

