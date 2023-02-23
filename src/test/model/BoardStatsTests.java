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

    @Test
    public void addGuess(){
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
}
