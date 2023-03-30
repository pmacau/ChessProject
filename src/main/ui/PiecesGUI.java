package ui;


import model.Board;
import model.BoardStats;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Contains most of piece mechanisms for GUI, such as placing pieces on the board, and generating images for pieces.
public class PiecesGUI implements ActionListener {

    private Integer currentPieceSelected;
    private Integer currentTeamSelected;
    private JLayeredPane gamePanel;
    private JFrame frame;
    private Board board;
    private Integer dimension;
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
    private JButton whiteQueenButton;
    private JButton whiteKingButton;
    private JButton whiteRookButton;
    private JButton whiteBishopButton;
    private JButton whiteKnightButton;
    private JButton whitePawnButton;
    private JButton blackQueenButton;
    private JButton blackKingButton;
    private JButton blackRookButton;
    private JButton blackBishopButton;
    private JButton blackKnightButton;
    private JButton blackPawnButton;
    private Color whiteTile;
    private Color brownTile;
    private List<JButton> tiles;
    private List<String> proposedSet;
    private List<String> piecesOnBoard;
    private BoardStats stats;
    private List<String> guesses;

    // Effects: Sets fields
    // Modifies: This
    public PiecesGUI(JLayeredPane gamePanel, JFrame frame, Board board, BoardStats stats) {
        this.gamePanel = gamePanel;
        this.frame = frame;
        this.board = board;
        this.dimension = (int) Math.sqrt(board.getSlots());
        this.stats = stats;
        tiles = new ArrayList<>();
        proposedSet = new ArrayList<>();
    }

    // Effects: Adds action listeners to buttons.
    // Modifies: This
    public void actionListeners() {
        whiteKingButton.addActionListener(this);
        whiteQueenButton.addActionListener(this);
        whiteBishopButton.addActionListener(this);
        whitePawnButton.addActionListener(this);
        whiteRookButton.addActionListener(this);
        whiteKnightButton.addActionListener(this);
        blackKingButton.addActionListener(this);
        blackQueenButton.addActionListener(this);
        blackBishopButton.addActionListener(this);
        blackPawnButton.addActionListener(this);
        blackRookButton.addActionListener(this);
        blackKnightButton.addActionListener(this);
    }


    // Effects: Displays white piece selection
    // Modifies: This
    public void displayUserWhiteSelection() {
        whiteKingButton = new JButton((Icon) new ImageIcon(whiteKing));
        whiteQueenButton = new JButton((Icon) new ImageIcon(whiteQueen));
        whiteKnightButton = new JButton((Icon) new ImageIcon(whiteKnight));
        whitePawnButton = new JButton((Icon) new ImageIcon(whitePawn));
        whiteRookButton = new JButton((Icon) new ImageIcon(whiteRook));
        whiteBishopButton = new JButton((Icon) new ImageIcon(whiteBishop));
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

    // Effects: Displays black piece selection.
    // Modifies: This.
    public void displayUserBlackSelection() {
        blackKingButton = new JButton((Icon) new ImageIcon(blackKing));
        blackQueenButton = new JButton((Icon) new ImageIcon(blackQueen));
        blackKnightButton = new JButton((Icon) new ImageIcon(blackKnight));
        blackPawnButton = new JButton((Icon) new ImageIcon(blackPawn));
        blackRookButton = new JButton((Icon) new ImageIcon(blackRook));
        blackBishopButton = new JButton((Icon) new ImageIcon(blackBishop));
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

    // Effects: Generates pieces
    // Modifies: This
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


    // Effects: Displays white pieces on the board.
    // Modifies: This.
    public void displayWhitePieces() {
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
            } else if (piece.equals("w.N")) {
                addPiece(new JLabel(new ImageIcon(whiteKnight)), i);
            }
            i++;
        }
        displayBlackPieces();
    }

    // Effects: Displays black pieces on the board.
    // Modifies: This
    public void displayBlackPieces() {
        List<String> piecesOnBoard = board.getBoard();
        int i = 0;
        for (String piece : piecesOnBoard) {
            if (piece.equals("b.Q")) {
                addPiece(new JLabel(new ImageIcon(blackQueen)), i);
            } else if (piece.equals("b.K")) {
                addPiece(new JLabel(new ImageIcon(blackKing)), i);
            } else if (piece.equals("b.R")) {
                addPiece(new JLabel(new ImageIcon(blackRook)), i);
            } else if (piece.equals("b.P")) {
                addPiece(new JLabel(new ImageIcon(blackPawn)), i);
            } else if (piece.equals("b.B")) {
                addPiece(new JLabel(new ImageIcon(blackBishop)), i);
            } else if (piece.equals("b.N")) {
                addPiece(new JLabel(new ImageIcon(blackKnight)), i);
            }
            i++;
        }
    }

