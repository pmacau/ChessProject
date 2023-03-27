package ui;


import model.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PiecesGUI {

    private Integer currentPieceSelected;
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

    public PiecesGUI(JLayeredPane gamePanel, JFrame frame, Board board) {
        this.gamePanel = gamePanel;
        this.frame = frame;
        this.board = board;
        this.dimension = (int) Math.sqrt(board.getSlots());
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

    public void createTileButtons() {
        int totalTiles = dimension * dimension;
        JButton[] tile = new JButton[totalTiles];
        int i = 0;
        for (JButton button : tile) {
            int x = (i % dimension) * 64;
            int y = (i / dimension) * 64;
            tile[i] = new JButton("T");
            tile[i].setVisible(false);
            gamePanel.add(tile[i], gamePanel.highestLayer());
            tile[i].setBounds(x, y, 64, 64);
            i++;
        }
    }
}
