package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StatsTest {
    private Stats testStats;

    @BeforeEach

    public void constructStats() {
        testStats = new Stats();
    }

    @Test
    // testing constructor
    public void constructTestStats() {
        List<BoardStats> emptyList = new ArrayList<>();
        assertEquals(emptyList, testStats.returnStats());
        BoardStats input = new BoardStats();
        testStats.addStat(input);
        List<BoardStats> statList = new ArrayList<>();
        statList.add(input);
        assertEquals(statList, testStats.returnStats());
        BoardStats input2 = new BoardStats();
        testStats.addStat(input2);
        statList.add(input2);
        assertEquals(statList, testStats.returnStats());
        assertEquals(2, testStats.statsSize());
    }

    @Test
    public void statRemove(){
        BoardStats in = new BoardStats();
        testStats.addStat(in);
        assertEquals(1 , testStats.statsSize());
        testStats.statsRemove(in);
        List<BoardStats> empty = new ArrayList<>();
        assertEquals(empty, testStats.returnStats());
        assertEquals(0 , testStats.statsSize());
        testStats.setStat(in);
        List<BoardStats> in1 = new ArrayList<>();
        in1.add(in);
        assertEquals(in1, testStats.returnStats());
        testStats.setStat(in);
        assertEquals(in1, testStats.returnStats());
    }


    @Test
    // testing biggestSize, and maxStreak
    public void sizesAndMaxStreak(){
        BoardStats boardStat1 = new BoardStats();
        BoardStats boardStat2 = new BoardStats();
        boardStat1.streak();
        boardStat1.streak(); // 2 is the streak
        boardStat2.streak(); // 1 is the streak
        assertEquals(2, boardStat1.getStreak());
        assertEquals(1, boardStat2.getStreak());
        boardStat1.boardSize(6); // 6 is the size
        boardStat2.boardSize(5); // 5 is the size
        assertEquals(6, boardStat1.getSize());
        assertEquals(5, boardStat2.getSize());
        testStats.addStat(boardStat1);
        testStats.addStat(boardStat2);
        assertEquals(2, testStats.highestStreak());
        assertEquals(6, testStats.biggestSize());
        boardStat2.streak();
        assertEquals(2, testStats.highestStreak());
        boardStat2.streak();
        assertEquals(3, testStats.highestStreak());
        boardStat2.boardSize(10);
        assertEquals(10, testStats.biggestSize());
    }

    @Test
    public void statsToJson(){
        Stats stats = new Stats();
        assertEquals("[]", stats.statsToJson().toString());
        BoardStats boardStat1 = new BoardStats();
        BoardStats boardStat2 = new BoardStats();
        boardStat1.streak();
        boardStat1.streak(); // 2 is the streak
        boardStat2.streak(); // 1 is the streak
        boardStat1.boardSize(6); // 6 is the size
        boardStat2.boardSize(5); // 5 is the size
        stats.addStat(boardStat1);
        stats.addStat(boardStat2);
        assertEquals("{\"stats\":[{\"Guesses\":[],\"Streak\":\"2\",\"ForMost\":[],\"Difficulty\":\"N/A\",\"Size\":\"6\"," +
                "\"Most Guessed Piece\":\"N/A\"},"
        + "{\"Guesses\":[],\"Streak\":\"1\",\"ForMost\":[],\"Difficulty\":\"N/A\",\"Size\":\"5\",\"Most Guessed Piece\""
               + ":\"N/A\"}]}", stats.toJson().toString());
    }


}
