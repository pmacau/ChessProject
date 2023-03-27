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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainGUI extends MouseInputAdapter implements ActionListener{
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
    private Image whiteQueen;
    private Image whiteKing;
    private Image whiteRook;
    private Image whiteBishop;
    private Image whiteKnight;
    private Image whitePawn;
    private Image blackQueen;
    private Image blackKing;
    private Image blackRook;
    private Image blackBishop;
    private Image blackKnight;
    private Image blackPawn;
    private Color whiteTile;
    private JPanel boardPanel;
    private Image whiteQueenButton;
    private Image whiteKingButton;
    private Image whiteRookButton;
    private Image whiteBishopButton;
    private Image whiteKnightButton;
    private Image whitePawnButton;
    private Image blackQueenButton;
    private Image blackKingButton;
    private Image blackRookButton;
    private Image blackBishopButton;
    private Image blackKnightButton;
    private Image blackPawnButton;


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
        try {
            generatePieces();
        } catch (Exception e) {
            System.out.println("Generate pieces is not working");
        }
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
        displayUserWhiteSelection();
        displayUserBlackSelection();
        while (going) {
            play();
            clear();
            prompt();
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

    private void prompt() {
    }

    private List<String> verifyRecalls() {
        return null;
    }

    private void clear() {
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                gamePanel.remove(gamePanel.highestLayer());
                frame.getContentPane().validate();
                frame.getContentPane().repaint();

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
        displayPieces();
    }

    public void displayUserWhiteSelection() {
        JButton whiteKingButton = new JButton((Icon) new ImageIcon(whiteKing));
        JButton whiteQueenButton = new JButton((Icon) new ImageIcon(whiteQueen));
        JButton whiteKnightButton = new JButton((Icon) new ImageIcon(whiteKnight));
        JButton whitePawnButton = new JButton((Icon) new ImageIcon(whitePawn));
        JButton whiteRookButton = new JButton((Icon) new ImageIcon(whiteRook));
        JButton whiteBishopButton = new JButton((Icon) new ImageIcon(whiteBishop));
        gamePanel.add(whiteKingButton);
        gamePanel.add(whiteQueenButton);
        gamePanel.add(whiteKnightButton);
        gamePanel.add(whitePawnButton);
        gamePanel.add(whiteRookButton);
        gamePanel.add(whiteBishopButton);
        whiteKingButton.setBounds(64 * dimension + 32, 0, 64, 64);
        whiteQueenButton.setBounds(64 * dimension + 32, 64, 64, 64);
        whiteKnightButton.setBounds(64 * dimension + 32, 128, 64, 64);
        whitePawnButton.setBounds(64 * dimension + 32, 128 + 64, 64, 64);
        whiteRookButton.setBounds(64 * dimension + 32, 128 + 64 + 64, 64, 64);
        whiteBishopButton.setBounds(64 * dimension + 32, 128 + 64 + 64 + 64, 64, 64);
    }

    public void displayUserBlackSelection() {
        JButton blackKingButton = new JButton((Icon) new ImageIcon(blackKing));
        JButton blackQueenButton = new JButton((Icon) new ImageIcon(blackQueen));
        JButton blackKnightButton = new JButton((Icon) new ImageIcon(blackKnight));
        JButton blackPawnButton = new JButton((Icon) new ImageIcon(blackPawn));
        JButton blackRookButton = new JButton((Icon) new ImageIcon(blackRook));
        JButton blackBishopButton = new JButton((Icon) new ImageIcon(blackBishop));
        blackKingButton.setBounds(64 * dimension + 32 + 64, 0, 64, 64);
        blackQueenButton.setBounds(64 * dimension + 32 + 64, 64, 64, 64);
        blackKnightButton.setBounds(64 * dimension + 32 + 64, 128, 64, 64);
        blackPawnButton.setBounds(64 * dimension + 32 + 64, 128 + 64, 64, 64);
        blackRookButton.setBounds(64 * dimension + 32 + 64, 128 + 64 + 64, 64, 64);
        blackBishopButton.setBounds(64 * dimension + 32 + 64, 128 + 64 + 64 + 64, 64, 64);
        gamePanel.add(blackKingButton);
        gamePanel.add(blackQueenButton);
        gamePanel.add(blackKnightButton);
        gamePanel.add(blackPawnButton);
        gamePanel.add(blackRookButton);
        gamePanel.add(blackBishopButton);
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
        gamePanel.setBounds(0, 0, 64 * dimension + 500, 64 * dimension + 500);
        boardPanel.setBounds(0, 0, 64 * dimension + 500, 64 * dimension + 500);
        gamePanel.add(boardPanel, gamePanel.lowestLayer());
        frame.add(gamePanel);
        frame.setVisible(true);
    }

    // Same source used to parse images into actual pieces as the board source
    public void generatePieces() throws IOException {
        BufferedImage chessSet = ImageIO.read(new File("C:\\Users\\macau\\Downloads\\chess (1).png"));
        Image[] pieces = new Image[12];
        int i = 0;
        for (int y = 0; y < 400; y += 200) {
            for (int x = 0; x < 1200; x += 200) {
                pieces[i] = chessSet.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                i++;
            }
        }
        whiteKing = pieces[0];
        whiteQueen = pieces[1];
        whiteBishop = pieces[2];
        whiteKnight = pieces[3];
        whiteRook = pieces[4];
        whitePawn = pieces[5];
        blackKing = pieces[6];
        blackQueen = pieces[7];
        blackBishop = pieces[8];
        blackKnight = pieces[9];
        blackRook = pieces[10];
        blackPawn = pieces[11];

    }


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void displayPieces() {
        List<String> piecesOnBoard = board.getBoard();
        int i = 0;
        for (String piece : piecesOnBoard) {
            if (piece.equals("w.Q")) {
                addPiece(new JLabel(new ImageIcon(whiteQueen)), i);
            } else if (piece.equals("w.K")) {
                addPiece(new JLabel(new ImageIcon(whiteKing)), i);
            } else if (piece.equals("w.R")) {
                addPiece(new JLabel(new ImageIcon(whiteRook)), i);
            } else if (piece.equals("w.P")) {
                addPiece(new JLabel(new ImageIcon(whitePawn)), i);
            } else if (piece.equals("w.B")) {
                addPiece(new JLabel(new ImageIcon(whiteBishop)), i);
            } else if (piece.equals("w.Kn")) {
                addPiece(new JLabel(new ImageIcon(whiteKnight)), i);
            } else if (piece.equals("b.Q")) {
                addPiece(new JLabel(new ImageIcon(blackQueen)), i);
            } else if (piece.equals("b.K")) {
                addPiece(new JLabel(new ImageIcon(blackKing)), i);
            } else if (piece.equals("b.R")) {
                addPiece(new JLabel(new ImageIcon(blackRook)), i);
            } else if (piece.equals("b.P")) {
                addPiece(new JLabel(new ImageIcon(blackPawn)), i);
            } else if (piece.equals("b.B")) {
                addPiece(new JLabel(new ImageIcon(blackBishop)), i);
            } else if (piece.equals("b.Kn")) {
                addPiece(new JLabel(new ImageIcon(blackKnight)), i);
            }
            i++;
        }
    }

    private void addPiece(JLabel label, int i) {
        int x = (i % dimension);
        int y = (i / dimension);
        gamePanel.add(label, gamePanel.highestLayer());
        label.setBounds(x * 64, y * 64, 64, 64);
        label.setOpaque(false);
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