    // Effects: Adds piece to the board.
    // Modifies: This.
    private void addPiece(JLabel label, int i) {
        int x = (i % dimension);
        int y = (i / dimension);
        gamePanel.add(label, gamePanel.highestLayer());
        label.setBounds(x * 64, y * 64, 64, 64);
        label.setOpaque(false);
    }

    // Effects: Allows for the board to respond to user input by having invisible buttons on board.
    // Modifies: This.
    // partially used https://stackoverflow.com/questions/5654208/making-a-jbutton-invisible-but-clickable#:~:
    // text=How%20do%20I%20make%20a,setVisible(false)%3B.
    public void createTileButtons() {
        int totalTiles = dimension * dimension;
        JButton[] tile = new JButton[totalTiles];
        int i = 0;
        Boolean white = true;
        for (JButton button : tile) {
            int x = (i % dimension) * 64;
            int y = (i / dimension) * 64;
            tile[i] = new JButton();
            tile[i].setOpaque(false);
            tile[i].setContentAreaFilled(false);
            tile[i].setBorderPainted(false);
            tile[i].addActionListener(this);
            gamePanel.add(tile[i], gamePanel.highestLayer());
            tile[i].setBounds(x, y, 64, 64);
            tiles.add(tile[i]);
            i++;
        }
    }

    // Effects: Generates colours for the tiles.
    // Modifies: This
    public void genColour() {
        brownTile = new Color(160, 77, 34, 123);
        whiteTile = new Color(255, 255, 255, 123);
    }

    // Effects: Decides what team the piece is to later be placed.
    // Modifies: This.
    public void placePiece(Integer i) {
        if (currentTeamSelected == 0) {
            placeWhitePiece(i);
        } else {
            placeBlackPiece(i);
        }
    }

    // Effects: Places white piece on board.
    // Modifies: This.
    public void placeWhitePiece(Integer i) {
        if (currentPieceSelected == 0) {
            whiteKing(i);
        } else if (currentPieceSelected == 1) {
            whiteQueen(i);
        } else if (currentPieceSelected == 2) {
            whiteBishop(i);
        } else if (currentPieceSelected == 3) {
            whiteKnight(i);
        } else if (currentPieceSelected == 4) {
            whiteRook(i);
        } else if (currentPieceSelected == 5) {
            whitePawn(i);
        }

    }

