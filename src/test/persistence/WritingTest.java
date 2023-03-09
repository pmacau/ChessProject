package persistence;

import model.Board;
import model.BoardStats;
import model.Stats;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WritingTest {
    @Test
    public void testWriterInvalidFile() {
        try {
            Board b = new Board(2);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyWorkroom() {
        try {
            Board b = new Board(2);
            b.setDifficulty(8000);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.writeBoard(b);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            b = reader.readBoard();
            assertEquals(4, b.getSlots());
            List<String> empty = new ArrayList<>();
            assertEquals(empty, b.getPieceSet());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    public void testWriterGeneralWorkroom() {
        try {
            Board b = new Board(2);
            b.setDifficulty(4000);
            b.genRowsAndColumns();
            b.genNextPos();
            b.genNextPos();
            b.genNextPos();
            List<String> prev = b.getBoard();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.writeBoard(b);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            b = reader.readBoard();
            assertEquals(4, b.getSlots());
            assertEquals(prev, b.getBoard());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void StatsWriting() {
        try {
            JsonWriter writer = new JsonWriter("./data/testToStats.json");
            writer.open();
            Stats stat = new Stats();
            BoardStats stats1 = new BoardStats();
            stats1.addPiecesGuessed("Q");
            stats1.addPiecesGuessed("Q");
            stats1.difficulty("Hard");
            stats1.boardSize(4);
            stats1.streak();
            stats1.streak();
            stats1.streak();
            stats1.userGuesses("b.B;0.0");
            stats1.userGuesses("w.B;0.0");
            stat.addStat(stats1);
            writer.writeStats(stat);
            writer.close();
        } catch (IOException e) {
            System.out.println("Shouldn't happen");
        }


        JsonReader jsonReader = new JsonReader("./data/testToStats.json");

        try {
            Stats stat3 = jsonReader.readStats();
            assertEquals(4, stat3.biggestSize());

            assertEquals(3, stat3.highestStreak());

        } catch (
                IOException ex) {
            throw new RuntimeException(ex);
        }

    }

}

