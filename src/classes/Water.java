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

    private final TimerTask _task = new TimerTask() {
        public void run() {
            nextTick();
        }
    };

    private final Timer _timer = new Timer("Flow timer");
    private final int DELAY = 1000;

    public Water(WaterTank source) {
        _pastTicks = new ArrayList<HashMap<WaterTank,Direction>>();
        _pastTicks.add(new HashMap<WaterTank,Direction>());
        _pastTicks.get(0).put(source, null);
    }
    //flow by timer
    public void flow() {
        _timer.scheduleAtFixedRate(_task, DELAY, DELAY);
    }

    public void stop() {
        _timer.cancel();
    }

    public void nextTick()
    {
        HashMap<WaterTank,Direction> lastTick = _pastTicks.get(_pastTicks.size()-1);
        HashMap<WaterTank,Direction> newTick = new HashMap<>();
        for (WaterTank waterTank : lastTick.keySet()) {
            HashMap<Direction, WaterTank> neighbours = waterTank.getReachableWaterTanks();
            for (Direction direction : neighbours.keySet()) {
                boolean isFilled = neighbours.get(direction).fillFromDirection(direction, Water.this);
                if (isFilled) {
                    newTick.put(neighbours.get(direction), direction);
                }
            }
        }

        if (newTick.isEmpty()) {
            stop();
            fireEndFlowEvent();
        } else {
            _pastTicks.add(newTick);
        }

        fireEndTickEvent();
    }

    public void addListener (WaterActionListener listener) {
        _listeners.add(listener);
    }

    private void fireEndTickEvent() {
        for (WaterActionListener listener : _listeners) {
            listener.tickEnd(new WaterActionEvent(this));
        }
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
