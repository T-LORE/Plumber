package classes.entities.water_tanks;

import classes.Diameter;
import classes.Direction;
import classes.MaterialNode;
import classes.entities.water_tanks.water_tanks_ends.MaterialWaterTankEnd;

public class Fitting extends AbstractRotatableWaterTanks{
    public Fitting() {
        super();
        super.setMaterial(MaterialNode.getRoot());
        super.setDiameter(new Diameter(0));
    }

    @Override
    public void setMaterial(MaterialNode material) {
        return;
    }

    @Override
    public void setDiameter(Diameter diameter) {
        return;
    }

}
