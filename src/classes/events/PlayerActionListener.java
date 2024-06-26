package classes.events;

import java.util.EventListener;

public interface PlayerActionListener extends EventListener {
    public void rotateClockwise(PlayerActionEvent event);
    public void startFlow(PlayerActionEvent event);
    public void startLevel(PlayerActionEvent event);
}
