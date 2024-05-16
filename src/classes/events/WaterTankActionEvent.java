package classes.events;

import java.util.EventObject;
public class WaterTankActionEvent extends EventObject{

    public WaterTankActionEvent(Object source) {
        super(source);
    }
}