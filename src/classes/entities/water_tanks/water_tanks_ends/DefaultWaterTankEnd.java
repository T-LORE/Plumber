package classes.entities.water_tanks.water_tanks_ends;

import classes.Direction;
import classes.entities.water_tanks.AbstractWaterTank;

public class DefaultWaterTankEnd extends AbstractWaterTankEnd {
    public DefaultWaterTankEnd(Direction direction) {
        super(direction);
    }

    @Override
    public AbstractWaterTank getConnectedNeighbour() {
        AbstractWaterTank neighAbstractWaterTank = getNeighbour(getDirection());

        if (neighAbstractWaterTank != null) {
            //check if the neighbour has a DefaultWaterTankEnd in the opposite direction
            if (neighAbstractWaterTank.getEnd(getDirection().turnAround()) instanceof DefaultWaterTankEnd) {
                return neighAbstractWaterTank;
            }
        }
        return null;
    }

    private AbstractWaterTank getNeighbour(Direction direction) {
        return getParentWaterTank().getNeighbour(direction);
    }

}