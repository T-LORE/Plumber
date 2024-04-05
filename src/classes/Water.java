package classes;

import classes.entities.water_tanks.WaterTank;
import classes.events.WaterActionEvent;
import classes.events.WaterActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Water {
    private ArrayList<HashMap<WaterTank,Direction>> _pastTicks;

    private ArrayList<WaterActionListener> _listeners = new ArrayList<>();
    public Water(WaterTank source) {
        _pastTicks = new ArrayList<HashMap<WaterTank,Direction>>();
        _pastTicks.add(new HashMap<WaterTank,Direction>());
        _pastTicks.get(0).put(source, null);
    }
    //flow by timer
    public void flow() {
        TimerTask task = new TimerTask() {
            public void run() {
                nextTick();
                flow();
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        timer.schedule(task, delay);
    }

    public void nextTick()
    {
        //get last tick
        HashMap<WaterTank,Direction> lastTick = _pastTicks.get(_pastTicks.size()-1);
        //create new tick
        HashMap<WaterTank,Direction> newTick = new HashMap<WaterTank,Direction>();
        //iterate over last tick
        for (WaterTank waterTank : lastTick.keySet()) {
            //get reachable neighbours
            HashMap<Direction, WaterTank> neighbours = waterTank.getReachableWaterTanks();
            for (Direction direction : neighbours.keySet()) {
                boolean isFilled = neighbours.get(direction).fillFromDirection(direction, Water.this);
                if (isFilled) {
                    newTick.put(neighbours.get(direction), direction);
                }
            }
        }
        if (newTick.isEmpty()) {
            fireEndFlowEvent();
        } else {
            _pastTicks.add(newTick);
        }
    }

    public void addListener (WaterActionListener listener) {
        _listeners.add(listener);
    }

    private void fireEndFlowEvent() {
        for (WaterActionListener listener : _listeners) {
            listener.waterEndFlow(new WaterActionEvent(this));
        }
    }

    private void firePouredOutEvent() {
        for (WaterActionListener listener : _listeners) {
            listener.waterEndFlow(new WaterActionEvent(this));
        }
    }
}
