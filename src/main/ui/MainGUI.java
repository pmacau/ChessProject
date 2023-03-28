package ui;

import model.Board;
import model.BoardStats;
import model.Stats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class MainGUI implements ActionListener {
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


    public MainGUI() {
        init();
        mainMenu();
    }


    public void init() {
        frame = new JFrame();
        panel = new JPanel();
        buttonPlay = new JButton("Play");
        buttonView = new JButton("View");
        buttonLoad = new JButton("Load");
        buttonQuit = new JButton("Quit");
        buttonMenu = new JButton("Main Menu");
        buttonQuit.addActionListener(this);
        buttonView.addActionListener(this);
        buttonLoad.addActionListener(this);
        buttonPlay.addActionListener(this);
        buttonMenu.addActionListener(this);
        genColour();
        going = true;
    }

    public void genColour() {
        brownTile = new Color(160, 77, 34, 123);
        whiteTile = new Color(255, 255, 255, 123);
    }


    public void mainMenu() {
        panel.add(buttonPlay);
        panel.add(buttonView);
        panel.add(buttonLoad);
        panel.add(buttonQuit);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(0, 1));
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Gui");
        frame.setBounds(0, 0, 1800, 1800);
        frame.pack();
        frame.setVisible(true);
    }

    public void playOptions() {
        frame.getContentPane().removeAll();
        frame.repaint();
        playOptions = new JPanel();
        frame.add(playOptions);
        JLabel instructions = new JLabel("What dimensions would you like the next board to be (any integer > 1)? \n"
                + " (easy would be 4x4, medium would be 6x6, and hard would be 8x8)");
        playOptions.add(instructions);
        diffAndDim();
        beginPlay();
        frame.setVisible(true);
    }

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

    public void beginPlay() {
        playButton = new JButton("Click to play");
        playOptions.add(playButton);
        playButton.addActionListener(this);
    }


    public void runPlay() {
        initPlayUI();
        clearAndPrompt();

    }


//    private List<String> verifyRecalls() {
//        List<String> pieceSet = new ArrayList<>();
//        pieceSet.add("b.B;0.0");
//        pieceSet.add("w.Q;1.0");
//        pieceSet.add("w.K;1.1");
//        pieceSet.add("w.K;0.1");
//        return pieceSet;
//    }

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

    private void complete() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setLayout(null);
        JPanel panelQuit = new JPanel();
        JPanel panelMenu = new JPanel();
        panelQuit.add(buttonQuit);
        panelMenu.add(buttonMenu);
        panelQuit.setBounds(200, 328, 100, 128);
        panelMenu.setBounds(200, 200, 100, 128);
        frame.add(panelQuit);
        frame.add(panelMenu);
        frame.setVisible(true);
    }


    private void incorrect() {
    }


    public void initPlayUI() {
        frame.getContentPane().removeAll();
        //frame.setLayout(null);
        frame.setBounds(10, 10, 90 * dimension + 500, 90 * dimension + 500);
        frame.repaint();
        displayBoard();
        displayCheck();
        displayStreak();
        piecesGUI.displayPieces();
        piecesGUI.displayUserWhiteSelection();
        piecesGUI.displayUserBlackSelection();
        piecesGUI.actionListeners();
        frame.setVisible(true);
    }

    public void displayCheck() {
        check = new JButton("Check");
        check.addActionListener(this);
        check.setBounds(64 * dimension + 32, 128 + 64 + 64 + 64 + 128, 160, 64);
        gamePanel.add(check);
    }

    public void check() {
        if (board.solved()) {
            complete();
        } else if (board.check(piecesGUI.getProposedSet())) {
            board.genNextPos();
            runPlay();
        } else {
            incorrect();
        }
    }


    public void displayStreak() {
        //JLabel streakLabel = new JLabel("Current Streak:" +  Integer.toString(boardstats.getStreak());
        //frame.add(streakLabel);
    }

    // Heavily used: https://www.youtube.com/watch?v=vO7wHV0HB8w&t=250s&ab_channel=ScreenWorks, for creating the board
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

    public void panelAndFrameInit() {
        gamePanel.setBounds(0, 0, 64 * dimension + 500, 64 * dimension + 500);
        boardPanel.setBounds(0, 0, 64 * dimension + 500, 64 * dimension + 500);
        gamePanel.add(boardPanel, gamePanel.lowestLayer());
        frame.add(gamePanel);
        piecesGUI = new PiecesGUI(gamePanel, frame, board);
        try {
            piecesGUI.generatePieces();
        } catch (Exception z) {
            System.out.println("Generate pieces is not working");
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
            board.genBoard();
            runPlay();
        } else if (actionSource.equals(buttonPlay)) {
            playOptions();
        } else if (actionSource.equals(buttonQuit)) {
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


    public static void main(String[] args) {
        new MainGUI();
    }

}
