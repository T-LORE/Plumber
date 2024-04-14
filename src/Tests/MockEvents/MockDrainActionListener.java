package Tests.MockEvents;

import classes.events.DrainActionEvent;
import classes.events.DrainActionListener;

public class MockDrainActionListener implements DrainActionListener {
    public DrainActionEvent receivedEvent;
    
    @Override
    public void filled(DrainActionEvent event) {
        receivedEvent = event;
    }
}
