package classes.events;

import java.util.EventListener;

public interface GameActionListener extends EventListener {
    public void winEvent(GameActionEvent event);

    public void loseEvent(GameActionEvent event);

    public void waterTick(GameActionEvent event);
}
