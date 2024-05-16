package classes.events;

import java.util.EventListener;

public interface PipeActionListener extends WaterTankActionListener {
    public void pipeRotated(PipeActionEvent e);

}
