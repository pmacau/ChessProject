package ui;

import model.Board;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

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
        System.out.println("What dimensions would you like the board to be? " + "\n (easy would be 4x4, medium would be 6x6, and hard" + " would be 8x8" + "\n Must be a perfect square e.g 4, 8, 16, 64..." + "\n Type in a perfect square to start.");
        init();
        play();
    }

    public void init() {
        scan = new Scanner(System.in);
        String dimensionChoice = scan.next();
        Integer numDimension = Integer.parseInt(dimensionChoice);
        board = new Board(numDimension);
        board.genBoard();

    }

    public void play() {
        Boolean runningBoard;
        runningBoard = true;
        while (runningBoard) {
            List<String> listBoard = board.getBoard();
            displayRowsAndColumns(listBoard);
            System.out.print("\n---------------" + "\n---------------" + "\n---------------");
            System.out.print("Please recall the positions on the board, separate multiple pieces with a space, also make sure"
                    + "to use the notation presented in the visual printed, e.g. b.Nx6y5 would indicate a knight on "
                    + "the 5th row and 6th column presented in the board");
            scan = new Scanner(System.in);
            String recall = scan.next();
            gatherRecalls(recall);
            if (!board.check(gatherRecalls())) {
                runningBoard = false;
            }
        }
    }

    public List<String> gatherRecalls(String recall) {
        return null;
    }

    public void displayRowsAndColumns(List<String> board) {
        int rowLength = (int) Math.sqrt(board.size());
        int i = 0;
        for (String p : board) {
            System.out.print(p + " ");
            i++;
            if (i % rowLength == 0) {
                System.out.println();
            }


        }
    }
}
