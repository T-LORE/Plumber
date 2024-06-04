package ui;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import classes.entities.water_tanks.AbstractRotatableWaterTanks;
import classes.entities.water_tanks.AbstractWaterTank;
import ui.events.PipeWidgetEvent;
import ui.events.PipeWidgetListener;

public abstract class AbstractRotatableWaterTankWidget extends AbstractWaterTankWidget {
    
    private ArrayList<PipeWidgetListener> _listeners = new ArrayList<>();

    public AbstractRotatableWaterTankWidget(AbstractWaterTank watertank) {
        super(watertank);
        addMouseListener(new WidgetClickListener());
    }

    public AbstractRotatableWaterTanks getRotatableWaterTank(){
        return (AbstractRotatableWaterTanks) getWaterTank();
    }

    public void fireRotateEvent(){
        PipeWidgetEvent event = new PipeWidgetEvent(this);
        event.coords =  getCellWidget().getCell().getCoords();
        for (PipeWidgetListener listener : _listeners) {
            listener.rotateClockwise(event);
        }
    }

    public void addListener(PipeWidgetListener listener) {
        _listeners.add(listener);
    }

    public class WidgetClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            fireRotateEvent();
            repaint();
        }
    }
    
}
