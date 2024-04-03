package classes.entities.water_tanks;

import classes.Water;

public class Source extends WaterTank {
    
    public Source(){
        super();
    }
    
    public void createWater(){
        Water water = new Water();
        fill(water);
    }

    @Override
    public String toString(){
        return "s";
    }
}
