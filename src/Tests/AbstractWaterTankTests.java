package Tests;

import classes.Direction;
import classes.Field;
import classes.Water;
import classes.entities.water_tanks.AbstractWaterTank;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractWaterTankTests {

    @Test
    public void testAddPossibleDirection() {
        AbstractWaterTank abstractWaterTank = new AbstractWaterTank();
//        waterTank.addPossibleDirection(Direction.UP);
//        waterTank.addPossibleDirection(Direction.LEFT);
//        waterTank.addPossibleDirection(Direction.RIGHT);
//        assertEquals(3, waterTank.getPossibleDirections().size());
//        assertTrue(waterTank.getPossibleDirections().contains(Direction.UP));
//        assertTrue(waterTank.getPossibleDirections().contains(Direction.LEFT));
//        assertTrue(waterTank.getPossibleDirections().contains(Direction.RIGHT));
    }

    @Test
    public void testAddPossibleDirections() {
        AbstractWaterTank abstractWaterTank = new AbstractWaterTank();
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);
//        waterTank.addPossibleDirections(directions);
//        assertEquals(3, waterTank.getPossibleDirections().size());
//        assertTrue(waterTank.getPossibleDirections().contains(Direction.UP));
//        assertTrue(waterTank.getPossibleDirections().contains(Direction.DOWN));
//        assertTrue(waterTank.getPossibleDirections().contains(Direction.LEFT));
    }

    @Test
    public void testRemovePossibleDirection() {
        AbstractWaterTank abstractWaterTank = new AbstractWaterTank();
//        waterTank.addPossibleDirection(Direction.UP);
//        waterTank.addPossibleDirection(Direction.LEFT);
//        waterTank.addPossibleDirection(Direction.RIGHT);
//        waterTank.removePossibleDirection(Direction.LEFT);
//        assertEquals(2, waterTank.getPossibleDirections().size());
//        assertTrue(waterTank.getPossibleDirections().contains(Direction.UP));
//        assertTrue(waterTank.getPossibleDirections().contains(Direction.RIGHT));
    }

    @Test
    public void testRemovePossibleDirections() {
        AbstractWaterTank abstractWaterTank = new AbstractWaterTank();
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);
//        waterTank.addPossibleDirections(directions);
//        waterTank.removePossibleDirections(directions);
//        assertEquals(0, waterTank.getPossibleDirections().size());
    }

    @Test
    public void testClearPossibleDirections() {
        AbstractWaterTank abstractWaterTank = new AbstractWaterTank();
//        waterTank.addPossibleDirection(Direction.UP);
//        waterTank.addPossibleDirection(Direction.LEFT);
//        waterTank.addPossibleDirection(Direction.RIGHT);
//        waterTank.clearPossibleDirections();
//        assertEquals(0, waterTank.getPossibleDirections().size());
    }

    @Test
    public void testGetConnectedFromOneDirection() throws IOException {
        String level = "3 3\n" +
                "s╣□\n" +
                "□╬□\n" +
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
        
        AbstractWaterTank start = field.getPipeOnCords(new Point(1, 1));
        HashMap<Direction, AbstractWaterTank> expectedConnected = new HashMap<>();
        expectedConnected.put(Direction.UP, field.getPipeOnCords(new Point(1, 0)));

        HashMap<Direction, AbstractWaterTank> actualConnected = start.getConnectedWaterTanks();
        assertEquals(expectedConnected.size(), actualConnected.size());
        
        for (Direction direction : expectedConnected.keySet()) {
            assertTrue(actualConnected.containsKey(direction));
            assertEquals(expectedConnected.get(direction), actualConnected.get(direction));
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void getConnectedFromTwoDirections() throws IOException {
        String level = "3 3\n" +
                "s╣□\n" +
                "□╬□\n" +
                "d╣□\n";
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

        AbstractWaterTank start = field.getPipeOnCords(new Point(1, 1));
        HashMap<Direction, AbstractWaterTank> expectedConnected = new HashMap<>();
        expectedConnected.put(Direction.UP, field.getPipeOnCords(new Point(1, 0)));
        expectedConnected.put(Direction.DOWN, field.getPipeOnCords(new Point(1, 2)));

        HashMap<Direction, AbstractWaterTank> actualConnected = start.getConnectedWaterTanks();
        assertEquals(expectedConnected.size(), actualConnected.size());

        for (Direction direction : expectedConnected.keySet()) {
            assertTrue(actualConnected.containsKey(direction));
            assertEquals(expectedConnected.get(direction), actualConnected.get(direction));
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void getConnectedFromThreeDirections() throws IOException {
        String level = "3 3\n" +
                "s╣□\n" +
                "□╬╣\n" +
                "d╣□\n";
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

        AbstractWaterTank start = field.getPipeOnCords(new Point(1, 1));
        HashMap<Direction, AbstractWaterTank> expectedConnected = new HashMap<>();
        expectedConnected.put(Direction.UP, field.getPipeOnCords(new Point(1, 0)));
        expectedConnected.put(Direction.DOWN, field.getPipeOnCords(new Point(1, 2)));
        expectedConnected.put(Direction.RIGHT, field.getPipeOnCords(new Point(2, 1)));

        HashMap<Direction, AbstractWaterTank> actualConnected = start.getConnectedWaterTanks();
        assertEquals(expectedConnected.size(), actualConnected.size());

        for (Direction direction : expectedConnected.keySet()) {
            assertTrue(actualConnected.containsKey(direction));
            assertEquals(expectedConnected.get(direction), actualConnected.get(direction));
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void getConnectedFromFourDirections() throws IOException {
        String level = "3 3\n" +
                "s╣□\n" +
                "╠╬╣\n" +
                "d╣□\n";
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

        AbstractWaterTank start = field.getPipeOnCords(new Point(1, 1));
        HashMap<Direction, AbstractWaterTank> expectedConnected = new HashMap<>();
        expectedConnected.put(Direction.UP, field.getPipeOnCords(new Point(1, 0)));
        expectedConnected.put(Direction.DOWN, field.getPipeOnCords(new Point(1, 2)));
        expectedConnected.put(Direction.RIGHT, field.getPipeOnCords(new Point(2, 1)));
        expectedConnected.put(Direction.LEFT, field.getPipeOnCords(new Point(0, 1)));

        HashMap<Direction, AbstractWaterTank> actualConnected = start.getConnectedWaterTanks();
        assertEquals(expectedConnected.size(), actualConnected.size());

        for (Direction direction : expectedConnected.keySet()) {
            assertTrue(actualConnected.containsKey(direction));
            assertEquals(expectedConnected.get(direction), actualConnected.get(direction));
        }

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void tryToGetConnectedPipesAtTheCorner() throws IOException {
        String level = "3 3\n" +
                "s□╬\n" +
                "□□╬\n" +
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

        AbstractWaterTank start = field.getPipeOnCords(new Point(2, 0));
        HashMap<Direction, AbstractWaterTank> expectedConnected = new HashMap<>();
        expectedConnected.put(Direction.DOWN, field.getPipeOnCords(new Point(2, 1)));

        HashMap<Direction, AbstractWaterTank> actualConnected = start.getConnectedWaterTanks();
        assertEquals(expectedConnected.size(), actualConnected.size());

        for (Direction direction : expectedConnected.keySet()) {
            assertTrue(actualConnected.containsKey(direction));
            assertEquals(expectedConnected.get(direction), actualConnected.get(direction));
        }

        // delete file
        File file = new File(fileName);
        file.delete();

    }

    @Test
    void fillFromOnePossibleDirection() {
        AbstractWaterTank source = new AbstractWaterTank();
        Water water = new Water(source);
        AbstractWaterTank testAbstractWaterTank = new AbstractWaterTank();
//        testWaterTank.addPossibleDirection(Direction.UP);
//
//        boolean isFilled = testWaterTank.fillFromDirection(Direction.UP, water);
//        assertTrue(isFilled);
        assertEquals(water, testAbstractWaterTank.getWater());
    }

    @Test
    void fillFromNotPossibleDirection() {
        AbstractWaterTank source = new AbstractWaterTank();
        Water water = new Water(source);
        AbstractWaterTank testAbstractWaterTank = new AbstractWaterTank();
//        testWaterTank.addPossibleDirection(Direction.UP);
//
//        boolean isFilled = testWaterTank.fillFromDirection(Direction.DOWN, water);
//        assertFalse(isFilled);
        assertNull(testAbstractWaterTank.getWater());
    }

    @Test
    void doubleFilled() {
        AbstractWaterTank source = new AbstractWaterTank();
        Water water1 = new Water(source);
        Water water2 = new Water(source);
        AbstractWaterTank testAbstractWaterTank = new AbstractWaterTank();
//        testWaterTank.addPossibleDirection(Direction.UP);

//        boolean isFilled1 = testWaterTank.fillFromDirection(Direction.UP, water1);
//        boolean isFilled2 = testWaterTank.fillFromDirection(Direction.UP, water2);
//        assertTrue(isFilled1);
//        assertFalse(isFilled2);

        assertEquals(water1, testAbstractWaterTank.getWater());
    }

    @Test
    public void testToString() {
        AbstractWaterTank abstractWaterTank = new AbstractWaterTank();
//        waterTank.addPossibleDirection(Direction.DOWN);
//        waterTank.addPossibleDirection(Direction.LEFT);
//        waterTank.addPossibleDirection(Direction.RIGHT);
        assertEquals("╦", abstractWaterTank.toString());
    }
}