package Tests.new_tests;
import classes.Diameter;
import classes.Direction;
import classes.Field;
import classes.MaterialNode;
import classes.Water;
import classes.MaterialNode.MaterialType;
import classes.entities.water_tanks.AbstractWaterTank;
import classes.entities.water_tanks.Fitting;
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

public class FittingTests {
    Fitting _fitting;
    @BeforeEach
    public void setUp() {
        MaterialNode.configure();
        _fitting = new Fitting();
        Water.setWaterDelay(1);
    }

    @Test
    public void equalMaterialEnds() {
        MaterialWaterTankEnd end1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getMaterial(MaterialType.METAL), new Diameter(10));
        MaterialWaterTankEnd end2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getMaterial(MaterialType.METAL), new Diameter(10));
        _fitting.addEnd(end2);
        _fitting.addEnd(end1);
        assertEquals(end1, _fitting.getEnd(Direction.UP));
        assertEquals(end2, _fitting.getEnd(Direction.DOWN));
    }

    @Test
    public void differentCompatibleMaterialsEnd() {
        MaterialWaterTankEnd end1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getMaterial(MaterialType.METAL), new Diameter(10));
        MaterialWaterTankEnd end2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getMaterial(MaterialType.STEEL), new Diameter(10));
        _fitting.addEnd(end2);
        _fitting.addEnd(end1);
        assertEquals(end1, _fitting.getEnd(Direction.UP));
        assertEquals(end2, _fitting.getEnd(Direction.DOWN));
    }

    @Test
    public void differentNotCompatibleMaterialsEnd() {
        MaterialWaterTankEnd end1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getMaterial(MaterialType.METAL), new Diameter(10));
        MaterialWaterTankEnd end2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getMaterial(MaterialType.PLASTIC), new Diameter(10));
        _fitting.addEnd(end2);
        _fitting.addEnd(end1);
        assertEquals(end1, _fitting.getEnd(Direction.UP));
        assertEquals(end2, _fitting.getEnd(Direction.DOWN));
    }

    @Test
    public void equalDiameterEnds() {
        MaterialWaterTankEnd end1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getMaterial(MaterialType.METAL), new Diameter(10));
        MaterialWaterTankEnd end2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getMaterial(MaterialType.METAL), new Diameter(10));
        _fitting.addEnd(end2);
        _fitting.addEnd(end1);
        assertEquals(end1, _fitting.getEnd(Direction.UP));
        assertEquals(end2, _fitting.getEnd(Direction.DOWN));
    }

    @Test
    public void differentDiameterEnds() {
        MaterialWaterTankEnd end1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getMaterial(MaterialType.METAL), new Diameter(10));
        MaterialWaterTankEnd end2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getMaterial(MaterialType.METAL), new Diameter(20));
        _fitting.addEnd(end2);
        _fitting.addEnd(end1);
        assertEquals(end1, _fitting.getEnd(Direction.UP));
        assertEquals(end2, _fitting.getEnd(Direction.DOWN));
    }

    @Test
    public void testAddEndToFitting() {
        MaterialWaterTankEnd expectedEnd = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10));
        _fitting.addEnd(expectedEnd);
        assertEquals(1, _fitting.getEnds().size());
        assertEquals(expectedEnd, _fitting.getEnd(Direction.UP));
    }

    @Test
    public void testAddEndsToFitting() {
        ArrayList<AbstractWaterTankEnd> ends = new ArrayList<>();
        MaterialWaterTankEnd expectedEnd1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10));
        MaterialWaterTankEnd expectedEnd2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getRoot(), new Diameter(10));
        ends.add(expectedEnd1);
        ends.add(expectedEnd2);
        _fitting.addEnds(ends);
        assertEquals(2, _fitting.getEnds().size());
        assertEquals(expectedEnd1, _fitting.getEnd(Direction.UP));
        assertEquals(expectedEnd2, _fitting.getEnd(Direction.DOWN));
    }

    @Test
    public void testRemoveEndFromFitting() {
        MaterialWaterTankEnd expectedEnd = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10));
        _fitting.addEnd(expectedEnd);
        _fitting.removeEnd(expectedEnd);
        assertEquals(0, _fitting.getEnds().size());
        assertEquals(null, _fitting.getEnd(Direction.UP));
    }

    @Test
    public void testRemoveEndsFromFitting(){
        ArrayList<AbstractWaterTankEnd> ends = new ArrayList<>();
        MaterialWaterTankEnd expectedEnd1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10));
        MaterialWaterTankEnd expectedEnd2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getRoot(), new Diameter(10));
        ends.add(expectedEnd1);
        ends.add(expectedEnd2);
        _fitting.addEnds(ends);
        _fitting.removeEnds(ends);
        assertEquals(0, _fitting.getEnds().size());
        assertEquals(null, _fitting.getEnd(Direction.UP));
        assertEquals(null, _fitting.getEnd(Direction.DOWN));
        }

        @Test
        public void testClearEndsOfFitting(){
        MaterialWaterTankEnd expectedEnd1 = new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10));
        MaterialWaterTankEnd expectedEnd2 = new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getRoot(), new Diameter(10));
        _fitting.addEnd(expectedEnd1);
        _fitting.addEnd(expectedEnd2);
        _fitting.clearEnds();
        assertEquals(0, _fitting.getEnds().size());
        assertEquals(null, _fitting.getEnd(Direction.UP));
        assertEquals(null, _fitting.getEnd(Direction.DOWN));
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
        void testGetConnectedFromTwoDirections() throws IOException {
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
        void testGetConnectedFromThreeDirections() throws IOException {
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
        void testGetConnectedFromFourDirections() throws IOException {
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
        void tryToGetConnectedFittingsAtTheCorner() throws IOException {
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

        boolean isFilled1 = _fitting.fill(water1);
        boolean isFilled2 = _fitting.fill(water2);
        assertTrue(isFilled1);
        assertFalse(isFilled2);

        assertEquals(water1, _fitting.getWater());
    }

    @Test
    public void testRotateClockwise() {
        _fitting.addEnd(new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10)));
        _fitting.addEnd(new MaterialWaterTankEnd(Direction.RIGHT, MaterialNode.getRoot(), new Diameter(10)));
        
        _fitting.rotateClockwise();

        assertNull(_fitting.getEnd(Direction.UP));
        assertNotNull(_fitting.getEnd(Direction.DOWN));
        assertNotNull(_fitting.getEnd(Direction.RIGHT));
    }

    @Test
    void zeroEndsRotationClockwise() {
        _fitting.rotateClockwise();
        assertEquals(0, _fitting.getEnds().size());
    }

    void allEndsPossible() {
        _fitting.addEnd(new MaterialWaterTankEnd(Direction.UP, MaterialNode.getRoot(), new Diameter(10)));
        _fitting.addEnd(new MaterialWaterTankEnd(Direction.RIGHT, MaterialNode.getRoot(), new Diameter(10)));
        _fitting.addEnd(new MaterialWaterTankEnd(Direction.DOWN, MaterialNode.getRoot(), new Diameter(10)));
        _fitting.addEnd(new MaterialWaterTankEnd(Direction.LEFT, MaterialNode.getRoot(), new Diameter(10)));

        _fitting.rotateClockwise();

        assertEquals(4, _fitting.getEnds().size());
    }
}
