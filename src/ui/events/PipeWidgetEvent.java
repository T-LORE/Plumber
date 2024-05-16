package ui.events;

import java.awt.Point;
import java.util.EventObject;

public class PipeWidgetEvent extends EventObject {
    
    public Point coords;

    public PipeWidgetEvent(Object source) {
        super(source);
    }
}