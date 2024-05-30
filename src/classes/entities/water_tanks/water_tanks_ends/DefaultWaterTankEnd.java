package classes.entities.water_tanks.water_tanks_ends;

import classes.Direction;
import classes.entities.water_tanks.WaterTank;

public class DefaultWaterTankEnd extends AbstractWaterTankEnd {
    public DefaultWaterTankEnd(Direction direction) {
        super(direction);
    }

    @Override
    public WaterTank getConnectedNeighbour() {
        WaterTank neighWaterTank = getNeighbour(getDirection());

        if (neighWaterTank != null) {
            //check if the neighbour has a DefaultWaterTankEnd in the opposite direction
            if (neighWaterTank.getEnd(getDirection().turnAround()) instanceof DefaultWaterTankEnd) {
                return neighWaterTank;
            }
        }
        return null;
    }

    private WaterTank getNeighbour(Direction direction) {
        return getParentWaterTank().getNeighbour(direction);
    }

}