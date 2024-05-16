package classes.events;

import java.awt.*;
import java.util.EventObject;

public class PlayerActionEvent extends EventObject {
    public Point cords;
    public String levelPath;

    public PlayerActionEvent(Object source) {
        super(source);
    }
}
