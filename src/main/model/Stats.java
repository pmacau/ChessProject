package model;

import java.util.ArrayList;
import java.util.Collections;
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

    // Effects: Finds the highest streak user has obtained.
    public Integer highestStreak() {
        List<Integer> streaks = new ArrayList<>();
        for (BoardStats boardstats : stats) {
            Integer streak = boardstats.getStreak();
            streaks.add(streak);
        }
        Integer maxStreak = Collections.max(streaks);
        return maxStreak;
    }

    // Effects: Finds the largest game size the user has played.
    public Integer biggestSize() {
        List<Integer> sizes = new ArrayList<>();
        for (BoardStats boardstats : stats) {
            Integer size = boardstats.getSize();
            sizes.add(size);
        }
        Integer maxSize = Collections.max(sizes);
        return maxSize;
    }


    // Getter
    public List<BoardStats> returnStats() {
        return stats;
    }

}
