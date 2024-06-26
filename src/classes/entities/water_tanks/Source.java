package classes.entities.water_tanks;

import classes.Water;

public class Source extends AbstractWaterTank {
    
    public Source(){
        super();
    }
    
    public Water createWater(){
        if (getWater() != null){
            throw new IllegalStateException("Water already exists");
        }
        Water water = new Water(this);
        fill(water);
        return water;
    }

    @Override
    public String toString(){
        return "s";
    }
}
