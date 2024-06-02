package classes.entities.water_tanks.water_tanks_ends;

import classes.Direction;
import classes.entities.water_tanks.AbstractWaterTank;

public abstract class AbstractWaterTankEnd {
    Direction _direction;
    AbstractWaterTank _parentAbstractWaterTank;

    public AbstractWaterTankEnd(Direction direction) {
        _direction = direction;
    }

    public Direction getDirection() {
        return _direction;
    }

    public void rotateClockwise() {
        _direction = _direction.rotateClockwise();
    }

    public boolean setParentWaterTank(AbstractWaterTank parentAbstractWaterTank) {
        _parentAbstractWaterTank = parentAbstractWaterTank;
        return true;
    }

    public abstract AbstractWaterTank getConnectedNeighbour();

    public AbstractWaterTank getParentWaterTank() {
        return _parentAbstractWaterTank;
    }

    @Override
    public String toString() {
        return _direction.toString();
    }

}
