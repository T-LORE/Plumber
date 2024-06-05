package Tests;
import classes.Diameter;
import classes.Direction;
import classes.Field;
import classes.MaterialNode;
import classes.Water;
import classes.entities.water_tanks.AbstractWaterTank;
import classes.entities.water_tanks.Pipe;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;
import classes.entities.water_tanks.water_tanks_ends.MaterialWaterTankEnd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.lang.Thread.sleep;

public class PipeTests {
    Pipe _pipe;
    @BeforeEach
    public void setUp() {
        MaterialNode.configure();
        _pipe = new Pipe();
        _pipe.setMaterial(MaterialNode.getRoot());
        _pipe.setDiameter(new Diameter(10));
        Water.setWaterDelay(1);
    }

    @Test
    public void testAddEnd() {
        MaterialWaterTankEnd expectedEnd = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10));
        _pipe.addEnd(expectedEnd);
        assertEquals(1, _pipe.getEnds().size());
        assertEquals(expectedEnd, _pipe.getEnd(Direction.UP));
    }

    @Test
    public void testAddEnds() {
        ArrayList<AbstractWaterTankEnd> ends = new ArrayList<>();
        MaterialWaterTankEnd expectedEnd1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10));
        MaterialWaterTankEnd expectedEnd2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getRoot(), new Diameter(10));
        ends.add(expectedEnd1);
        ends.add(expectedEnd2);
        _pipe.addEnds(ends);
        assertEquals(2, _pipe.getEnds().size());
        assertEquals(expectedEnd1, _pipe.getEnd(Direction.UP));
        assertEquals(expectedEnd2, _pipe.getEnd(Direction.DOWN));
    }

    @Test
    public void testRemoveEnd() {
        MaterialWaterTankEnd expectedEnd = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10));
        _pipe.addEnd(expectedEnd);
        _pipe.removeEnd(expectedEnd);
        assertEquals(0, _pipe.getEnds().size());
        assertEquals(null, _pipe.getEnd(Direction.UP));
    }

    @Test
    public void testRemoveEnds(){
        ArrayList<AbstractWaterTankEnd> ends = new ArrayList<>();
        MaterialWaterTankEnd expectedEnd1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10));
        MaterialWaterTankEnd expectedEnd2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getRoot(), new Diameter(10));
        ends.add(expectedEnd1);
        ends.add(expectedEnd2);
        _pipe.addEnds(ends);
        _pipe.removeEnds(ends);
        assertEquals(0, _pipe.getEnds().size());
        assertEquals(null, _pipe.getEnd(Direction.UP));
        assertEquals(null, _pipe.getEnd(Direction.DOWN));
    }

    @Test
    public void testClearEnds(){
        MaterialWaterTankEnd expectedEnd1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10));
        MaterialWaterTankEnd expectedEnd2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getRoot(), new Diameter(10));
        _pipe.addEnd(expectedEnd1);
        _pipe.addEnd(expectedEnd2);
        _pipe.clearEnds();
        assertEquals(0, _pipe.getEnds().size());
        assertEquals(null, _pipe.getEnd(Direction.UP));
        assertEquals(null, _pipe.getEnd(Direction.DOWN));
    }

    @Test
    public void testGetConnectedFromOneDirection() throws IOException {
        String level = "3 3\n" +
                "sp□\n" +
                "□p□\n" +
                "d□□\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n";

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
        
        AbstractWaterTank start = field.getRotatableTankOnCords(new Point(1, 1));
        HashMap<Direction, AbstractWaterTank> expectedConnected = new HashMap<>();
        expectedConnected.put(Direction.UP, field.getRotatableTankOnCords(new Point(1, 0)));

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
                "sp□\n" +
                "□p□\n" +
                "dp□\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n";
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

        AbstractWaterTank start = field.getRotatableTankOnCords(new Point(1, 1));
        HashMap<Direction, AbstractWaterTank> expectedConnected = new HashMap<>();
        expectedConnected.put(Direction.UP, field.getRotatableTankOnCords(new Point(1, 0)));
        expectedConnected.put(Direction.DOWN, field.getRotatableTankOnCords(new Point(1, 2)));

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
                "sp□\n" +
                "□pp\n" +
                "dp□\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n";
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

        AbstractWaterTank start = field.getRotatableTankOnCords(new Point(1, 1));
        HashMap<Direction, AbstractWaterTank> expectedConnected = new HashMap<>();
        expectedConnected.put(Direction.UP, field.getRotatableTankOnCords(new Point(1, 0)));
        expectedConnected.put(Direction.DOWN, field.getRotatableTankOnCords(new Point(1, 2)));
        expectedConnected.put(Direction.RIGHT, field.getRotatableTankOnCords(new Point(2, 1)));

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
                "sp□\n" +
                "ppp\n" +
                "dp□\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n";
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

        AbstractWaterTank start = field.getRotatableTankOnCords(new Point(1, 1));
        HashMap<Direction, AbstractWaterTank> expectedConnected = new HashMap<>();
        expectedConnected.put(Direction.UP, field.getRotatableTankOnCords(new Point(1, 0)));
        expectedConnected.put(Direction.DOWN, field.getRotatableTankOnCords(new Point(1, 2)));
        expectedConnected.put(Direction.RIGHT, field.getRotatableTankOnCords(new Point(2, 1)));
        expectedConnected.put(Direction.LEFT, field.getRotatableTankOnCords(new Point(0, 1)));

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
                "s□p\n" +
                "□□p\n" +
                "d□□\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n";
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

        AbstractWaterTank start = field.getRotatableTankOnCords(new Point(2, 0));
        HashMap<Direction, AbstractWaterTank> expectedConnected = new HashMap<>();
        expectedConnected.put(Direction.DOWN, field.getRotatableTankOnCords(new Point(2, 1)));

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
    void fillFromOneEnd() throws Exception {
        String level = "3 3\n" +
                "sp□\n" +
                "□□□\n" +
                "d□□\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n";
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

        field.getSource().createWater().flow();
        sleep(10);

        Pipe pipe = field.getRotatableTankOnCords(new Point(1, 0));
        assertEquals(field.getSource().getWater(), pipe.getWater());

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void fillFromNotAnEnd() throws Exception {
        String level = "3 3\n" +
                "sp□\n" +
                "□□□\n" +
                "d□□\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;1\n" +
                "UNIVERSAL;10;1;1;1;0\n";
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

        field.getSource().createWater().flow();
        sleep(10);

        Pipe pipe = field.getRotatableTankOnCords(new Point(1, 0));
        assertEquals(null, pipe.getWater());

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void doubleFilled() {
        AbstractWaterTank source = new AbstractWaterTank();
        Water water1 = new Water(source);
        Water water2 = new Water(source);

        boolean isFilled1 = _pipe.fill(water1);
        boolean isFilled2 = _pipe.fill(water2);
        assertTrue(isFilled1);
        assertFalse(isFilled2);

        assertEquals(water1, _pipe.getWater());
    }

    @Test
    public void testToString() {
        _pipe.configureTankWithOneMaterial("UNIVERSAL;10;0;1;1;1", MaterialNode.getRoot());
        assertEquals("╦", _pipe.toString());
    }
    
    @Test
    public void testRotateClockwise() {
        _pipe.addEnd(new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10)));
        _pipe.addEnd(new MaterialWaterTankEnd(Direction.RIGHT, MaterialNode.getRoot(), new Diameter(10)));
        
        _pipe.rotateClockwise();

        assertNull(_pipe.getEnd(Direction.UP));
        assertNotNull(_pipe.getEnd(Direction.DOWN));
        assertNotNull(_pipe.getEnd(Direction.RIGHT));
    }

    @Test
    void zeroEndsRotationClockwise() {
        _pipe.rotateClockwise();
        assertEquals(0, _pipe.getEnds().size());
    }

    void allEndsPossible() {
        _pipe.addEnd(new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10)));
        _pipe.addEnd(new MaterialWaterTankEnd(Direction.RIGHT, MaterialNode.getRoot(), new Diameter(10)));
        _pipe.addEnd(new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getRoot(), new Diameter(10)));
        _pipe.addEnd(new MaterialWaterTankEnd(Direction.LEFT, MaterialNode.getRoot(), new Diameter(10)));

        _pipe.rotateClockwise();

        assertEquals(4, _pipe.getEnds().size());
    }
    
}
