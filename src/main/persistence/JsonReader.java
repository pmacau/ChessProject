package persistence;

import model.Board;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


// Note: Structured from JsonSerialization example.
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Board from file and then returns it.
    // throws IOException if an error occurs reading data from file
    // public Board read() throws IOException {
    //   String jsonData = readFile(source);
    //   JSONObject jsonObject = new JSONObject(jsonData);
    // return parseBoard(jsonObject);
    //   }


    // EFFECTS: parses Board from JSON object and returns it
    private Board parseWorkRoom(JSONObject jsonObject) {
        return null;
    }

    // taken directly from EdX example JsonSerialization.
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}
