package ui;

import model.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardApp {
    private Board board;
    private Scanner scan;

    public BoardApp() {
        runBoardApp();
    }

    // Used TellerApp to heavily influence my UI design choices, e.g the runBoardApp is almost identical to
    // runtellerApp.
    private void runBoardApp() {
        boolean keepGoing = true;
        System.out.println("Input 'Play' to start the a new game or 'q' to quit");
        while (keepGoing) {
            scan = new Scanner(System.in);
            String input = scan.next();
            if (input.equals("q")) {
                keepGoing = false;
            } else if (input.equals("Play")) {
                playCommand();
            }
        }

        System.out.println("\nGame is finished!");
    }

    public void playCommand() {
        playOptions();

    }


    public void playOptions() {
        System.out.println("What dimensions would you like the board to be? "
                +
                "\n (easy would be 4x4, medium would be 6x6, and hard"
                +
                " would be 8x8"
                + "\n Must be a perfect square e.g 4, 8, 16, 64..."
                + "\n Type in a perfect square to start.");
        scan = new Scanner(System.in);
        String dimensionChoice = scan.next();
        Integer numDimension = Integer.parseInt(dimensionChoice);
        board = new Board(numDimension);
        List<String> listBoard = board.rowsAndColumns();
        displayRowsAndColumns(listBoard);
    }

    public void displayRowsAndColumns(List<String> board) {
        int rowLength = (int) Math.sqrt(board.size());
        int i = 0;
            for (String p : board) {
              System.out.print(p);
            i++;
            if (i % rowLength == 0) {
                System.out.println();
             }


        }
    }
}
