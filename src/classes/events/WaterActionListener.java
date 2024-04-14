package classes.events;

import java.util.EventListener;

public interface WaterActionListener extends EventListener {
    void waterEndFlow(WaterActionEvent event);

    void tickEnd(WaterActionEvent event);
}
