package classes;

import classes.entities.water_tanks.Pipe;
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

    public Player(ConsoleUserInput eventSimulator) {
        UserInputObserver userInputObserver = new UserInputObserver();
        eventSimulator.addListener(userInputObserver);
    }

    public void setActive(boolean isActive) {
        _isActive = isActive;
    }

    private void rotateClockwise(Point cords) {
        if (!_isActive)
            return;

        fireRotateClockwiseEvent(cords);
    }

    private void fireRotateClockwiseEvent(Point cords) {
        PlayerActionEvent event = new PlayerActionEvent(this);
        event.cords = cords;
        for (PlayerActionListener listener : _listeners) {
            listener.rotateClockwise(event);
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
        }

        @Override
        public void loadLevel(UserInputEvent event) throws IOException {
        }
    }
}
