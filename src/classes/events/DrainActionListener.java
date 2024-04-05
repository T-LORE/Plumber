package classes.events;

import java.util.EventListener;
public interface DrainActionListener extends EventListener {
    void filled(DrainActionEvent event);
}
