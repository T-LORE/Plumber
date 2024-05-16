package classes;

import classes.events.PlayerActionEvent;
import classes.events.PlayerActionListener;
import classes.events.UserInputEvent;
import classes.events.UserInputListener;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private boolean _isActive;

    private ArrayList<PlayerActionListener> _listeners = new ArrayList<>();

    public Player(UserInputHandler userInputEventSender) {
        UserInputObserver userInputObserver = new UserInputObserver();
        userInputEventSender.addListener(userInputObserver);
    }

    public Player() {

    }

    public void setActive(boolean isActive) {
        _isActive = isActive;
    }

    public void rotateClockwise(Point cords) {
        if (!_isActive)
            return;

        fireRotateClockwiseEvent(cords);
    }

    public void startLevel(String levelPath) {
        fireStartLevelEvent(levelPath);
    }

    public void startWaterFlow() {
        fireStartWaterFlowEvent();
    }

    private void fireStartLevelEvent(String levelPath) {
        PlayerActionEvent event = new PlayerActionEvent(this);
        event.levelPath = levelPath;
        for (PlayerActionListener listener : _listeners) {
            listener.startLevel(event);
        }
    }

    private void fireRotateClockwiseEvent(Point cords) {
        PlayerActionEvent event = new PlayerActionEvent(this);
        event.cords = cords;
        for (PlayerActionListener listener : _listeners) {
            listener.rotateClockwise(event);
        }
    }

    private void fireStartWaterFlowEvent() {
        PlayerActionEvent event = new PlayerActionEvent(this);
        for (PlayerActionListener listener : _listeners) {
            listener.startFlow(event);
        }
    }

    public void addListener(PlayerActionListener listener) {
        _listeners.add(listener);
    }

    private class UserInputObserver implements UserInputListener {

        @Override
        public void rotateClockwise(UserInputEvent event) {
            Player.this.rotateClockwise(event.point);
        }

        @Override
        public void startFlow(UserInputEvent event) {
            Player.this.setActive(false);
        }

        @Override
        public void loadLevel(UserInputEvent event) throws IOException {
            Player.this.setActive(true);
        }
    }
}
