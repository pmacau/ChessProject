package ui;

import model.Board;
import model.BoardStats;
import model.Stats;

import java.util.*;

public class BoardApp {
    private Board board;
    private Scanner scan;
    private Integer difficultyTime;
    private Stats stats;
    private BoardStats boardStats;
    private List<String> listBoard;

    // Effects: Runs boardApp interface.
    // Modifies: This
    public BoardApp() {
        runBoardApp();
    }

    // Effects: Runs the boardApp UI.
    // Modifies: This
    private void runBoardApp() {
        boolean keepGoing = true;
        statInit();
        System.out.println("Input 'Play' to start the a new game or 'q' to quit");
        while (keepGoing) {
            scan = new Scanner(System.in);
            String input = scan.next();
            if (input.equals("q")) {
                keepGoing = false;
            } else if (input.equals("Play")) {
                initOptions();
            }
        }
        keepGoing = false;
        System.out.println("\nGame is finished!");
    }

    // Effects: Initializes entire stats class.
    // Modifies: This.
    public void statInit() {
        stats = new Stats();
    }

    // Effects: packages essential play methods for user.
    public void initOptions() {
        playInit();
        difficultyUI();
        play();
    }

    // Effects: Sets the difficulty of the game.
    // Modifies: This
    public void difficultyUI() {
        System.out.println("What difficulty? Options: 'Hard', 'Medium', 'Easy' (default) "
                + "(impacts the time delay in which you have to recall the pieces shown on the board)");
        scan = new Scanner(System.in);
        String difficultyChoice = scan.next();
        if (difficultyChoice.equals("Hard")) {
            difficultyTime = 2000;  // purely UI data, does not have any impact on board.
            boardStats.difficulty("Hard");
        } else if (difficultyChoice.equals("Medium")) {
            difficultyTime = 4000;
            boardStats.difficulty("Medium");
        } else {
            difficultyTime = 8000;
            boardStats.difficulty("Easy");
        }
    }

    // Effects: Initializes play instance, by generating the board, with the given user inputs.
    // Modifies: this
    public void playInit() {
        boardStats = new BoardStats();
        System.out.println("What dimensions would you like the board to be (any integer > 1)? "
                + "\n (easy would be 4x4, medium would be 6x6, and hard" + " would be 8x8)");
        String dimensionChoice = scan.next();
        Integer numDimension = Integer.parseInt(dimensionChoice);
        board = new Board(numDimension);
        boardStats.boardSize((int) Math.pow(numDimension, 2));
        board.genBoard();
        scan = new Scanner(System.in);
        listBoard = board.getBoard();

    }

    // Effects: Runs the recall game with the board instance.
    // Modifies: This
    public void play() {
        Boolean runningBoard = true;
        while (runningBoard) {
            displayRowsAndColumns(listBoard);
            displayInstructions();
            if (!board.check(gatherRecalls())) {
                incorrect();
                runningBoard = false;
            } else {
                correct();
                if (board.solved()) {
                    complete();
                    runningBoard = false;
                } else {
                    board.genNextPos();
                }
            }
        }
        System.out.println("Do you want to quit (type 'q') or play again (type 'play')?");
        scan = new Scanner(System.in);
        String option = scan.next();
        if (option.equals("play")) {
            initOptions();
        }
    }

    // Effects: Displays correct message for user, and updates board statistics.
    // Modifies: This
    public void correct() {
        System.out.println("You are correct");
        boardStats.streak();
        boardStats.updateGuess();
    }
    // Effects: Displays incorrect message for user, and updates statistics.
    // Modifies: This
    public void incorrect() {
        System.out.println("You are incorrect");
        stats.addStat(boardStats);
        seeStats();
    }
    // Modifies: This.
    // Effects: Displays completion for user, and updates statistics.
    public void complete() {
        System.out.println("You have solved the entire board!");
        stats.addStat(boardStats);
        seeStats();
    }


    // Effects: Makes the board 'disappear' and instructs user how to recall.
    // Modifies: This
    public void displayInstructions() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.print("\n---------------" + "\n---------------" + "\n---------------");
                // in the final version the board itself will disappear, however
                // I don't believe the console for IntelliJ is capable of that or at least
                // to my knowledge.
                System.out.print("\n Please recall the positions on the board, "
                        + "separate multiple pieces with a comma, also make sure"
                        + "to use the notation presented in the visual printed, "
                        + "e.g. b.N;6.5, would indicate a knight on "
                        + "\n 6th column presented and the 5th row in the board, "
                        + "starting from 0 at the top left of the board."
                        + "\n Enter here:");
            }
        }, difficultyTime);
    }

    // Effects: Converts the recall into comparable format to Board's specifications.
    // Modifies: This
    // Used https://beginnersbook.com/2015/05/java-string-to-arraylist-conversion/#:~:text=1)%20First%20split%20the%20string,asList()%20method.
    public List<String> gatherRecalls() {
        scan = new Scanner(System.in);
        String recall = scan.next();
        boardStats.addGuess(recall);
        String[] str;
        str = recall.split(",");
        List<String> separatedCoordinates = new ArrayList<>();
        separatedCoordinates = Arrays.asList(str);
        return separatedCoordinates;
    }

    // Effects: Displays the board with the current positions.
    // Used as a reference: https://stackoverflow.com/questions/58326516/how-to-print-arraylist-into-rows-and-columns
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

    // Effects: Displays stats of all prior games.
    public void seeStats() {
        List<BoardStats> totalStats = stats.returnStats();
        System.out.println("Stats of all prior games:");
        for (BoardStats statistic : totalStats) {
            System.out.println(statistic.getTotalStat());
        }
    }
}
