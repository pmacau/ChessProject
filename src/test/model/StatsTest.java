package model;

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
    }


}
