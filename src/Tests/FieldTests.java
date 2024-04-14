package Tests;
import classes.Cell;
import classes.Direction;
import classes.Field;
import classes.entities.water_tanks.Source;
import classes.entities.water_tanks.Drain;
import classes.entities.water_tanks.Pipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class FieldTests {

    private Field _field;

    @BeforeEach
    void setUp() {
        _field = new Field(3, 3);
    }

    @Test
    void testFieldInitialization() {
        assertNotNull(_field);
        assertEquals(3, _field.getHeight());
        assertEquals(3, _field.getWidth());
    }
    
    @Test
    void testSourceLoadFromFile() throws IOException {
        // prepare file
        String level = "3 3\n" +
                "s□□\n" +
                "□□□\n" +
                "d□□\n";
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        Field field = Field.loadFromFile(fileName);
        assertNotNull(field);
        assertEquals(3, field.getHeight());
        assertEquals(3, field.getWidth());
        // check entities
        Source source = field.getSource();
        assertNotNull(source);
        assertEquals(new Point(0, 0), source.getCell().getCoords());

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void testDrainLoadFromFile() throws IOException {
        // prepare file
        String level = "3 3\n" +
                "s□□\n" +
                "□□□\n" +
                "d□□\n";
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        Field field = Field.loadFromFile(fileName);
        assertNotNull(field);
        assertEquals(3, field.getHeight());
        assertEquals(3, field.getWidth());
        // check entities
        Drain drain = field.getDrain();
        assertNotNull(drain);
        assertEquals(new Point(0, 2), drain.getCell().getCoords());

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void testZeroFieldLoadFromFile() throws IOException {
        // prepare file
        String level = "0 0\n";
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        try {
            Field field = Field.loadFromFile(fileName);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void testNegativeFieldLoadFromFile() throws IOException {
        // prepare file
        String level = "-1 -1\n";
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        try {
            Field field = Field.loadFromFile(fileName);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void testInvalidSymbolField() throws IOException {
        // prepare file
        String level = "3 3\n" +
                "□□□\n" +
                "□□□\n" +
                "x□□\n";
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        try {
            Field field = Field.loadFromFile(fileName);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // delete file
        File file = new File(fileName);
        file.delete();
       
    }

    @Test
    void veryBigFieldLoadFromFile() throws IOException {
        // prepare file
        int xSize = 10;
        int ySize = 10;
        StringBuilder level = new StringBuilder();
        level.append(xSize).append(" ").append(ySize).append("\n");
        level.append("s");
        level.append("d");
        for (int i = 0; i < xSize-2; i++) {
            level.append("□");
        }
        level.append("\n");

        for (int i = 1; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                level.append("□");
            }
            level.append("\n");
        }
        
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level.toString());
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        Field field = Field.loadFromFile(fileName);
        assertNotNull(field);
        assertEquals(ySize, field.getHeight());
        assertEquals(xSize, field.getWidth());

        //check every cell
        Source source = field.getSource();
        assertNotNull(source);
        assertEquals(new Point(0, 0), source.getCell().getCoords());

        Drain drain = field.getDrain();
        assertNotNull(drain);
        assertEquals(new Point(1, 0), drain.getCell().getCoords());

        for (int j = 2; j < xSize; j++) {
            Cell cell = field.getCell(new Point(0, j));
            assertNotNull(cell);
            assertNull(cell.getEntity());
        }
        for (int y = 1; y < ySize-1; y++) {
            for (int x = 0; x < xSize; x++) {
                Cell cell = field.getCell(new Point(x, y));
                assertNotNull(cell);
                assertNull(cell.getEntity());
            }
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }
    @Test
    void testComplexLoadFromFile() throws IOException {
        // prepare file
        String level = "5 5\n" +
                "□□d□□\n" +
                "□╔╣□□\n" +
                "□║□□□\n" +
                "□║□□□\n" +
                "□s□□□\n";
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        Field field = Field.loadFromFile(fileName);
        assertNotNull(field);
        assertEquals(5, field.getHeight());
        assertEquals(5, field.getWidth());
        // check entities
        Drain drain = field.getDrain();
        assertNotNull(drain);
        assertEquals(new Point(2, 0), drain.getCell().getCoords());

        Source source = field.getSource();
        assertNotNull(source);
        assertEquals(new Point(1, 4), source.getCell().getCoords());

        Pipe pipe = field.getPipeOnCords(new Point(1, 2));
        assertNotNull(pipe);
        ArrayList<Direction> pipeExpectedDirections = new ArrayList<>();
        pipeExpectedDirections.add(Direction.UP);
        pipeExpectedDirections.add(Direction.DOWN);
        pipeExpectedDirections.add(Direction.LEFT);
        for (Direction direction : pipe.getPossibleDirections()) {
            assertTrue(pipeExpectedDirections.contains(direction));
        }

        pipe = field.getPipeOnCords(new Point(1, 1));
        assertNotNull(pipe);
        pipeExpectedDirections = new ArrayList<>();
        pipeExpectedDirections.add(Direction.RIGHT);
        pipeExpectedDirections.add(Direction.DOWN);
        for (Direction direction : pipe.getPossibleDirections()) {
            assertTrue(pipeExpectedDirections.contains(direction));
        }

        
        pipe = field.getPipeOnCords(new Point(1, 2));
        assertNotNull(pipe);
        pipeExpectedDirections = new ArrayList<>();
        pipeExpectedDirections.add(Direction.UP);
        pipeExpectedDirections.add(Direction.DOWN);
        for (Direction direction : pipe.getPossibleDirections()) {
            assertTrue(pipeExpectedDirections.contains(direction));
        }

        pipe = field.getPipeOnCords(new Point(1, 3));
        assertNotNull(pipe);
        pipeExpectedDirections = new ArrayList<>();
        pipeExpectedDirections.add(Direction.UP);
        pipeExpectedDirections.add(Direction.DOWN);
        for (Direction direction : pipe.getPossibleDirections()) {
            assertTrue(pipeExpectedDirections.contains(direction));
        }

        // delete file
        File file = new File(fileName);
        file.delete();

    }

    @Test
    void twoSourcesOnField() throws IOException {
        // prepare file
        String level = "3 3\n" +
                "s□□\n" +
                "□□□\n" +
                "s□□\n";
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        try {
            Field field = Field.loadFromFile(fileName);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void twoDrainsOnField() throws IOException {
        // prepare file
        String level = "3 3\n" +
                "d□□\n" +
                "□□□\n" +
                "d□□\n";
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        try {
            Field field = Field.loadFromFile(fileName);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void noSourcesOnField() throws IOException {
        // prepare file
        String level = "3 3\n" +
                "□□□\n" +
                "□□□\n" +
                "d□□\n";
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        try {
            Field field = Field.loadFromFile(fileName);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void noDrainsOnField() throws IOException {
        // prepare file
        String level = "3 3\n" +
                "s□□\n" +
                "□□□\n" +
                "□□□\n";
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        try {
            Field field = Field.loadFromFile(fileName);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void getPipeOnEmptyField() {
        assertNull(_field.getPipeOnCords(new Point(1, 1)));
    }

    @Test
    void getPipeTest() {
        Cell cell = new Cell(new Point(1, 1), _field);
        Pipe pipe = new Pipe();
        cell.setEntity(pipe);
        _field.setCell(cell);
        assertEquals(pipe, _field.getPipeOnCords(new Point(1, 1)));
    }

    @Test
    void setCell() {
        Cell cell = new Cell(new Point(1, 1), _field);
        _field.setCell(cell);
        assertEquals(cell, _field.getCell(new Point(1, 1)));
    }

    @Test
    void cellOutOfField() {
        Cell cell = new Cell(new Point(5, 5), _field);
        
        try {
            _field.setCell(cell);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    void negativeCellCords() {
        Cell cell = new Cell(new Point(-1, -1), _field);
        try {
            _field.setCell(cell);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    void setCellOnField() {
        Cell cell = new Cell();
        cell.setCords(new Point(1, 1));
        _field.setCell(cell);
        assertEquals(cell, _field.getCell(new Point(1, 1)));
    }

    @Test
    void toStringTest() {
        String expected = "□□□\n□□□\n□□□\n";
        assertEquals(expected, _field.toString());
    }


}