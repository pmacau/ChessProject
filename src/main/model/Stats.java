package model;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    private List<BoardStats> stats;

    public Stats() {
        this.stats = new ArrayList<>();
    }

    public void addStat(BoardStats statistic) {
        this.stats.add(statistic);
    }

    public List<BoardStats> returnStats() {
        return stats;
    }
}
