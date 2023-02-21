package model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class Board {
    private List<String> boardGeneration;
    private Integer slots;
    private List<String> board;


    // Effects: Constructs a board with the dimensions given, for example, the input 8, would produce a
    // board of 8^2, 64 slots.
    // Requires: dimensions > 0, and must be a perfect square.
    public Board(Integer slots) {
        boardInteraction(slots);
    }

    public void boardInteraction(Integer dimensions) {
        slots = (int) Math.pow(dimensions, 2);
    }


    public int getSlots() {
        return slots;
    }

    public List<String> rowsAndColumns() {
        board = new ArrayList();
        for (int i = 0; i < this.getSlots(); i++) {
            board.add("X");
        }
        return board;

    }


    //Effects: Generates the board with the given slots
    //Requires: slots > 0
    //Modifies: This
    public List<String> getBoard() {
        return null;
    }

    public static void displayBoard(List<String> board) {
        System.out.println(board.toString());
    }


}

