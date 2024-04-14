package Tests.MockEvents;

import classes.events.PlayerActionEvent;
import classes.events.PlayerActionListener;

public class MockPlayerActionListener implements PlayerActionListener {

    public PlayerActionEvent _event;

    @Override
    public void rotateClockwise(PlayerActionEvent event) {
        _event = event;
    }

    public PlayerActionEvent getReceivedEvent() {
        return _event;
    }
}
