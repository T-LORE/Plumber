package Tests.MockEvents;

import classes.UserInputHandler;
import classes.events.UserInputEvent;
import classes.events.UserInputListener;

import java.awt.*;

public class MockUserInputHandler extends UserInputHandler {

    public void simulateRotateClockwiseEvent(Point point) {
        UserInputEvent event = new UserInputEvent(this);
        event.point = point;
        for (UserInputListener listener : _listeners) {
            listener.rotateClockwise(event);
        }
    }

    public void simulateStartFlowEvent() {
        UserInputEvent event = new UserInputEvent(this);
        for (UserInputListener listener : _listeners) {
            listener.startFlow(event);
        }
    }

    public void simulateLoadLevelEvent(String levelPath) throws Exception {
        UserInputEvent event = new UserInputEvent(this);
        event.levelPath = levelPath;
        for (UserInputListener listener : _listeners) {
            listener.loadLevel(event);
        }
    }
}
