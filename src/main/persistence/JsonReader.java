package persistence;

import model.Board;
import model.BoardStats;
import model.Stats;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


// Note: Structured from JsonSerialization example.
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Board readBoard() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBoard(jsonObject);
    }

    public Stats readStats() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStats(jsonObject);
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Stats parseStats(JSONObject jsonObject) {
        Stats stats = new Stats();
        JSONArray jsonArray = jsonObject.getJSONArray("stats");
        for (Object json : jsonArray) {
            stats.addStat(parseBoardStat(json));
        }
        return stats;
    }

    public Boolean parseComplete() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonObject.getBoolean("complete");
    }

    private BoardStats parseBoardStat(Object json) {
        BoardStats boardStats = new BoardStats();
        JSONObject jsonObject = (JSONObject) json;
        Integer streak = jsonObject.getInt("Streak");
        int i;
        for (i = 0; i < streak; i++) {
            boardStats.streak();
        }
        Integer size = jsonObject.getInt("Size");
        boardStats.boardSize(size);
        String difficulty = jsonObject.getString("Difficulty");
        boardStats.difficulty(difficulty);
        String mostGuessed = jsonObject.getString("Most Guessed Piece");
        boardStats.setGuess(mostGuessed);
        JSONArray positions = jsonObject.getJSONArray("Guesses");
        List<String> allGuesses = new ArrayList<>();
        int x = 0;
        for (Object guess : positions) {
            String g = positions.getString(x);
            x++;
            allGuesses.add(g);
        }
        return boardStats;
    }


    // EFFECTS: parses workroom from JSON object and returns it
    private Board parseBoard(JSONObject jsonObject) {
        Integer slots = jsonObject.getInt("slots");
        Integer difficulty = jsonObject.getInt("difficulty");
        Board board = new Board(slots);
        board.setDifficulty(difficulty);
        List<String> pieceSet = parsePosition(jsonObject);
        board.boardLoad(pieceSet);
        return board;
    }

    private List<String> parsePosition(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("positions");
        List<String> pieceSet = new ArrayList<>();
        int i = 0;
        for (Object json : jsonArray) {
            String piece = jsonArray.getString(i);
            i++;
            //JSONObject piece = (JSONObject) json;
            pieceSet.add(piece);
        }
        return pieceSet;
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