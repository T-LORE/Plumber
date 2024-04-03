package Tests;

import classes.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTests {

    @Test
    void turnAroundUP() {
        Direction dir = Direction.UP;
        Direction expectedDir = Direction.DOWN;
        Direction actualDir = dir.turnAround();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void turnAroundLEFT() {
        Direction dir = Direction.LEFT;
        Direction expectedDir = Direction.RIGHT;
        Direction actualDir = dir.turnAround();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void turnAroundDOWN() {
        Direction dir = Direction.DOWN;
        Direction expectedDir = Direction.UP;
        Direction actualDir = dir.turnAround();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void turnAroundRIGHT() {
        Direction dir = Direction.RIGHT;
        Direction expectedDir = Direction.LEFT;
        Direction actualDir = dir.turnAround();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void rotateClockwiseUP() {
        Direction dir = Direction.UP;
        Direction expectedDir = Direction.RIGHT;
        Direction actualDir = dir.rotateClockwise();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void rotateClockwiseLEFT() {
        Direction dir = Direction.LEFT;
        Direction expectedDir = Direction.UP;
        Direction actualDir = dir.rotateClockwise();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void rotateClockwiseDOWN() {
        Direction dir = Direction.DOWN;
        Direction expectedDir = Direction.LEFT;
        Direction actualDir = dir.rotateClockwise();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void rotateClockwiseRIGHT() {
        Direction dir = Direction.RIGHT;
        Direction expectedDir = Direction.DOWN;
        Direction actualDir = dir.rotateClockwise();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void rotateCounterClockwiseUP() {
        Direction dir = Direction.UP;
        Direction expectedDir = Direction.LEFT;
        Direction actualDir = dir.rotateCounterClockwise();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void rotateCounterClockwiseLEFT() {
        Direction dir = Direction.LEFT;
        Direction expectedDir = Direction.DOWN;
        Direction actualDir = dir.rotateCounterClockwise();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void rotateCounterClockwiseDOWN() {
        Direction dir = Direction.DOWN;
        Direction expectedDir = Direction.RIGHT;
        Direction actualDir = dir.rotateCounterClockwise();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void rotateCounterClockwiseRIGHT() {
        Direction dir = Direction.RIGHT;
        Direction expectedDir = Direction.UP;
        Direction actualDir = dir.rotateCounterClockwise();
        assertEquals(expectedDir, actualDir);
    }

    @Test
    void turnAroundTwice() {
        Direction dir = Direction.UP;
        Direction expectedDir = Direction.UP;
        Direction actualDir = dir.turnAround().turnAround();
        assertEquals(expectedDir, actualDir);
    }
}
