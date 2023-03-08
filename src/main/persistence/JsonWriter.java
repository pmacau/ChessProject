package persistence;

// Note: Structured from JsonSerialization example.

import model.Board;
import model.BoardStats;
import model.Stats;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String jsonStore) {
        this.destination = jsonStore;
    }

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    public void writeBoard(Board board) {
        JSONObject json = board.toJson();
        saveToFile(json.toString(TAB));
    }

    public void close() {
        writer.close();
    }

    public void writeBoardStat(BoardStats boardStats) {
    }

    public void writeStats(Stats stats) {
        JSONObject json = stats.toJson();
        saveToFile(json.toString(TAB));
    }

    private void saveToFile(String json) {
        writer.print(json);
    }
}
