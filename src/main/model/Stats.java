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
        EventLog.getInstance().logEvent(new Event("New 'Stats' is made"));
    }

    // Modifies: This
    // Effects: Adds individual board statistic to the list of statistics.
    public void addStat(BoardStats statistic) {
        this.stats.add(statistic);
        EventLog.getInstance().logEvent(new Event(statistic + "added"));
    }

    // Effects: Finds the highest streak user has obtained.
    public Integer highestStreak() {
        List<Integer> streaks = new ArrayList<>();
        for (BoardStats boardstats : stats) {
            Integer streak = boardstats.getStreak();
            streaks.add(streak);
            EventLog.getInstance().logEvent(new Event(streak + "added to streaks"));
        }
        Integer maxStreak = Collections.max(streaks);
        EventLog.getInstance().logEvent(new Event("max streak is calculated"));
        return maxStreak;
    }

    // Effects: Finds the largest game size the user has played.
    public Integer biggestSize() {
        List<Integer> sizes = new ArrayList<>();
        for (BoardStats boardstats : stats) {
            Integer size = boardstats.getSize();
            EventLog.getInstance().logEvent(new Event(size + "is added to sizes"));
            sizes.add(size);
        }
        Integer maxSize = Collections.max(sizes);
        EventLog.getInstance().logEvent(new Event("Max size is found"));
        return maxSize;
    }


    // Getter
    public List<BoardStats> returnStats() {
        EventLog.getInstance().logEvent(new Event("Stats is returned"));
        return stats;
    }

    // Effects: Removes certain boardStat from stats.
    public void statsRemove(BoardStats i) {
        EventLog.getInstance().logEvent(new Event("BoardStat: " + i + " is removed"));
        stats.remove(i);
    }

    // Getter
    public Integer statsSize() {
        EventLog.getInstance().logEvent(new Event("Stat size: " + stats.size() + " is returned"));
        return stats.size();
    }

    // Effects: Converts stats to Json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("stats", statsToJson());
        EventLog.getInstance().logEvent(new Event("Stats converted to Json"));
        return json;
    }

    // Effects: Converts stats array, into BoardStats to Json.
    public JSONArray statsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (BoardStats stat : stats) {
            EventLog.getInstance().logEvent(new Event("Stat added to JSONArray"));
            jsonArray.put(stat.toJson());
        }
        return jsonArray;
    }


    // Effects: Sets stat as specific boardStat.
    // Modifies: This
    public void setStat(BoardStats boardStats) {
        if (statsSize() == 0) {
            EventLog.getInstance().logEvent(new Event("BoardStat: " + boardStats + " added"));
            stats.add(boardStats);
        }
        EventLog.getInstance().logEvent(new Event("Stat set at index " + (statsSize() - 1) + "setting as: "
                + boardStats));
        stats.set(statsSize() - 1, boardStats);
    }
}
