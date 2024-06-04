package classes.entities.water_tanks.water_tanks_ends;

import classes.Diameter;
import classes.Direction;
import classes.MaterialNode;
import classes.entities.water_tanks.AbstractWaterTank;

public class MaterialWaterTankEnd extends AbstractWaterTankEnd {
    MaterialNode _material;
    Diameter _diameter;
    
    public MaterialWaterTankEnd(Direction direction, MaterialNode material, Diameter diameter) {
        super(direction);
        _material = material;
        _diameter = diameter;
    }
    
    @Override
    public AbstractWaterTank getConnectedNeighbour() {
        AbstractWaterTank neighbourAbstractWaterTank = getParentWaterTank().getNeighbour(getDirection());

        if (neighbourAbstractWaterTank != null) {
            AbstractWaterTankEnd neighbourEnd = neighbourAbstractWaterTank.getEnd(getDirection().turnAround());
            if (neighbourEnd instanceof MaterialWaterTankEnd) {
                MaterialNode neighbourMaterial = ((MaterialWaterTankEnd) neighbourEnd).getMaterial();
                Diameter neighbourDiameter = ((MaterialWaterTankEnd) neighbourEnd).getDiameter();
                if (_material.isCompatible(neighbourMaterial) && _diameter.isCompatible(neighbourDiameter)){
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
        if (parentAbstractWaterTank.getMaterial().isCompatible(_material) && parentAbstractWaterTank.getDiameter().isCompatible(_diameter)) {
            _parentAbstractWaterTank = parentAbstractWaterTank;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String superToString = super.toString();
        return superToString + ": " +_material.toString() + "; diameter: " + _diameter.toString();
    }

    public void setMaterial(MaterialNode material) {
        _material = material;
    }

    public void setDiameter(Diameter diameter) {
        _diameter = diameter;
    }

    public Diameter getDiameter() {
        return _diameter;
    }
}
