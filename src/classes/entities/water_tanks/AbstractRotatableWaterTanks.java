package classes.entities.water_tanks;

import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;
import classes.events.RotatableWaterTankActionEvent;
import classes.events.RotatableWaterTankActionListener;
import classes.events.WaterTankActionListener;

public abstract class AbstractRotatableWaterTanks extends AbstractWaterTank {
    
    public void rotateClockwise() {
        for (AbstractWaterTankEnd end : getEnds()) {
            end.rotateClockwise();
        }
        fireRotatedEvent();  
    }

    private void fireRotatedEvent() {
        RotatableWaterTankActionEvent event = new RotatableWaterTankActionEvent(this);
        for (WaterTankActionListener listener : _listeners) {
            if (listener instanceof RotatableWaterTankActionListener) {
                ((RotatableWaterTankActionListener) listener).pipeRotated(event);
            }
        }
    }
}
