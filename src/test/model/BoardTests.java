package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTests {
    // delete or rename this class!

    @Test
    //tests the constructor.
    public void dimensionTest(){
        Board board = new Board(3);
        assertEquals(9, board.getSlots());
        Board boardBase = new Board(1);
        assertEquals(1, boardBase.getSlots());
    }
    @Test
    // tests the generator method implementation
    public void generatorTest(){
        Board genBoard = new Board ( 3 );
       // assertEquals(5 , genBoard.generate(genBoard.getSlots()));
    }

}