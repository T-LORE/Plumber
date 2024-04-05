package classes;

import classes.entities.water_tanks.Pipe;

import java.awt.*;

public class Player {
    private boolean _isActive;

    private Field _field;

    public Player(Field field) {
        _field = field;
    }

    public void setActive(boolean isActive) {
        _isActive = isActive;
    }

    private void RotateClockwise(Point cords) {
        if (!_isActive)
            return;

        Pipe pipe = _field.getPipeOnCords(cords);
        if (pipe != null) {
            pipe.rotateClockwise();
        }
    }
}
