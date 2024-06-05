package Tests;

import classes.Direction;

import classes.Game;
import classes.Water;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;
import classes.events.GameActionEvent;
import classes.events.GameActionListener;

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
        _game = new Game();
        _mockGameActionListener = new MockGameActionListener();
        _game.addListener(_mockGameActionListener);
        Water.setWaterDelay(1);
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

        _game.getPlayer().startLevel(fileName);

        // delete file
        File file = new File(fileName);
        file.delete();
    }

    private void startWaterFlow() {
        _game.getPlayer().startWaterFlow();
    }

    @Test
    void testWinEvent() throws Exception {
        String level = "3 3\n" +
                "s□□\n" +
                "p□□\n" +
                "ppd\n" +
                "UNIVERSAL;10;0;0;1;0\n" +
                "UNIVERSAL;10;0;0;0;1\n" +
                "UNIVERSAL;10;1;0;1;0\n" +
                "UNIVERSAL;10;1;1;0;0\n" +
                "UNIVERSAL;10;0;1;0;1\n";

        loadLevel(level);
        startWaterFlow();
        
        sleep(100);
        assertEquals(true, _mockGameActionListener.winEventCalled);
    }

    @Test
    void testLoseEvent() throws Exception {
        String level = "3 3\n" +
        "s□□\n" +
        "p□□\n" +
        "ppd\n" +
        "UNIVERSAL;10;0;0;1;0\n" +
        "UNIVERSAL;10;0;0;0;1\n" +
        "UNIVERSAL;10;1;0;1;0\n" +
        "UNIVERSAL;10;1;1;0;0\n" +
        "UNIVERSAL;10;0;0;0;0\n";

        loadLevel(level);

        startWaterFlow();
        sleep(100);
        assertEquals(true, _mockGameActionListener.loseEventCalled);
    }

    @Test
    void rotateClockwiseTest() throws Exception {
        String level = "3 3\n" +
                "s□□\n" +
                "p□□\n" +
                "□□d\n" +
                "UNIVERSAL;10;1;0;1;0\n" +
                "UNIVERSAL;10;1;1;0;0\n" +
                "UNIVERSAL;10;1;0;1;0\n";

        loadLevel(level);

        _game.getPlayer().rotateClockwise(new Point(0,1));
        ArrayList<Direction> expectedDirs = new ArrayList<Direction>();
        expectedDirs.add(Direction.LEFT);
        expectedDirs.add(Direction.RIGHT);
        ArrayList <AbstractWaterTankEnd> ends = _game.getField().getRotatableTankOnCords( new Point(0,1)).getEnds();
        for (AbstractWaterTankEnd end : ends) {
            assertEquals(true, expectedDirs.contains(end.getDirection()));
        }


    }

    
}
