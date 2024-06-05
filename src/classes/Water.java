package classes;

import classes.entities.water_tanks.AbstractWaterTank;
import classes.events.WaterActionEvent;
import classes.events.WaterActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Water {
    private ArrayList<HashMap<AbstractWaterTank,Direction>> _pastSteps = new ArrayList<>();

    private ArrayList<WaterActionListener> _listeners = new ArrayList<>();

    private final TimerTask _task = new TimerTask() {
        public void run() {
            nextStep();
        }
    };

    private final Timer _timer = new Timer("Flow timer");
    private static int DELAY = 1000;

    public ArrayList<HashMap<AbstractWaterTank,Direction>> getAllSteps() {
        return _pastSteps;
    }

    public static void setWaterDelay(int delay) {
        DELAY = delay;
    }

    public Water(AbstractWaterTank source) {
        HashMap<AbstractWaterTank,Direction> zeroStep = new HashMap<>();
        zeroStep.put(source, null);
        _pastSteps.add(zeroStep);
    }

    public void flow() {
        _timer.scheduleAtFixedRate(_task, DELAY, DELAY);
    }

    public void stop() {
        _timer.cancel();
    }

    public void nextStep()
    {     
        HashMap<AbstractWaterTank,Direction> newStep = new HashMap<>();
        for (AbstractWaterTank abstractWaterTank : lastStep().keySet()) {
            HashMap<Direction, AbstractWaterTank> neighbours = abstractWaterTank.getConnectedWaterTanks();
            for (Direction direction : neighbours.keySet()) {
                boolean isFilled = neighbours.get(direction).fill(Water.this);
                if (isFilled) {
                    newStep.put(neighbours.get(direction), direction.turnAround());
                }
            }
        }

        if (newStep.isEmpty()) {
            stop();
            fireEndFlowEvent();
        } else {
            _pastSteps.add(newStep);
        }

        fireEndStepEvent();
    }

    private HashMap<AbstractWaterTank,Direction> lastStep() {
        return _pastSteps.get(_pastSteps.size()-1);
    }

    public void addListener (WaterActionListener listener) {
        _listeners.add(listener);
    }

    private void fireEndStepEvent() {
        for (WaterActionListener listener : _listeners) {
            listener.stepEnd(new WaterActionEvent(this));
        }
    }

    private void fireEndFlowEvent() {
        for (WaterActionListener listener : _listeners) {
            listener.waterEndFlow(new WaterActionEvent(this));
        }
    } 
}
