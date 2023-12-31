package ui;

import model.Board;
import model.BoardStats;
import model.Event;
import model.EventLog;
import model.Stats;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import persistence.JsonReader;
import persistence.JsonWriter;

// import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

// Graphical user interface containing most of the user displays.
public class MainGUI implements ActionListener, WindowListener {
    // private final JLabel label;
    private JButton buttonPlay;
    private JButton buttonView;
    private JButton buttonLoad;
    private JButton buttonQuit;
    private JPanel panel;
    private JFrame frame;
    private JPanel playOptions;
    private JButton playButton;
    private JTextField difficultyChoice;
    private JTextField dimensionChoice;
    private Board board;
    private Integer dimension;
    private JLayeredPane gamePanel;
    private Color brownTile;
    private BoardStats boardstats;
    private Stats stats;
    private Color whiteTile;
    private JPanel boardPanel;
    private PiecesGUI piecesGUI;
    private JButton check;
    private Boolean going;
    private JButton buttonMenu;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JsonWriter jsonWriterStat;
    private JsonReader jsonReaderStat;
    private Scanner scan;
    private JButton buttonSave;
    private static final String JSON_STORE = "./data/workroom.json";
    private static final String JSON_STORE1 = "./data/statistics.json";

    // Effects: Begins the GUI
    public MainGUI() {
        loadAndSaveinit();
        init();
        statInit();
        mainMenu();
    }

