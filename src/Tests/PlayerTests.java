package Tests;

import Tests.MockEvents.MockPlayerActionListener;
import Tests.MockEvents.MockUserInputHandler;
import classes.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
public class PlayerTests {

    private Player _player;
    private MockUserInputHandler _mockInputHandler;
    private MockPlayerActionListener _mockPlayerActionListener;

    @BeforeEach
    void setUp() {
        _mockInputHandler = new MockUserInputHandler();
        _player = new Player(_mockInputHandler);
        _mockPlayerActionListener = new MockPlayerActionListener();
        _player.addListener(_mockPlayerActionListener);
    }

    @Test
    void testRotateClockwiseBeforeLevelStart() {
        _mockInputHandler.simulateRotateClockwiseEvent(new Point(1, 5));
        assertNull(_mockPlayerActionListener.getReceivedEvent());
    }

    @Test
    void testRotateClockwiseAfterLevelStart() throws Exception {
        _mockInputHandler.simulateLoadLevelEvent("test_level.txt");
        _mockInputHandler.simulateRotateClockwiseEvent(new Point(1, 5));
        assertEquals(new Point(1, 5), _mockPlayerActionListener.getReceivedEvent().cords);
    }

    @Test
    void testRotateClockwiseWhenWaterFlow() throws Exception {
        _mockInputHandler.simulateLoadLevelEvent("test_level.txt");
        _mockInputHandler.simulateStartFlowEvent();
        _mockInputHandler.simulateRotateClockwiseEvent(new Point(1, 5));
        assertNull(_mockPlayerActionListener.getReceivedEvent());
    }

}
