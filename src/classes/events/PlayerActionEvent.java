package classes.events;

import java.awt.*;
import java.util.EventObject;

public class PlayerActionEvent extends EventObject {
    public Point cords;

    public PlayerActionEvent(Object source) {
        super(source);
    }
}
