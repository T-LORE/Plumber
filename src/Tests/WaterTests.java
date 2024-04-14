package Tests;

import classes.Field;
import classes.Water;
import classes.entities.water_tanks.Source;

import classes.events.WaterActionEvent;
import classes.events.WaterActionListener;
import org.junit.jupiter.api.Test;

import java.awt.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WaterTests {
    private Field _field;
    private Field prepareField(String level) throws IOException {
        String fileName = "test_level.txt";
        try {
            // write to file
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(level);
            fileWriter.close();
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // load from file
        Field field = Field.loadFromFile(fileName);

        // delete file
        File file = new File(fileName);
        file.delete();

        return field;
    }

    @Test
    void sourceAndDrainConnected() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "d□□\n" +
                "□□□\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.setWaterDelay(1);
        water.flow();
        sleep(10);
        assertEquals(water, _field.getDrain().getWater());
    }

    @Test
    void sourceAndDrainNotConnected() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "□□□\n" +
                "□□d\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.setWaterDelay(1);
        water.flow();
        sleep(100);
        assertEquals(null, _field.getDrain().getWater());
        assertEquals(_field.getSource(), water.getAllSteps().get(0).keySet().toArray()[0]);
    }

    @Test
    void sourceAndDrainConnectedWithPipe() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "║□□\n" +
                "╚═d\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.setWaterDelay(1);
        water.flow();
        sleep(10);
        assertEquals(water, _field.getDrain().getWater());
        assertEquals(water, _field.getPipeOnCords(new Point(0, 1)).getWater());
        assertEquals(water, _field.getPipeOnCords(new Point(0, 2)).getWater());
        assertEquals(water, _field.getPipeOnCords(new Point(1, 2)).getWater());
    }

    @Test
    void waterFork() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "╠═╗\n" +
                "╚═d\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.setWaterDelay(1);
        water.flow();
        sleep(10);
        assertEquals(water, _field.getDrain().getWater());
        assertEquals(water, _field.getPipeOnCords(new Point(0, 1)).getWater());
        assertEquals(water, _field.getPipeOnCords(new Point(0, 2)).getWater());
        assertEquals(water, _field.getPipeOnCords(new Point(1, 1)).getWater());
        assertEquals(water, _field.getPipeOnCords(new Point(1, 2)).getWater());
        assertEquals(water, _field.getPipeOnCords(new Point(2, 1)).getWater());
    }

    @Test
    void noConnectedPipe() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "╠□╝\n" +
                "□╝d\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.setWaterDelay(1);
        water.flow();
        sleep(10);
        assertEquals(null, _field.getDrain().getWater());
        assertEquals(water, _field.getPipeOnCords(new Point(0, 1)).getWater());
        assertEquals(null, _field.getPipeOnCords(new Point(2, 1)).getWater());
        assertEquals(null, _field.getPipeOnCords(new Point(1, 2)).getWater());
    }

    @Test
    void connectedWithTheRndOfField() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "╩□╝\n" +
                "□╝d\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.setWaterDelay(1);
        water.flow();
        sleep(10);
        assertEquals(null, _field.getDrain().getWater());
        assertEquals(water, _field.getPipeOnCords(new Point(0, 1)).getWater());
        assertEquals(null, _field.getPipeOnCords(new Point(2, 1)).getWater());
        assertEquals(null, _field.getPipeOnCords(new Point(1, 2)).getWater());
    }

    @Test
    void waterEndFlowEventTest() throws InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "║□□\n" +
                "╚═d\n";

        try {
            _field = prepareField(level);
        } catch (IOException e) {
            fail("Failed to create field");
        }

        Source src = _field.getSource();
        Water water = src.createWater();
        water.setWaterDelay(1);
        MockWaterListener listener = new MockWaterListener();
        water.addListener(listener);
        water.flow();
        sleep(10);
        assertEquals(true, listener.isEndFlowEventRecieved);
    }

    @Test
    void stepEndEventWithForkTest() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "╬╦╦\n" +
                "╚═d\n";


        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.setWaterDelay(1);
        MockWaterListener listener = new MockWaterListener();
        water.addListener(listener);
        water.flow();
        sleep(100);
        assertEquals(5, listener.stepCount);
    }

    @Test
    void stepEndEventTest() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "║□□\n" +
                "╚═d\n";


        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.setWaterDelay(1);
        MockWaterListener listener = new MockWaterListener();
        water.addListener(listener);
        water.flow();
        sleep(10);
        assertEquals(5, listener.stepCount);
    }



    private class MockWaterListener implements WaterActionListener {
        public boolean isEndFlowEventRecieved = false;
        public int stepCount = 0;

        @Override
        public void waterEndFlow(WaterActionEvent event) {
            isEndFlowEventRecieved = true;
        }

        @Override
        public void stepEnd(WaterActionEvent event) {
            stepCount++;
        }
    }
}
