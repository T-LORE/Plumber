package classes.entities.water_tanks;
import classes.Direction;

import java.util.ArrayList;

public class Pipe extends WaterTank {
    public void rotateClockwise() {  
        ArrayList<Direction> newDirections = new ArrayList<Direction>();
        for (Direction direction : getPossibleDirections()) {
            newDirections.add(direction.rotateClockwise());
        }
        clearPossibleDirections();
        addPossibleDirections(newDirections);
    }

    public void rotateCounterClockwise() {
        ArrayList<Direction> newDirections = new ArrayList<Direction>();
        for (Direction direction : getPossibleDirections()) {
            newDirections.add(direction.rotateCounterClockwise());
        }
        clearPossibleDirections();
        addPossibleDirections(newDirections);
    }
}
