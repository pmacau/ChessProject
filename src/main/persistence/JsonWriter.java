package persistence;



import model.Board;
import model.BoardStats;
import model.Stats;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Writes Json files.
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // Effects: Writes to destination file.
    public JsonWriter(String jsonStore) {
        this.destination = jsonStore;
    }

    // Effects: Opens file.
    // throws FileNotFoundException if file doesn't exist in path given.
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // Effects: Writes board to Json.
    public void writeBoard(Board board) {
        JSONObject json = board.toJson();
        saveToFile(json.toString(TAB));
    }

    // Effects: Closes writer
    public void close() {
        writer.close();
    }


    // Effects: Writes Stats.
    public void writeStats(Stats stats) {
        JSONObject json = stats.toJson();
        saveToFile(json.toString(TAB));
    }

    // Effects: Saves to file.
    private void saveToFile(String json) {
        writer.print(json);
    }
}


