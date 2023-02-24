package model;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BoardTests {

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
        Board [] equivalentBoard = new Board[1000000];
        for (Integer i = 0; i < 1000000; i++) {
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
    public void duplicateCordTest(){

    }



}