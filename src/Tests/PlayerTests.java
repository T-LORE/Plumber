package Tests;

import classes.Player;
import classes.events.PlayerActionEvent;
import classes.events.PlayerActionListener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
public class PlayerTests {

    private Player _player;
    MockPlayerListener _mockPlayerActionListener = new MockPlayerListener();

    @BeforeEach
    void setUp() {
        _player = new Player();
        _player.addListener(_mockPlayerActionListener);
    }

    @Test
    void testRotateWhenNotActive() {
        _player.rotateClockwise(new Point(1, 5));
        assertFalse(_mockPlayerActionListener.isRotateClockwiseEventReceived);
    }

    @Test
    void testRotateClockwiseWhenActive() {
        _player.setActive(true);
        _player.rotateClockwise(new Point(1, 5));
        assertTrue(_mockPlayerActionListener.isRotateClockwiseEventReceived);
        assertEquals(new Point(1, 5), _mockPlayerActionListener.receivedEvent.cords);
    }

    @Test
    void testStartFlowWhenNotActive() {
        _player.startWaterFlow();
        assertFalse(_mockPlayerActionListener.isStartFlowEventReceived);
    }

    @Test
    void testStartFlowWhenActive() {
        _player.setActive(true);
        _player.startWaterFlow();
        assertTrue(_mockPlayerActionListener.isStartFlowEventReceived);
    }

    @Test
    void testStartLevel() {
        _player.startLevel("levelpath");
        assertTrue(_mockPlayerActionListener.isStartLevelEventReceived);
        assertEquals("levelpath", _mockPlayerActionListener.receivedEvent.levelPath);
    }

    private class MockPlayerListener implements PlayerActionListener {
        public boolean isRotateClockwiseEventReceived = false;
        public boolean isStartFlowEventReceived = false;
        public boolean isStartLevelEventReceived = false;

        PlayerActionEvent receivedEvent;
        @Override
        public void rotateClockwise(PlayerActionEvent event) {
            isRotateClockwiseEventReceived = true;
            receivedEvent = event;
        }

        @Override
        public void startFlow(PlayerActionEvent event) {
            isStartFlowEventReceived = true;
            receivedEvent = event;
        }

        @Override
        public void startLevel(PlayerActionEvent event) {
            isStartLevelEventReceived = true;
            receivedEvent = event;
        }
  
    }

}
