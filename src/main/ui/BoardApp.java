package ui;

import model.Board;

import java.util.*;

public class BoardApp {
    private Board board;
    private Scanner scan;
    private Integer difficultyTime;

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
                playOptions();

            }
        }
        keepGoing = false;
        System.out.println("\nGame is finished!");
    }


    public void playOptions() {
        difficultyUI();
        System.out.println("What dimensions would you like the board to be? " + "\n (easy would be 4x4, medium would be 6x6, and hard" + " would be 8x8" + "\n Must be a perfect square e.g 4, 8, 16, 64..." + "\n Type in a perfect square to start.");
        init();
        play();
    }

    public void difficultyUI() {
        System.out.println("What difficulty? Options: 'Hard', 'Medium', 'Easy' (default) (impacts the time delay in which you have to recall the pieces shown on the board)");
        scan = new Scanner(System.in);
        String difficultyChoice = scan.next();
        if (difficultyChoice.equals("Hard")) {
            difficultyTime = 2000;
        } else if (difficultyChoice.equals("Medium")) {
            difficultyTime = 4000;
        } else {
            difficultyTime = 8000;
        }
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
            displayInstructions();
            if (board.solved()) {
                System.out.println("You have solved the entire board!");
                runningBoard = false;
            } else if (!board.check(gatherRecalls())) {
                System.out.println("You are incorrect");
                runningBoard = false;
                runBoardApp();
            } else {
                System.out.println("You are correct");
                board.genNextPos();
            }
        }
        System.out.println("Do you want to quit (type 'q') or go back to main menu (type 'menu')?");
        scan = new Scanner(System.in);
        String option = scan.next();
        if (option.equals("menu")) {
            playOptions();
        }

    }

    public void displayInstructions() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.print("\n---------------" + "\n---------------" + "\n---------------");
                // in the final version the board itself will disappear, however I don't believe the console for IntelliJ is capable of that or at least
                // to my knowledge.
                System.out.print("\n Please recall the positions on the board, separate multiple pieces with a comma, also make sure"
                        + "to use the notation presented in the visual printed, e.g. b.N;6.5, would indicate a knight on "
                        + "\n 6th column presented and the 5th row in the board, starting from 0 at the top left of the board."
                        + "\n Enter here:");
            }
        }, difficultyTime);
    }

    // Used https://beginnersbook.com/2015/05/java-string-to-arraylist-conversion/#:~:text=1)%20First%20split%20the%20string,asList()%20method.
    public List<String> gatherRecalls() {
        scan = new Scanner(System.in);
        String recall = scan.next();
        String[] str;
        str = recall.split(",");
        List<String> separatedCoordinates = new ArrayList<>();
        separatedCoordinates = Arrays.asList(str);
        return separatedCoordinates;
    }


    public void displayRowsAndColumns(List<String> board) {
        int rowLength = (int) Math.sqrt(board.size());
        int i = 0;
        for (String p : board) {
            System.out.print(p + "    ");
            i++;
            if (i % rowLength == 0) {
                System.out.println();
            }


        }
    }
}
