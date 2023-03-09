package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Contains the statistics for all boards in a list, as well as calculated the highest streak of the session
// along the largest size of the session. Appears at the end of every board game.

public class Stats implements Writable {
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

    // Effects: Removes certain boardStat from stats.
    public void statsRemove(BoardStats i) {
        stats.remove(i);
    }

    // Getter
    public Integer statsSize() {
        return stats.size();
    }

    // Effects: Converts stats to Json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("stats", statsToJson());
        return json;
    }

    // Effects: Converts stats array, into BoardStats to Json.
    public JSONArray statsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (BoardStats stat : stats) {
            jsonArray.put(stat.toJson());
        }
        return jsonArray;
    }


    // Effects: Sets stat as specific boardStat.
    // Modifies: This
    public void setStat(BoardStats boardStats) {
        if (statsSize() == 0) {
            stats.add(boardStats);
        }
        stats.set(statsSize() - 1, boardStats);
    }
}
