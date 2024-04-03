package classes.entities.water_tanks;

import classes.Water;

public class Drain extends WaterTank {

    public Drain(){
        super();
    }

    @Override
    public void fill(Water water){
        super.fill(water);
        // TODO: invoke event

    }

    @Override
    public String toString(){
        return "d";
    }

}
