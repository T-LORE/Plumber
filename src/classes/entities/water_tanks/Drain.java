package classes.entities.water_tanks;

import classes.Water;
import classes.events.DrainActionEvent;
import classes.events.DrainActionListener;

import java.util.ArrayList;

public class Drain extends AbstractWaterTank {
    private ArrayList<DrainActionListener> _listeners = new ArrayList<>();

    public Drain(){
        super();
    }

    @Override
    public boolean fill(Water water){
        boolean isFilled = super.fill(water);
        if (isFilled){
            fireFilledEvent();
        }
        return isFilled;

    }

    @Override
    public String toString(){
        return "d";
    }

    private void fireFilledEvent() {
        for (DrainActionListener listener : _listeners) {
            listener.filled(new DrainActionEvent(this));
        }
    }

    public void addListener(DrainActionListener listener) {
        _listeners.add(listener);
    }
}
