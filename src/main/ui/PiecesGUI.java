package ui;


import model.Board;

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


    public PiecesGUI(JLayeredPane gamePanel, JFrame frame, Board board) {
        this.gamePanel = gamePanel;
        this.frame = frame;
        this.board = board;
        this.dimension = (int) Math.sqrt(board.getSlots());
        tiles = new ArrayList<>();
        proposedSet = new ArrayList<>();
    }

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
            } else if (piece.equals("w.N")) {
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
            } else if (piece.equals("b.N")) {
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

    public void genColour() {
        brownTile = new Color(160, 77, 34, 123);
        whiteTile = new Color(255, 255, 255, 123);
    }

    public void placePiece(Integer i) {
        if (currentTeamSelected == 0) {
            placeWhitePiece(i);
        } else {
            placeBlackPiece(i);
        }
    }

    public void placeWhitePiece(Integer i) {
        String coordinate = coordinateConversion(i);
        if (currentPieceSelected == 0) {
            addPiece(new JLabel(new ImageIcon(whiteKing)), i);
            proposedSet.add("w.K" + ";" + coordinate);
        } else if (currentPieceSelected == 1) {
            addPiece(new JLabel(new ImageIcon(whiteQueen)), i);
            proposedSet.add("w.Q" + ";" + coordinate);
        } else if (currentPieceSelected == 2) {
            addPiece(new JLabel(new ImageIcon(whiteBishop)), i);
            proposedSet.add("w.B" + ";" + coordinate);
        } else if (currentPieceSelected == 3) {
            addPiece(new JLabel(new ImageIcon(whiteKnight)), i);
            proposedSet.add("w.N" + ";" + coordinate);
        } else if (currentPieceSelected == 4) {
            addPiece(new JLabel(new ImageIcon(whiteRook)), i);
            proposedSet.add("w.R" + ";" + coordinate);
        } else if (currentPieceSelected == 5) {
            addPiece(new JLabel(new ImageIcon(whitePawn)), i);
            proposedSet.add("w.P" + ";" + coordinate);
        }

    }

    private String coordinateConversion(Integer i) {
        int x = i % dimension;
        int y = i / dimension;
        return x + "." + y;
    }

    public void placeBlackPiece(Integer i) {
        String coordinate = coordinateConversion(i);
        if (currentPieceSelected == 6) {
            addPiece(new JLabel(new ImageIcon(blackKing)), i);
            proposedSet.add("b.K" + ";" + coordinate);
        } else if (currentPieceSelected == 7) {
            addPiece(new JLabel(new ImageIcon(blackQueen)), i);
            proposedSet.add("b.Q" + ";" + coordinate);
        } else if (currentPieceSelected == 8) {
            addPiece(new JLabel(new ImageIcon(blackBishop)), i);
            proposedSet.add("b.B" + ";" + coordinate);
        } else if (currentPieceSelected == 9) {
            addPiece(new JLabel(new ImageIcon(blackKnight)), i);
            proposedSet.add("b.N" + ";" + coordinate);
        } else if (currentPieceSelected == 10) {
            addPiece(new JLabel(new ImageIcon(blackRook)), i);
            proposedSet.add("b.R" + ";" + coordinate);
        } else if (currentPieceSelected == 11) {
            addPiece(new JLabel(new ImageIcon(blackPawn)), i);
            proposedSet.add("b.P" + ";" + coordinate);
        }
    }

    public List<String> getProposedSet() {
        return proposedSet;
    }

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
