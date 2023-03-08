package ui;

import model.Board;
import model.BoardStats;
import model.Stats;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class BoardApp {
    private static final String JSON_STORE = "./data/workroom.json";
    private static final String JSON_STORE1 = "./data/statistics.json";
    private Board board;
    private Scanner scan;
    private Integer difficultyTime;
    private Stats stats;
    private BoardStats boardStats;
    private List<String> listBoard;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JsonWriter jsonWriterStat;
    private JsonReader jsonReaderStat;

    // Effects: Runs boardApp interface.
    // Modifies: This
    public BoardApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriterStat = new JsonWriter(JSON_STORE1);
        jsonReaderStat = new JsonReader(JSON_STORE1);
        runBoardApp();
    }

    // Effects: Runs the boardApp UI.
    // Modifies: This
    private void runBoardApp() {
        boolean keepGoing = true;
        statInit();
        System.out.println("Input 'Play' to start the a new game, 'load' to load previous board"
                + " 'view' to see previous guesses, or 'q' to quit");
        while (keepGoing) {
            scan = new Scanner(System.in);
            String input = scan.next();
            if (input.equals("q")) {
                keepGoing = false;
            } else if (input.equals("Play")) {
                initOptions();
            } else if (input.equals("view")) {
                loadBoardState();
                viewGuesses();
                runBoardApp();
            } else {
                if (isPrevCompleted() == true) {
                    loadBoardState();
                }
                loadBoardState();
                play();
            }
        }
        System.out.println("\nGame is finished!");
    }

    private void viewGuesses() {
        List<String> allGuesses = boardStats.getUserGuesses();
        System.out.println("Previous board guesses " + allGuesses);
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
            board.setDifficulty(2000);
            boardStats.difficulty("Hard");
        } else if (difficultyChoice.equals("Medium")) {
            difficultyTime = 4000;
            board.setDifficulty(4000);
            boardStats.difficulty("Medium");
        } else {
            difficultyTime = 8000;
            board.setDifficulty(8000);
            boardStats.difficulty("Easy");
        }
        if (difficultyTime == null) {
            difficultyTime = board.getDifficulty();
        }
    }

    // Effects: Initializes play instance, by generating the board, with the given user inputs.
    // Modifies: this
    public void playInit() {
        boardStats = new BoardStats();
        System.out.println("What dimensions would you like the next board to be (any integer > 1)? "
                + "\n (easy would be 4x4, medium would be 6x6, and hard" + " would be 8x8) "
                + "or type q to quit");
        String dimensionChoice = scan.next();
        if (dimensionChoice.equals("q")) {
            System.exit(0);
        }
        int numDimension = Integer.parseInt(dimensionChoice);
        board = new Board(numDimension);
        boardStats.boardSize((int) Math.pow(numDimension, 2));
        board.genBoard();
        scan = new Scanner(System.in);
        listBoard = board.getBoard();
    }

    // Effects: Runs the recall game with the board instance.
    // Modifies: This
    public void play() {
        boolean runningBoard = true;
        while (runningBoard) {
            displayRowsAndColumns(listBoard);
            displayInstructions();
            if (!board.check(gatherRecalls())) {
                incorrect();
                runningBoard = false;
            } else if (board.solved()) {
                complete();
                runningBoard = false;
            } else {
                correct();
                saveInGame();
                board.genNextPos();
            }
        }
    }


    // Effects: Gives user the option to play more, or to exit the entire program.
    // Modifies: This
    public void playOrExit() {
        System.out.println("Do you want to quit (type 'q'), to save game (type 'save'), or play again (type 'play')?");
        scan = new Scanner(System.in);
        String option = scan.next();
        if (option.equals("play")) {
            initOptions();
        } else if (option.equals("save")) {
            board.boardSetComplete();
            saveStats();
            saveBoard();
            initOptions();
        } else {
            System.out.println("Game is finished!");
            System.exit(0);
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
       // jsonWriter.writeComplete();
        playOrExit();
    }

    // Modifies: This.
    // Effects: Displays completion for user, and updates statistics.
    public void complete() {
        System.out.println("You have solved the entire board!");
        stats.setStat(boardStats);
        seeStats();
        playOrExit();
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
                        + " to use the notation presented in the visual printed, "
                        + "e.g. b.N;6.5,w.Q;0.0 would indicate a knight on "
                        + "\n 6th column presented and the 5th row in the board, alongside a white queen on row 0, "
                        + "column 0 "
                        + "starting from 0 at the top left of the board."
                        + "\n Enter here:");
            }
        }, board.getDifficulty());
    }

    // Effects: Converts the recall into comparable format to Board's specifications.
    // Modifies: This
    // Used https://beginnersbook.com/2015/05/java-string-to-arraylist-conversion/#:~:text=1)%20First%20split%20the%20string,asList()%20method.
    public List<String> gatherRecalls() {
        scan = new Scanner(System.in);
        String recall = scan.next();
        boardStats.addGuess(recall);
        boardStats.userGuesses(recall);
        String[] str;
        str = recall.split(",");
        List<String> separatedCoordinates;
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
        System.out.println("Biggest sized game you've played:" + stats.biggestSize()
                + "x" + stats.biggestSize());
        System.out.println("Highest streak you've gotten:" + stats.highestStreak());
        System.out.println("Stats of all prior games:");
        for (BoardStats statistic : totalStats) {
            System.out.println(statistic.getTotalStat());
        }
    }

    public void saveInGame() {
        System.out.println("If you'd like to save your game and quit type 'save/q', if not type anything else");
        scan = new Scanner(System.in);
        String save = scan.next();
        if (save.equals("save/q")) {
            stats.addStat(boardStats);
            saveStats();
            saveBoard();
            System.exit(0);
        }
    }

    public void saveBoard() {
        try {
            jsonWriter.open();
            jsonWriter.writeBoard(board);
            jsonWriter.close();
            System.out.println("Saved to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Could not save file");
        }
    }

    public void saveStats() {

        try {
            jsonWriterStat.open();
            jsonWriterStat.writeStats(stats);
            jsonWriterStat.close();
            System.out.println("Saved to" + JSON_STORE1);
        } catch (FileNotFoundException e) {
            System.out.println("Could not save file");
        }
    }

    // Effects: Tells if previous game was completed.

    public boolean isPrevCompleted() {
        try {
            return jsonReader.parseComplete();
        } catch (IOException e) {
            return false;
        }
    }

    // Modifies: This
    // Effects: Loads board state from file.
    public void loadBoardState() {
        try {
            if (isPrevCompleted()) {
                stats = jsonReaderStat.readStats();
                boardStats = new BoardStats();
                initOptions();
            }
            board = jsonReader.readBoard();
            stats = jsonReaderStat.readStats();
            boardStats = stats.returnStats().get(stats.returnStats().size() - 1);
            listBoard = board.getBoard();
            System.out.println("Loaded");
        } catch (JSONException | IOException e) {
            try {
                stats = jsonReaderStat.readStats();
                System.out.println("Loaded");
            } catch (IOException f) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }
}
