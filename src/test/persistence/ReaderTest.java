package persistence;

import model.Board;
import model.BoardStats;
import model.Stats;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    private static final String JSON_STORE = "./data/testReader.json";
    private JsonReader jsonReader;

    @BeforeEach
    public void construct(){
       jsonReader = new JsonReader(JSON_STORE);

    }

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readBoard();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testJSON_StoreFile(){
        try {
            Board board = jsonReader.readBoard();
            Boolean isComplete = jsonReader.parseComplete();
            assertEquals(false, board.getComplete());
            assertEquals(4, board.getSlots());
            assertEquals("w.R", board.getBoard().get(0));
            assertEquals("X", board.getBoard().get(3));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testJSON_emptyBoard(){
        String emptyBoard = "./data/testReaderEmptyBoard.json";
        JsonReader jso = new JsonReader(emptyBoard);
        try { Board board= jso.readBoard();}
        catch (IOException e){
            // passes since it should never have an empty position set.
        }

    }


    @Test
    public void parsePositionTest() {
        try {
            jsonReader.readBoard();
        } catch (IOException e) {
            System.out.println("Error in reading");
        }
    }

    @Test
    public void parseStatisticsTest(){
        JsonReader reader = new JsonReader("./data/StatisticsReaderTest.json");
        try {
            Stats stats = reader.readStats();
            assertEquals( 4,stats.returnStats().get(0).getSize());
            assertEquals( 3,stats.returnStats().get(0).getStreak());
        } catch (IOException e) {
            System.out.println("Shouldn't receive IOException");
        }


    }

    @Test
    public void parseBoardTest(){
        try {
            JsonReader reader = new JsonReader("./data/testToStats.json");
            String jsonData = reader.readFile("./data/testToStats.json");
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("stats");
            BoardStats boardStats = reader.parseBoardStat(jsonArray.get(0));
            assertEquals("Hard" ,boardStats.getTotalStat().get(1));
            assertEquals("3" ,boardStats.getTotalStat().get(5));
            List<String> userGuesses = new ArrayList<>();
            userGuesses.add("b.B;0.0");
            userGuesses.add("w.B;0.0");
            assertEquals(userGuesses ,boardStats.getUserGuesses());
        } catch (IOException e){
            System.out.println("Should not be getting IO");
        }
    }

}
