package model;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    private List<BoardStats> stats;

    public Stats() {
        this.stats = new ArrayList<>();
    }

    public void addStat(BoardStats statistics) {
        this.stats.add(statistics);
    }

    public List<BoardStats> returnStats() {
        return stats;
    }
}
