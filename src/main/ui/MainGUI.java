package ui;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JPanel boardPanel;
    private Color brownTile;
    private Color whiteTile;

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

    public void play() {
        board.genBoard();
        board.genNextPos();
        initPlayUI();
    }

    public void initPlayUI() {
        frame.getContentPane().removeAll();
        frame.repaint();
        displayBoard();
        //display
    }

    // Heavily used: https://www.youtube.com/watch?v=vO7wHV0HB8w&t=250s&ab_channel=ScreenWorks, for creating the board
    public void displayBoard() {
        frame.setBounds(10, 10, 64 * dimension, 64 * dimension);
        boardPanel = new JPanel() {
            public void paint(Graphics g) {
                // board.getBoard();
                boolean white = true;
                for (int y = 0; y < dimension; y++) {
                    for (int x = 0; x < dimension; x++) {
                        if (white) {
                            g.setColor(Color.white);
                        } else {
                            g.setColor(Color.black);
                        }
                        g.fillRect(x * 64, y * 64, 64, 64);
                        white = !white;
                    }
                    white = !white;
                }
            }
        };
        frame.add(boardPanel);
        frame.setVisible(true);
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
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        new MainGUI();
    }

}
