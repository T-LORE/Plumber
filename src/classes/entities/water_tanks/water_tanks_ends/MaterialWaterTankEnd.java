package classes.entities.water_tanks.water_tanks_ends;

import classes.Direction;
import classes.MaterialNode;
import classes.MaterialNode.MaterialType;
import classes.entities.water_tanks.WaterTank;

public class MaterialWaterTankEnd extends AbstractWaterTankEnd {
    MaterialNode _material;
    
    public MaterialWaterTankEnd(Direction direction, MaterialNode material) {
        super(direction);
        _material = material;
    }
    
    @Override
    public WaterTank getConnectedNeighbour() {
        WaterTank neighbourWaterTank = getParentWaterTank().getNeighbour(getDirection());

        if (neighbourWaterTank != null) {
            AbstractWaterTankEnd neighbourEnd = neighbourWaterTank.getEnd(getDirection().turnAround());
            if (neighbourEnd instanceof MaterialWaterTankEnd) {
                MaterialNode neighbourMaterial = ((MaterialWaterTankEnd) neighbourEnd).getMaterial();
                if (_material.isCompatible(neighbourMaterial)) {
                    return neighbourWaterTank;
                }
            }
        }
        
        return null;
    }

    public MaterialNode getMaterial() {
        return _material;
    }
}
