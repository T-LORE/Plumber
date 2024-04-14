package Tests;

import Tests.MockEvents.MockUserInputHandler;
import classes.Direction;
import classes.Field;
import classes.Game;
import classes.Water;
import classes.entities.water_tanks.Source;

import classes.events.GameActionEvent;
import classes.events.GameActionListener;
import classes.events.WaterActionEvent;
import classes.events.WaterActionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GameTests {
    private MockUserInputHandler _mockUserInputHandler = new MockUserInputHandler();
    private Game _game;

    private class MockGameActionListener implements GameActionListener {
        public boolean winEventCalled = false;
        public boolean loseEventCalled = false;
        public GameActionEvent receivedEvent;

        @Override
        public void winEvent(GameActionEvent event) {
            winEventCalled = true;
        }

        @Override
        public void loseEvent(GameActionEvent event) {
            loseEventCalled = true;
        }

        @Override
        public void waterTick(GameActionEvent event) {

        }
    }
    private MockGameActionListener _mockGameActionListener;
    @BeforeEach
    void setUp() {
        _game = new Game(_mockUserInputHandler);
        _mockGameActionListener = new MockGameActionListener();
        _game.addListener(_mockGameActionListener);
    }

    private void loadLevel (String level) throws Exception {
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        _mockUserInputHandler.simulateLoadLevelEvent(fileName);

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    @Test
    void testWinEvent() throws Exception {
        String level = "3 3\n" +
                "s□□\n" +
                "║□□\n" +
                "╚═d\n";

        loadLevel(level);

        _mockUserInputHandler.simulateStartFlowEvent();
        sleep(1000);
        assertEquals(true, _mockGameActionListener.winEventCalled);
    }

    @Test
    void testLoseEvent() throws Exception {
        String level = "3 3\n" +
                "s□□\n" +
                "║□□\n" +
                "□□d\n";

        loadLevel(level);

        _mockUserInputHandler.simulateStartFlowEvent();
        sleep(1000);
        assertEquals(true, _mockGameActionListener.loseEventCalled);
    }

    @Test
    void rotateClockwiseTest() throws Exception {
        String level = "3 3\n" +
                "s□□\n" +
                "║□□\n" +
                "□□d\n";

        loadLevel(level);

        _mockUserInputHandler.simulateRotateClockwiseEvent(new Point(0, 1));
        ArrayList<Direction> expectedDirs = new ArrayList<Direction>();
        expectedDirs.add(Direction.LEFT);
        expectedDirs.add(Direction.RIGHT);
        ArrayList<Direction> actualDirs = _game.getField().getPipeOnCords(new Point(0, 1)).getPossibleDirections();
        for (Direction dir : expectedDirs) {
            assertEquals(true, actualDirs.contains(dir));
        }

        for (Direction dir : actualDirs) {
            assertEquals(true, expectedDirs.contains(dir));
        }

    }

    
}