    // Effects: Initializes JSON Stores.
    // Modifies: This
    public void loadAndSaveinit() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriterStat = new JsonWriter(JSON_STORE1);
        jsonReaderStat = new JsonReader(JSON_STORE1);
    }

    // Effects: Initializes frame, and important buttons.
    // Modifies: This
    public void init() {
        frame = new JFrame();
        frame.addWindowListener(this);
        panel = new JPanel();
        buttonPlay = new JButton("Play");
        view();
        load();
        saveButton();
        buttonQuit = new JButton("Quit");
        buttonMenu = new JButton("Main Menu");
        buttonQuit.addActionListener(this);
        buttonView.addActionListener(this);

        buttonPlay.addActionListener(this);
        buttonMenu.addActionListener(this);
        genColour();
        going = true;
    }

    // Effects: If pressed saves the given state.
    // Modifies: This.
    public void saveButton() {
        buttonSave = new JButton("Save");
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStats();
                saveBoard();
            }
        });
    }

    // Effects: If pressed goes to view page.
    // Modifies: This
    public void view() {
        buttonView = new JButton("View");
        buttonView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewDisplay();
            }
        });
    }

    // Effects: Displays user guesses
    // Modifies: This
    public void viewDisplay() {
        frame.getContentPane().removeAll();
        frame.repaint();
        JScrollPane userGuesses = new JScrollPane();
        userGuesses.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userGuesses.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JTextArea o = new JTextArea();
        String str = "";
        for (BoardStats boardStat : stats.returnStats()) {
            for (String s : boardStat.getUserGuesses()) {
                str = str + s;
            }
            o.append(str);
        }
        //o = new JTextArea("Test");
        userGuesses.add(o);
        userGuesses.setViewportView(o);
        frame.add(userGuesses, BorderLayout.CENTER);
        frame.add(buttonMenu, BorderLayout.EAST);
        userGuesses.setBounds(300, 300, 300, 180);
        userGuesses.setEnabled(true);
        frame.setVisible(true);

    }

    // Effects: Loads last save if pressed
    // Modifies: This
    public void load() {
        buttonLoad = new JButton("Load");
        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBoardState();
            }
        });

    }

    // Effects: Initializes stats
    // Modifies: This
    public void statInit() {
        stats = new Stats();
    }

    // Effects: Generates colours for tiles.
    // Modifies: This
    public void genColour() {
        brownTile = new Color(160, 77, 34, 123);
        whiteTile = new Color(255, 255, 255, 123);
    }


    // Effects: Displays main menu.
    // Modifies: This
    public void mainMenu() {
        panel.add(buttonPlay);
        panel.add(buttonView);
        panel.add(buttonLoad);
        panel.add(buttonSave);
        panel.add(buttonQuit);
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        panel.setLayout(new GridLayout(0, 1));
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Gui");
        frame.setBounds(0, 0, 1800, 1800);
        frame.pack();
        frame.setVisible(true);
    }

    // Effects: PlayOptions display
    // Modifies: This
    public void playOptions() {
        frame.getContentPane().removeAll();
        frame.repaint();
        playOptions = new JPanel();
        frame.add(playOptions);
        JLabel instructions = new JLabel("What dimensions would you like the next board to be (any integer > 1)? \n"
                + " (easy would be 4x4, medium would be 6x6, and hard would be 8x8, must be even)");
        playOptions.add(instructions);
        diffAndDim();
        beginPlay();
        frame.setVisible(true);
    }

    // Effects: Dimension options display
    // Modifies: this
    public void diffAndDim() {
        JLabel dimensions = new JLabel("Dimensions:");
        playOptions.add(dimensions);
        dimensionChoice = new JTextField(20);
        playOptions.add(dimensionChoice);
        dimensionChoice.addActionListener(this);
        JLabel difficulty = new JLabel("Difficulty ('Hard', 'Medium', or 'Easy'):");
        playOptions.add(difficulty);
        difficultyChoice = new JTextField(20);
        difficultyChoice.addActionListener(this);
        playOptions.add(difficultyChoice);
    }

    // Effects: Begins play
    // Modifies: This
    public void beginPlay() {
        playButton = new JButton("Click to play");
        playOptions.add(playButton);
        playButton.addActionListener(this);
    }


    // Effects: Runs play
    // Modifies: This
    public void runPlay() {
        difficultyAndSizeSetter();
        initPlayUI();
        clearAndPrompt();

    }

    // Effects: sets the size and gets the difficulty from the board.
    // Modifies: This
    public void difficultyAndSizeSetter() {
        boardstats.boardSize(dimension);
        if (board.getDifficulty() == 8000) {
            boardstats.difficulty("Easy");
        } else if (board.getDifficulty() == 4000) {
            boardstats.difficulty("Medium");
        } else if (board.getDifficulty() == 2000) {
            boardstats.difficulty("Hard");
        }
    }

    // Effects: Clears the board.
    // Modifies: This.
    private void clearAndPrompt() {
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                for (Component comp : gamePanel.getComponents()) {
                    if (comp instanceof JLabel) {
                        gamePanel.remove(comp);
                    }

                }
                frame.getContentPane().validate();
                frame.getContentPane().repaint();
                piecesGUI.createTileButtons();

            }
        }, board.getDifficulty());
    }

    // Effects: Develops completion screen.
    // Modifies: This
    private void complete() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setLayout(new BorderLayout());
        displayAndFinalizeStats();
        displayCompleteEndGameOptions();
        frame.setVisible(true);
    }

    // Effects: Displays complete options for user.
    // Modifies: This.
    public void displayCompleteEndGameOptions() {
        JPanel centerPane = new JPanel();
        clearStatisticsDisplay(centerPane);
        save(centerPane);
        JPanel panelQuit = new JPanel();
        JPanel panelMenu = new JPanel();
        JPanel panelIncorrect = new JPanel();
        panelIncorrect.add(new JLabel("Complete!"));
        panelQuit.add(buttonQuit);
        panelMenu.add(buttonMenu);
        centerPane.add(panelIncorrect);
        centerPane.add(panelQuit);
        centerPane.add(panelMenu);
        frame.add(centerPane, BorderLayout.CENTER);
    }

    // Effects: Clears stats if pressed.
    // Modifies: This
    public void clearStatisticsDisplay(JPanel centerPane) {
        JPanel panelReset = new JPanel();
        JButton reset = new JButton("Clear statistics");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statInit();
                clearStat();
                complete();
            }
        });
        panelReset.add(reset);
        centerPane.add(panelReset);
    }

    // Effects: Displays save button.
    // Modifies: This
    public void save(JPanel centerPane) {
        JPanel panelSave = new JPanel();
        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBoard();
                saveStats();
            }
        });
        panelSave.add(save);
        centerPane.add(panelSave);
    }

    // Effects: Clears stat.
    // Modifies: This
    private void clearStat() {
        boardstats = new BoardStats();
    }

    // Effects: When user is incorrect, develops screen and options.
    // Modifies: This.
    private void incorrect() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setLayout(null);
        board.boardSetComplete();
        // panelQuit.setBounds(200, 328, 100, 128);
        //panelMenu.setBounds(200, 200, 100, 128);
        //panelIncorrect.setBounds(200, 50, 100, 128);
        frame.getContentPane().setLayout(new BorderLayout());
        displayIncorrectEndGameOptions();
        displayAndFinalizeStats();
        frame.setVisible(true);
    }

    // Effects: displays options for when user gets incorrect guess.
    // Modifies: This
    public void displayIncorrectEndGameOptions() {
        JPanel centerPane = new JPanel();
        clearStatisticsDisplay(centerPane);
        save(centerPane);
        JPanel panelQuit = new JPanel();
        JPanel panelMenu = new JPanel();
        JPanel panelIncorrect = new JPanel();
        panelIncorrect.add(new JLabel("Incorrect!"));
        panelQuit.add(buttonQuit);
        panelMenu.add(buttonMenu);
        centerPane.add(panelIncorrect);
        centerPane.add(panelQuit);
        centerPane.add(panelMenu);
        frame.add(centerPane, BorderLayout.CENTER);
    }

    // Effects: Displays stats.
    // Modifies: This
    // https://stackoverflow.com/questions/26933411/how-to-pass-list-string-to-jlabel-and-show-in-jframe
    public void displayAndFinalizeStats() {
        JScrollPane allStats = new JScrollPane();
        allStats.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        allStats.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JTextArea o = new JTextArea();
        int i = 1;
        for (BoardStats boardstats : stats.returnStats()) {
            String individualStat = "\n Game " + i + ": ";
            for (String str : boardstats.getTotalStat()) {
                individualStat = individualStat + " " + str;
            }
            o.append(individualStat);
            i++;
        }
        allStats.add(o);
        allStats.setViewportView(o);
        frame.add(allStats, BorderLayout.EAST);
        allStats.setBounds(300, 300, 600, 180);
        allStats.setEnabled(true);
        frame.setVisible(true);
    }


    // Effects: Initiates game UI.
    // Modifies: This.
    public void initPlayUI() {
        frame.getContentPane().removeAll();
        //frame.setLayout(null);
        frame.setBounds(10, 10, 90 * dimension + 500, 90 * dimension + 500);
        frame.repaint();
        displayBoard();
        displayCheck();
        displayStreak();
        saveInGame();
        piecesGUI.displayWhitePieces();
        piecesGUI.displayUserWhiteSelection();
        piecesGUI.displayUserBlackSelection();
        piecesGUI.actionListeners();
        frame.setVisible(true);
    }

    // Effects: Displays check button.
    // Modifies: This.
    public void displayCheck() {
        check = new JButton("Check");
        check.addActionListener(this);
        check.setBounds(64 * dimension + 32, 128 + 64 + 64 + 64 + 128, 160, 64);
        gamePanel.add(check);
    }

    // Effects: Checks to see if user completes/guesses correctly.
    // Modifies: This
    public void check() {
        if (board.solved()) {
            boardstats.streak();
            complete();
        } else if (board.check(piecesGUI.getProposedSet()) && (piecesGUI.getProposedSet().size()
                <= board.getPieceSet().size())) {
            boardstats.streak();
            board.genNextPos();
            runPlay();
        } else {
            incorrect();
        }
    }

    //Effects: Displays user streak
    // Modifies: This
    public void displayStreak() {
        JButton streakLabel = new JButton("Current Streak:" + Integer.toString(boardstats.getStreak()));
        streakLabel.setEnabled(false);
        gamePanel.add(streakLabel);
        streakLabel.setBounds(64 * dimension + 32, 128 + 64 + 64 + 128, 160, 64);

    }

    // Heavily used: https://www.youtube.com/watch?v=vO7wHV0HB8w&t=250s&ab_channel=ScreenWorks, for creating the board
    // Effects: Displays board tiles
    // Modifies: This
    public void displayBoard() {
        gamePanel = new JLayeredPane();
        boardPanel = new JPanel() {
            public void paint(Graphics g) {
                boolean white = true;
                for (int y = 0; y < dimension; y++) {
                    for (int x = 0; x < dimension; x++) {
                        if (white) {
                            g.setColor(whiteTile);
                        } else {
                            g.setColor(brownTile);
                        }
                        g.fillRect(x * 64, y * 64, 64, 64);
                        white = !white;
                    }
                    white = !white;
                }
            }
        };
        panelAndFrameInit();
    }

    // Effects: Initiates frame and panel
    // Modifies: This
    public void panelAndFrameInit() {
        gamePanel.setBounds(0, 0, 64 * dimension + 500, 64 * dimension + 500);
        boardPanel.setBounds(0, 0, 64 * dimension + 500, 64 * dimension + 500);
        gamePanel.add(boardPanel, gamePanel.lowestLayer());
        frame.add(gamePanel);
        piecesGUI = new PiecesGUI(gamePanel, frame, board, boardstats);
        try {
            piecesGUI.generatePieces();
        } catch (Exception z) {
            System.out.println("Generate pieces is not working");
        }
    }

    // Effects: Starts the play mechanism
    // Modifies: this
    public void play() {
        boardstats = new BoardStats();
        stats.addStat(boardstats);
        board.genBoard();
        runPlay();
    }


    // Effects: Saves while in game.
    // Modifies: this.
    public void saveInGame() {
        JButton saveAndExitButton = new JButton("Save and Exit");
        saveAndExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // stats.addStat(boardstats);
                saveStats();
                saveBoard();
                windowClosing(null);
                System.exit(0);
            }

        });
        JPanel savePanel = new JPanel();
        savePanel.add(saveAndExitButton);
        gamePanel.add(savePanel);
        savePanel.setBounds(64 * dimension + 32, 128 + 64 + 64 + 64 + 128 + 160, 160, 160);
        ;

    }

    // Effects: Saves board state.
    public void saveBoard() {
        try {
            jsonWriter.open();
            jsonWriter.writeBoard(board);
            jsonWriter.close();
            // System.out.println("Saved to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            //System.out.println("Could not save file");
        }
    }

    // Effects: Saves stats
    public void saveStats() {

        try {
            jsonWriterStat.open();
            jsonWriterStat.writeStats(stats);
            jsonWriterStat.close();
            //  System.out.println("Saved to" + JSON_STORE1);
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
                boardstats = new BoardStats();
            } else {
                board = jsonReader.readBoard();
                stats = jsonReaderStat.readStats();
                boardstats = stats.returnStats().get(stats.returnStats().size() - 1);
                // stats.statsRemove(boardstats);
                dimension = (int) Math.sqrt(board.getSlots());
                // listBoard = board.getBoard();
                board.loadSet();
                runPlay();
            }
        } catch (JSONException | IOException e) {
            // System.out.println("Error occurred");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton actionSource = (JButton) e.getSource();
        if (actionSource.equals(playButton)) {
            dimension = Integer.parseInt(dimensionChoice.getText());
            board = new Board(dimension);
            if (difficultyChoice.getText().equals("Hard")) {
                board.setDifficulty(2000);
            } else if (difficultyChoice.getText().equals("Medium")) {
                board.setDifficulty(4000);
            } else if (difficultyChoice.getText().equals("Easy")) {
                board.setDifficulty(8000);
            }
            play();
        } else if (actionSource.equals(buttonPlay)) {
            playOptions();
        } else if (actionSource.equals(buttonQuit)) {
            windowClosing(null);
            System.exit(0);
        } else if (actionSource.equals(check)) {
            check();
        } else if (actionSource.equals(buttonMenu)) {
            restart();
        }
    }

    public void restart() {
        frame.setVisible(false);
        init();
        mainMenu();

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Event i : EventLog.getInstance()) {
            System.out.println(i);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }



}
