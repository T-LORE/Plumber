package classes.entities.water_tanks;
import classes.Direction;
import classes.events.PipeActionEvent;
import classes.events.PipeActionListener;
import classes.events.WaterTankActionListener;

import java.util.ArrayList;

public class Pipe extends WaterTank {
    
    public Pipe() {
        super();
    }
    
    public void rotateClockwise() {  
        ArrayList<Direction> newDirections = new ArrayList<Direction>();
        for (Direction direction : getPossibleDirections()) {
            newDirections.add(direction.rotateClockwise());
        }
        clearPossibleDirections();
        addPossibleDirections(newDirections);
        fireRotatedEvent();
    }

    public void rotateCounterClockwise() {
        ArrayList<Direction> newDirections = new ArrayList<Direction>();
        for (Direction direction : getPossibleDirections()) {
            newDirections.add(direction.rotateCounterClockwise());
        }
        clearPossibleDirections();
        addPossibleDirections(newDirections);
        fireRotatedEvent();
    }

    private void fireRotatedEvent() {
        PipeActionEvent event = new PipeActionEvent(this);
        for (WaterTankActionListener listener : _listeners) {
            if (listener instanceof PipeActionListener) {
                ((PipeActionListener) listener).pipeRotated(event);
            }
        }
    }
}
