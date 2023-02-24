package model;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    private List<BoardStats> stats;

    // Modifies: This
    // Effects: Creates statistics for individual board games to be stored in.
    public Stats() {
        this.stats = new ArrayList<>();
    }

    // Modifies: This
    // Effects: Adds individual board statistic to the list of statistics.
    public void addStat(BoardStats statistic) {
        this.stats.add(statistic);
    }

    // Getter
    public List<BoardStats> returnStats() {
        return stats;
    }
}
