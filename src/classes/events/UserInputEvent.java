package classes.events;

import java.awt.*;
import java.util.EventObject;

public class UserInputEvent extends EventObject {
    public Point point;
    public String levelPath;

    public UserInputEvent(Object source) {
        super(source);
    }
}
