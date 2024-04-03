package classes.entities.water_tanks;

import classes.Water;

public class Source extends WaterTank {
    
    public Source(){
        super();
    }
    
    public Water createWater(){
        Water water = new Water(this);
        fill(water);
        return water;
    }

    @Override
    public String toString(){
        return "s";
    }
}
