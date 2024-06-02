package Tests;
import classes.Cell;
import classes.Direction;
import classes.Field;
import classes.entities.water_tanks.AbstractWaterTank;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CellTests {
    @Test
    void setField() {
        Cell cell = new Cell();
        Field field = new Field(1, 1);
        cell.setField(field, new Point(0, 0));
        assertEquals(field, cell.getField());
        assertEquals(new Point(0, 0), cell.getCoords());
        assertEquals(cell, field.getCell(new Point(0, 0)));
    }

    @Test
    void setPipeOnCell() {
        Cell cell = new Cell();
        Field field = new Field(1, 1);
        cell.setField(field, new Point(0, 0));
        AbstractWaterTank pipe = new AbstractWaterTank();
        cell.setEntity(pipe);
        assertEquals(pipe, cell.getEntity());
    }

    @Test
    void getNeighborAllNeighboursReachable() {
        Field field = new Field(3, 3);
        Cell cell = new Cell();
        cell.setField(field, new Point(1, 1));
        HashMap<Direction, Cell> expected = new HashMap<Direction, Cell>();
        expected.put(Direction.UP, field.getCell(new Point(1, 0)));
        expected.put(Direction.DOWN, field.getCell(new Point(1, 2)));
        expected.put(Direction.LEFT, field.getCell(new Point(0, 1)));
        expected.put(Direction.RIGHT, field.getCell(new Point(2, 1)));
        for (Direction direction : Direction.values()) {
            assertEquals(expected.get(direction), cell.getNeighbor(direction));
        }
    }

    @Test
    void getNeighbourNoNeighbours() {
        Field field = new Field(1, 1);
        Cell cell = new Cell();
        cell.setField(field, new Point(0, 0));
        for (Direction direction : Direction.values()) {
            assertEquals(null, cell.getNeighbor(direction));
        }
       
    }

    @Test
    void getNeighbourNoField() {
        Cell cell = new Cell();
        for (Direction direction : Direction.values()) {
            assertEquals(null, cell.getNeighbor(direction));
        }

    }

    @Test
    void getNeighborAtTheCorner() {
        Field field = new Field(3, 3);
        Cell cell = new Cell();
        cell.setField(field, new Point(0, 0));
        HashMap<Direction, Cell> expected = new HashMap<Direction, Cell>();
        expected.put(Direction.UP, null);
        expected.put(Direction.DOWN, field.getCell(new Point(0, 1)));
        expected.put(Direction.LEFT, null);
        expected.put(Direction.RIGHT, field.getCell(new Point(1, 0)));
        for (Direction direction : Direction.values()) {
            assertEquals(expected.get(direction), cell.getNeighbor(direction));
        }
    }

    @Test
    void toStringNoEntity() {
        Cell cell = new Cell();
        assertEquals("□", cell.toString());
    }

    @Test
    void toStringWithEntity() {
        Cell cell = new Cell();
        AbstractWaterTank pipe = new AbstractWaterTank();
        cell.setEntity(pipe);
        assertEquals("○", cell.toString());
    }

}
