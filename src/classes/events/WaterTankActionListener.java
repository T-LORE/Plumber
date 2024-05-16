package classes.events;

import java.util.EventListener;

public interface WaterTankActionListener extends EventListener {
    public void tankFilled(WaterTankActionEvent e);
}
