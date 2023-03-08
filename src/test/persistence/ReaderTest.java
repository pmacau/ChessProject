package persistence;

import model.Board;
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
            assertEquals(16, board.getSlots());
            assertEquals("b.P", board.getBoard().get(0));
            assertEquals("b.B", board.getBoard().get(4));
            assertEquals("X", board.getBoard().get(3));
        } catch (IOException e) {
            fail("Couldn't read from file");
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
}
