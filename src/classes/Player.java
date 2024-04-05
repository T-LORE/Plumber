package classes;

import classes.entities.water_tanks.Pipe;
import classes.events.UserInputEvent;
import classes.events.UserInputListener;

import java.awt.*;
import java.io.IOException;

public class Player {
    private boolean _isActive;

    private Field _field;

    public Player(ConsoleUserInput eventSimulator) {
        UserInputObserver userInputObserver = new UserInputObserver();
        eventSimulator.addListener(userInputObserver);
    }

    public void setField(Field field) {
        _field = field;
    }

    public void setActive(boolean isActive) {
        _isActive = isActive;
    }

    private void rotateClockwise(Point cords) {
        if (!_isActive)
            return;

        Pipe pipe = _field.getPipeOnCords(cords);
        if (pipe != null) {
            pipe.rotateClockwise();
        }
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
