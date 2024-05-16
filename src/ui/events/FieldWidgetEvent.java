package ui.events;

import java.awt.*;
import java.util.EventObject;

public class FieldWidgetEvent extends EventObject {

    public Point coords;
    public FieldWidgetEvent(Object source) {
        super(source);
    }
}
