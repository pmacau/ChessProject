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
// Reads Json files.
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

    // Effects: reads stats, and throws
    // IOException if an error occurs reading data from file
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


    // Effects: Sees if board is complete.
    public Boolean parseComplete() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonObject.getBoolean("complete");
    }

    // Effects: Parses boardStat json.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public BoardStats parseBoardStat(Object json) {
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
        JSONArray guessesForMost = jsonObject.getJSONArray("ForMost");
        int y = 0;
        for (Object guess : guessesForMost) {
            String g = guessesForMost.getString(y);
            y++;
            boardStats.addPiecesGuessed(g);
        }
        JSONArray positions = jsonObject.getJSONArray("Guesses");
        int x = 0;
        for (Object guess : positions) {
            String g = positions.getString(x);
            x++;
            boardStats.userGuesses(g);
        }
        return boardStats;
    }


    // EFFECTS: parses workroom from JSON object and returns it

    public Board parseBoard(JSONObject jsonObject) {
        Integer slots = jsonObject.getInt("slots");
        Integer difficulty = jsonObject.getInt("difficulty");
        Board board = new Board(slots);
        board.setDifficulty(difficulty);
        List<String> pieceSet = parsePosition(jsonObject);
        board.boardLoad(pieceSet);
        return board;
    }

    // Effects: Parses positions, and retrieves pieceSet.
    public List<String> parsePosition(JSONObject jsonObject) {
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
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}
