package Tests;
import classes.Direction;
import classes.entities.water_tanks.Pipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PipeTests {
    
    @Test
    public void testRotateClockwise() {
        Pipe pipe = new Pipe();
        ArrayList<Direction> directions = new ArrayList<Direction>();
        directions.add(Direction.UP);
        directions.add(Direction.RIGHT);
        directions.add(Direction.DOWN);

        pipe.addPossibleDirections(directions);
        
        pipe.rotateClockwise();
        
        assertEquals(Arrays.asList(Direction.RIGHT, Direction.DOWN, Direction.LEFT), pipe.getPossibleDirections());
    }

    @Test
    void zeroPossibleDirections() {
        Pipe pipe = new Pipe();
        pipe.rotateClockwise();
        assertEquals(0, pipe.getPossibleDirections().size());
    }

    void allDirectionsPossible() {
        Pipe pipe = new Pipe();
        ArrayList<Direction> directions = new ArrayList<Direction>();
        directions.add(Direction.UP);
        directions.add(Direction.RIGHT);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);

        pipe.addPossibleDirections(directions);
        pipe.rotateClockwise();
        assertEquals(directions, pipe.getPossibleDirections());
    }
    
    @Test
    public void testRotateCounterClockwise() {
        Pipe pipe = new Pipe();
        ArrayList<Direction> directions = new ArrayList<Direction>();
        directions.add(Direction.UP);
        directions.add(Direction.RIGHT);
        directions.add(Direction.DOWN);
        
        pipe.addPossibleDirections(directions);

        pipe.rotateCounterClockwise();
        
        assertEquals(Arrays.asList(Direction.LEFT, Direction.UP, Direction.RIGHT), pipe.getPossibleDirections());
    }

    @Test
    void zeroPossibleDirectionsCounterClockwise() {
        Pipe pipe = new Pipe();
        pipe.rotateCounterClockwise();
        assertEquals(0, pipe.getPossibleDirections().size());
    }

    void allDirectionsPossibleCounterClockwise() {
        Pipe pipe = new Pipe();
        ArrayList<Direction> directions = new ArrayList<Direction>();
        directions.add(Direction.UP);
        directions.add(Direction.RIGHT);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);

        pipe.addPossibleDirections(directions);
        pipe.rotateCounterClockwise();
        assertEquals(directions, pipe.getPossibleDirections());
    }
    
}
