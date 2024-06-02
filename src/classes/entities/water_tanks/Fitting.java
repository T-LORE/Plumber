package classes.entities.water_tanks;

import classes.Direction;
import classes.MaterialNode;
import classes.entities.water_tanks.water_tanks_ends.MaterialWaterTankEnd;

public class Fitting extends AbstractRotatableWaterTanks{
    public Fitting() {
        super();
        super.setMaterial(MaterialNode.getRoot());
    }

    @Override
    public void setMaterial(MaterialNode material) {
        return;
    }

    public void setMaterialByDirection(MaterialNode material, Direction direction) {
        if (getEnd(direction) != null) {
            removeEnd(getEnd(direction));
        }
        addEnd(new MaterialWaterTankEnd(direction, material));
    }
}
