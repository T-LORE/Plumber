package Tests;

import classes.Water;
import classes.entities.water_tanks.Drain;
import classes.entities.water_tanks.Source;
import classes.events.DrainActionEvent;
import classes.events.DrainActionListener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DrainTests {
    private Drain _drain;
    private MockDrainActionListener _mockDrainActionListener;

    @BeforeEach
    void setUp() {
       _drain = new Drain();
       _mockDrainActionListener = new MockDrainActionListener();
       _drain.addListener(_mockDrainActionListener);
    }

    @Test
    void testFill() {
       Water water = new Water(new Source());
       boolean isFilled = _drain.fill(water);

       assertTrue(isFilled);
       assertSame(_mockDrainActionListener.receivedEvent.getSource(), _drain);
    }

    @Test
    void DoubleFIll() {
        Water water = new Water(new Source());
        _drain.fill(water);
        boolean isFilled = _drain.fill(water);

        assertFalse(isFilled);
    }

    @Test
    void testToString() {
        Drain drain = new Drain();
        String expected = "d";

        String actual = drain.toString();

        assertEquals(expected, actual);
    }

    private class MockDrainActionListener implements DrainActionListener {
        public DrainActionEvent receivedEvent;

        @Override
        public void filled(DrainActionEvent event) {
            receivedEvent = event;
        }

    }
}