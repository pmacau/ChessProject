package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BoardTest {

    @Test
    //tests the constructor.
    public void dimensionTest(){
        Board board = new Board(3);
        assertEquals(9, board.getSlots());
        Board boardBase = new Board(1);
        assertEquals(1, boardBase.getSlots());
        List<String> boardEquivalence = new ArrayList<>();
        boardEquivalence.add("X");
        boardBase.genRowsAndColumns();
        assertEquals(boardEquivalence, boardBase.getBoard());
    }
    @Test
    // tests the generator method implementation
    // got index method from https://coderanch.com/t/628589/java/create-objects-loop
    public void genBoardTest(){
        Board genBoard = new Board ( 1 );
        genBoard.genBoard();
        List<String> boardEquivalence = new ArrayList<>();
        boolean randomTest = false;
        // since it's random I run it till they match. That is the reason
        // I used 1 tile, to limit the possibilities to ensure it matches at least once.
        Board [] equivalentBoard = new Board[100000];
        for (Integer i = 0; i < 100000; i++) {
            equivalentBoard[i] = new Board(1);
            equivalentBoard[i].genRowsAndColumns();
            equivalentBoard[i].genNextPos();
            if (genBoard.getBoard().equals(equivalentBoard[i].getBoard())){
                randomTest = true;
                break;
            }
        }
        assertTrue(randomTest);

    }

    @Test
    public void duplicateCordTestAndSolved(){
        Board emptyBoard = new Board(2);
        emptyBoard.boardCreate("empty");
        assertFalse(emptyBoard.duplicateCord(0, 0));
        assertFalse(emptyBoard.solved());

        Board fullBoard = new Board(2);
        fullBoard.boardCreate("full");
        assertTrue(fullBoard.duplicateCord(0, 0));
        assertTrue(fullBoard.solved());
    }

    @Test
    public void testPieceSetAdderAndCheckTest(){
        Board testBoard = new Board(2);
        testBoard.genSet(0,0,"B","b");
        List<String> onePiece = new ArrayList<>();
        onePiece.add("b.B;0.0");
        assertEquals(onePiece, testBoard.getPieceSet());
        List<String> proposedSet = new ArrayList<>();
        proposedSet.add("b.B;0.0");
        assertTrue(testBoard.check(proposedSet));
        testBoard.genSet(0,1,"W","b");
        assertFalse(testBoard.check(proposedSet));
        proposedSet.add("b.W;0.1");
        assertTrue(testBoard.check(proposedSet));
    }

    @Test
    public void testGenNextPos(){
        Board testBoard = new Board(2);
        testBoard.boardCreate("empty");
        assertTrue(testBoard.getBoard().get(0).equals("X"));
        assertTrue(testBoard.getBoard().get(1).equals("X"));
        assertTrue(testBoard.getBoard().get(2).equals("X"));
        assertTrue(testBoard.getBoard().get(3).equals("X"));
        testBoard.genNextPos();
        assertFalse((testBoard.getBoard().get(0).equals("X")&&testBoard.getBoard().get(1).equals("X"))
                &&(testBoard.getBoard().get(2).equals("X")&&testBoard.getBoard().get(3).equals("X")));
        testBoard.genNextPos();
        testBoard.genNextPos();
        testBoard.genNextPos();
        assertTrue((!testBoard.getBoard().get(0).equals("X")&&!testBoard.getBoard().get(1).equals("X"))
                &&(!testBoard.getBoard().get(2).equals("X")&&!testBoard.getBoard().get(3).equals("X")));
    }

    @Test
    public void boardLoad(){
        Board testBoard = new Board(2 );
        List<String> pieceSet = new ArrayList<>();
        pieceSet.add("b.B;0.0");
        pieceSet.add("w.Q;1.0");
        pieceSet.add("w.K;1.1");
        pieceSet.add("w.K;0.1");
        testBoard.boardLoad(pieceSet);
        List<String> expectedBoard = new ArrayList<>();
        expectedBoard.add("b.B");
        expectedBoard.add("w.Q");
        expectedBoard.add("w.K");
        expectedBoard.add("w.K");
        assertEquals(expectedBoard, testBoard.getBoard());
        testBoard.setDifficulty(4000);
        assertEquals(4000, testBoard.getDifficulty());
        testBoard.boardSetComplete();
        assertEquals(testBoard.getComplete(), true);
    }

    @Test
    public void testLoadSet(){
        Board testBoard = new Board(2);
        List<String> pieces = new ArrayList<>();
        pieces.add("w.R");
        pieces.add("w.Q");
        pieces.add("w.Q");
        pieces.add("X");
        testBoard.setBoard(pieces);
        testBoard.loadSet();
        List<String> set = new ArrayList<>();
        set.add("w.R;0.0");
        set.add("w.Q;1.0");
        set.add("w.Q;0.1");
        assertEquals(set, testBoard.getPieceSet());




    }


    @Test
    public void toJson(){
        Board board = new Board(2);
        board.genRowsAndColumns();
        List<String> prevBoard = board.getBoard();
        List<String> pieceSet = new ArrayList<>();


        assertEquals("{\"slots\":2,\"boardState\":[\"X\",\"X\",\"X\",\"X\"],\"positions\":[],\"complete\":false}",
                board.toJson().toString());
    }

}