package classes.entities.water_tanks.water_tanks_ends;

import classes.Direction;
import classes.entities.water_tanks.WaterTank;

public abstract class AbstractWaterTankEnd {
    Direction _direction;
    WaterTank _parentWaterTank;

    public AbstractWaterTankEnd(Direction direction) {
        _direction = direction;
    }

    public Direction getDirection() {
        return _direction;
    }

    public void rotateClockwise() {
        _direction = _direction.rotateClockwise();
    }

    public boolean setParentWaterTank(WaterTank parentWaterTank) {
        _parentWaterTank = parentWaterTank;
        return true;
    }

    public abstract WaterTank getConnectedNeighbour();

    public WaterTank getParentWaterTank() {
        return _parentWaterTank;
    }

    @Override
    public String toString() {
        return _direction.toString();
    }

}
