package ui;

import model.Board;
import model.BoardStats;
import model.Stats;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        buttonQuit.addActionListener(this);
        buttonView.addActionListener(this);
        buttonLoad.addActionListener(this);
        buttonPlay.addActionListener(this);
        genColour();

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
        Boolean going = true;
        board.genBoard();
        while (going) {
            play();
            clearAndPrompt();
            if (!board.check(verifyRecalls())) {
                incorrect();
                going = false;
            } else if (board.getComplete()) {
                complete();
                going = false;
            } else {
                board.genNextPos();
            }

        }
    }





    private List<String> verifyRecalls() {
        List<String> pieceSet = new ArrayList<>();
        pieceSet.add("b.B;0.0");
        pieceSet.add("w.Q;1.0");
        pieceSet.add("w.K;1.1");
        pieceSet.add("w.K;0.1");
        return pieceSet;
    }

    private void clearAndPrompt() {
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                gamePanel.remove(gamePanel.highestLayer());
                frame.getContentPane().validate();
                frame.getContentPane().repaint();
                piecesGUI.createTileButtons();

            }
        }, board.getDifficulty());
    }

    private void complete() {

    }


    private void incorrect() {
    }

    public void play() {
        initPlayUI();
    }

    public void initPlayUI() {
        frame.getContentPane().removeAll();
        //frame.setLayout(null);
        frame.setBounds(10, 10, 90 * dimension, 90 * dimension);
        frame.repaint();
        displayBoard();
        displayStreak();
        piecesGUI.displayPieces();
        piecesGUI.displayUserWhiteSelection();
        piecesGUI.displayUserBlackSelection();
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
        frame.setVisible(true);
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
            runPlay();
        } else if (actionSource.equals(buttonPlay)) {
            playOptions();
        } else if (actionSource.equals(buttonQuit)) {
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        new MainGUI();
    }

}