    // Effects: Places whitePawn on the board.
    // Modifies: This
    private void whitePawn(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(whitePawn)), i);
        proposedSet.add("w.P" + ";" + coordinate);
        stats.addGuess("w.P;0.0");
        stats.userGuesses("w.P,");
    }

    // Effects: Places whiteRook on the board.
    // Modifies: This
    private void whiteRook(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(whiteRook)), i);
        proposedSet.add("w.R" + ";" + coordinate);
        stats.addGuess("w.R;0.0");
        stats.userGuesses("w.R,");
    }

    // Effects: Places whiteKnight on the board.
    // Modifies: This.
    private void whiteKnight(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(whiteKnight)), i);
        proposedSet.add("w.N" + ";" + coordinate);
        stats.addGuess("w.N;0.0");
        stats.userGuesses("w.N,");
    }

    // Effects: Places whiteBishop on the board.
    // Modifies: This.
    private void whiteBishop(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(whiteBishop)), i);
        proposedSet.add("w.B" + ";" + coordinate);
        stats.addGuess("w.B;0.0");
        stats.userGuesses("w.B,");
    }

    // Effects: Places whiteKing on the board.
    // Modifies: This.
    private void whiteKing(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(whiteKing)), i);
        proposedSet.add("w.K" + ";" + coordinate);
        stats.addGuess("w.K;0.0");
        stats.userGuesses("w.K,");
    }

    // Effects: Places whiteQueen on the board.
    // Modifies: This
    private void whiteQueen(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(whiteQueen)), i);
        proposedSet.add("w.Q" + ";" + coordinate);
        stats.addGuess("w.Q;0.0");
        stats.userGuesses("w.Q,");
    }

    // Effects: Determines the coordinates given the index of the piece.
    // Modifies: This
    private String coordinateConversion(Integer i) {
        int x = i % dimension;
        int y = i / dimension;
        return x + "." + y;
    }

    // Effects: Places black pieces on the board.
    // Modifies: This
    public void placeBlackPiece(Integer i) {
        if (currentPieceSelected == 6) {
            blackKing(i);
        } else if (currentPieceSelected == 7) {
            blackQueen(i);
        } else if (currentPieceSelected == 8) {
            blackBishop(i);
        } else if (currentPieceSelected == 9) {
            blackKnight(i);
        } else if (currentPieceSelected == 10) {
            blackRook(i);
        } else if (currentPieceSelected == 11) {
            blackPawn(i);
        }
    }

    // Effects: Places blackPawn on the board.
    // Modifies: This
    private void blackPawn(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(blackPawn)), i);
        proposedSet.add("b.P" + ";" + coordinate);
        stats.addGuess("w.P;0.0");
        stats.userGuesses("b.P,");
    }

    // Effects: Places blackRook on the board.
    // Modifies: This.
    private void blackRook(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(blackRook)), i);
        proposedSet.add("b.R" + ";" + coordinate);
        stats.addGuess("w.R;0.0");
        stats.userGuesses("b.R,");
    }

    // Effects: Places blackKnight on the board.
    // Modifies: This.
    private void blackKnight(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(blackKnight)), i);
        proposedSet.add("b.N" + ";" + coordinate);
        stats.addGuess("w.N;0.0");
        stats.userGuesses("b.N,");
    }

    // Effects: Places blackBishop on the board.
    // Modifies: This.
    private void blackBishop(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(blackBishop)), i);
        proposedSet.add("b.B" + ";" + coordinate);
        stats.addGuess("w.B;0.0");
        stats.userGuesses("b.B,");
    }

    // Effects: Places blackQueen on the board.
    // Modifies: This.
    private void blackQueen(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(blackQueen)), i);
        proposedSet.add("b.Q" + ";" + coordinate);
        stats.addGuess("w.Q;0.0");
        stats.userGuesses("b.Q,");
    }

    // Effects: Places blackKing on the board.
    // Modifies: This.
    public void blackKing(Integer i) {
        String coordinate = coordinateConversion(i);
        addPiece(new JLabel(new ImageIcon(blackKing)), i);
        proposedSet.add("b.K" + ";" + coordinate);
        stats.addGuess("w.K;0.0");
        stats.userGuesses("b.K,");
    }

    // getter
    public List<String> getProposedSet() {
        return proposedSet;
    }

    // Effects: Performs actions when buttons are pressed.
    // Modifies: This.
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton actionSource = (JButton) e.getSource();
        if (tiles.contains(actionSource)) {
            int i = tiles.indexOf(actionSource);
            placePiece(i);
        } else {
            selectPiece(actionSource);
        }
    }

    // Effects: Selects the index for a given piece.
    // Modifies: This
    private void selectPiece(JButton button) {
        currentTeamSelected = 0;
        if (button.equals(whiteKingButton)) {
            currentPieceSelected = 0;
        } else if (button.equals(whiteQueenButton)) {
            currentPieceSelected = 1;
        } else if (button.equals(whiteBishopButton)) {
            currentPieceSelected = 2;
        } else if (button.equals(whiteKnightButton)) {
            currentPieceSelected = 3;
        } else if (button.equals(whiteRookButton)) {
            currentPieceSelected = 4;
        } else if (button.equals(whitePawnButton)) {
            currentPieceSelected = 5;
        } else {
            selectRest(button);
        }
    }

    // Effects: Selects an index for a given piece.
    // Modifies: This.
    private void selectRest(JButton button) {
        currentTeamSelected = 1;
        if (button.equals(blackKingButton)) {
            currentPieceSelected = 6;
        } else if (button.equals(blackQueenButton)) {
            currentPieceSelected = 7;
        } else if (button.equals(blackBishopButton)) {
            currentPieceSelected = 8;
        } else if (button.equals(blackKnightButton)) {
            currentPieceSelected = 9;
        } else if (button.equals(blackRookButton)) {
            currentPieceSelected = 10;
        } else if (button.equals(blackPawnButton)) {
            currentPieceSelected = 11;
        }
    }
}
