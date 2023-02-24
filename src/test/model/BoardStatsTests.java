package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardStatsTests {

    private BoardStats boardStat;

    @BeforeEach

    public void beforeEach(){
        boardStat = new BoardStats();
    }
    // Tests difficulty storage.
    @Test
    public void difficultyTest(){
        boardStat.difficulty("Hard");
        List<String> strings = boardStat.getTotalStat();
        assertEquals("Hard", strings.get(1));
    }
    // Tests how a user's guess is added to a list that ultimately sorts into most common piece guessed.
    @Test
    public void addGuessTest(){
        boardStat.addGuess("b.N;6.5");
        List<String> array1 = new ArrayList<>();
        array1.add("N");
        assertEquals(array1, boardStat.getGuess());
        boardStat.addGuess("b.N;6.5,w.K;5.5");
        array1.add("K");
        assertEquals(array1, boardStat.getGuess());
        boardStat.addGuess("b.N;6.5,w.K;5.5,w.Q;6.7");
        array1.add("Q");
        assertEquals(array1, boardStat.getGuess());
    }

    // tests the streak statistic mechanism which just shows how many you got right on a board/in a row.
    @Test
    public void streakTest(){
        boardStat.streak();
        List<String> listStats = boardStat.getTotalStat();
        assertEquals("1", listStats.get(5));
        boardStat.streak();
        assertEquals("2", listStats.get(5));
    }
    // board size statistic mechanism tested.
    @Test
    public void boardSizeTest(){
        boardStat.boardSize(5);
        List<String> listStats = boardStat.getTotalStat();
        assertEquals("5", listStats.get(3));
        boardStat.boardSize(6);
        assertEquals("6", listStats.get(3));
    }
    // similar to addGuess, however tests for the final sorted list which is the most common piece added.
    @Test
    public void updateGuess(){
        boardStat.addGuess("b.N;6.5");
        List<String> array1 = new ArrayList<>();
        array1.add("N");
        assertEquals(array1, boardStat.getGuess());
        boardStat.addGuess("b.N;6.5,w.K;5.5");
        array1.add("K");
        assertEquals(array1, boardStat.getGuess());
        boardStat.addGuess("b.N;6.5,w.K;5.5,w.Q;6.7");
        array1.add("Q");
        boardStat.addGuess("b.N;6.5,w.K;5.5,w.Q;6.7,b.B;6.6");
        array1.add("B");
        boardStat.addGuess("b.N;6.5,w.K;5.5,w.Q;6.7,b.B;6.6,b.P;5.5,w.R;7.7");
        array1.add("R");
        boardStat.addGuess("b.N;6.5,w.K;5.5,w.Q;6.7,b.B;6.6,b.P;5.5,w.R;7.7,w.P;7.8");
        array1.add("P");
        boardStat.addGuess("b.N;6.5,w.K;5.5,w.Q;6.7,b.B;6.6,b.P;5.5,w.R;7.7,w.P;7.8,b.N;6.8");
        array1.add("N");
        assertEquals(array1, boardStat.getGuess());
        assertEquals("Knight", boardStat.getTotalStat().get(7));
        boardStat.addGuess("b.N;6.5,w.K;5.5,w.Q;6.7,b.B;6.6,b.P;5.5,w.R;7.7,w.P;7.8,b.N;6.8,w.R;8.8");
        assertEquals("Knight", boardStat.getTotalStat().get(7));
        boardStat.addGuess("b.N;6.5,w.K;5.5,w.Q;6.7,b.B;6.6,b.P;5.5,w.R;7.7,w.P;7.8,b.N;6.8,w.R;8.8,w.R;9.9");
        assertEquals("Rook", boardStat.getTotalStat().get(7));

        BoardStats testingAllPossibleMax = new BoardStats();
        testingAllPossibleMax.addGuess("b.P;1.1");
        assertEquals("Pawn", testingAllPossibleMax.getTotalStat().get(7));

        BoardStats testingAllPossibleMax2 = new BoardStats();
        testingAllPossibleMax2.addGuess("b.K;1.1,b.K;1.2");
        assertEquals("King", testingAllPossibleMax2.getTotalStat().get(7));
        BoardStats testingAllPossibleMax3 = new BoardStats();
        testingAllPossibleMax3.addGuess("b.Q;1.1");
        assertEquals("Queen", testingAllPossibleMax3.getTotalStat().get(7));
        BoardStats testingAllPossibleMax4 = new BoardStats();
        testingAllPossibleMax4.addGuess("b.K;1.1,b.K;1.1");
        assertEquals("King", testingAllPossibleMax4.getTotalStat().get(7));
        BoardStats testingAllPossibleMax5 = new BoardStats();
        testingAllPossibleMax5.addGuess("b.B;1.1");
        assertEquals("Bishop", testingAllPossibleMax5.getTotalStat().get(7));
    }


    @Test
    public void emptyGuess(){
        boardStat.sortGuessByMax(0, 0, 0,0,0,0);
        boardStat.getTotalStat();
        assertEquals("N/A", boardStat.getTotalStat().get(7));
        boardStat.sortGuessByMax(1, 0, 0,0,0,0);
        assertEquals("King", boardStat.getTotalStat().get(7));
    }



}
