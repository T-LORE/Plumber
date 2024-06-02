package classes.entities.water_tanks.water_tanks_ends;

import classes.Direction;
import classes.MaterialNode;
import classes.entities.water_tanks.AbstractWaterTank;

public class MaterialWaterTankEnd extends AbstractWaterTankEnd {
    MaterialNode _material;
    
    public MaterialWaterTankEnd(Direction direction, MaterialNode material) {
        super(direction);
        _material = material;
    }
    
    @Override
    public AbstractWaterTank getConnectedNeighbour() {
        AbstractWaterTank neighbourAbstractWaterTank = getParentWaterTank().getNeighbour(getDirection());

        if (neighbourAbstractWaterTank != null) {
            AbstractWaterTankEnd neighbourEnd = neighbourAbstractWaterTank.getEnd(getDirection().turnAround());
            if (neighbourEnd instanceof MaterialWaterTankEnd) {
                MaterialNode neighbourMaterial = ((MaterialWaterTankEnd) neighbourEnd).getMaterial();
                if (_material.isCompatible(neighbourMaterial)) {
                    return neighbourAbstractWaterTank;
                }
            }
        }
        
        return null;
    }

    public MaterialNode getMaterial() {
        return _material;
    }

    @Override
    public boolean setParentWaterTank(AbstractWaterTank parentAbstractWaterTank) {
        if (parentAbstractWaterTank.getMaterial().isCompatible(_material)) {
            _parentAbstractWaterTank = parentAbstractWaterTank;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String toString = super.toString();
        return toString + ": " +_material.toString();
    }

    public void setMaterial(MaterialNode material) {
        _material = material;
    }
}
