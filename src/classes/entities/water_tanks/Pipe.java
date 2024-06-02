package classes.entities.water_tanks;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;
import classes.events.PipeActionEvent;
import classes.events.PipeActionListener;
import classes.events.WaterTankActionListener;


public class Pipe extends AbstractWaterTank {
    
    public Pipe() {
        super();
    }
    
    public void rotateClockwise() {
        for (AbstractWaterTankEnd end : getEnds()) {
            end.rotateClockwise();
        }
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
