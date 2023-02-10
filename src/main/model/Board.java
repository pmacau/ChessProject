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
    public Board(Integer dimensions) {
        // https://stackoverflow.com/questions/8071363/calculating-powers-of-integers, for changing from double to int.
        slots = (int) Math.pow(dimensions, 2);


    }


    public int getSlots() {
        return slots;
    }

    public void rowsAndColumns() {
        List<String> board = new ArrayList();
        for (int i = 0; i < this.getSlots(); i++) {
            board.add("X");
        }
    }


    public void displayRowsAndColumns() {
        int i = 0;
        for (String pos : board) {
            System.out.print(pos);
            i++;
            if ((i % this.getSlots()) == 0) {
                System.out.println();
            }
        }
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

    public static void main(Integer dimensions) {
        System.out.println("What dimensions would you like the board to be? "
                +
                "(easy would be 4x4, medium would be 6x6, and hard"
                +
                "would be 8x8");
        new Board(dimensions);
    }
}