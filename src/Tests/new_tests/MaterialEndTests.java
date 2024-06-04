package Tests.new_tests;

import classes.Field;
import classes.Water;
import classes.entities.water_tanks.Fitting;
import classes.entities.water_tanks.Source;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class MaterialEndTests {
    Field _field;
    @BeforeEach
    void setUp() {
        Water.setWaterDelay(1);
    }

    @Test
    public void equalMaterialEnd() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "f□□\n" +
                "□□d\n" +
                "METAL;10;0;0;1;0\n" +
                "METAL;10;0;0;0;1\n" +
                "UNIVERSAL;0;METAL;10;null;10;null;10;null;10\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.flow();
        sleep(100);
        Fitting fitting = (Fitting) _field.getCell(new Point(0, 1)).getEntity();
        assertNotNull(fitting.getWater());
    }

    @Test
    public void differentCompatibleMaterialEnd() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "f□□\n" +
                "□□d\n" +
                "METAL;10;0;0;1;0\n" +
                "METAL;10;0;0;0;1\n" +
                "UNIVERSAL;0;STEEL;10;null;10;null;10;null;10\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.flow();
        sleep(100);
        Fitting fitting = (Fitting) _field.getCell(new Point(0, 1)).getEntity();
        assertNotNull(fitting.getWater());
    }

    @Test
    public void differentNotCompatibleMaterialEnd() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "f□□\n" +
                "□□d\n" +
                "METAL;10;0;0;1;0\n" +
                "METAL;10;0;0;0;1\n" +
                "UNIVERSAL;0;PLASTIC;10;null;10;null;10;null;10\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.flow();
        sleep(100);
        Fitting fitting = (Fitting) _field.getCell(new Point(0, 1)).getEntity();
        assertNull(fitting.getWater());
    }

    @Test
    public void differentEqualDiameterEnd() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "f□□\n" +
                "□□d\n" +
                "UNIVERSAL;10;0;0;1;0\n" +
                "UNIVERSAL;10;0;0;0;1\n" +
                "UNIVERSAL;0;UNIVERSAL;10;null;10;null;10;null;10\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.flow();
        sleep(100);
        Fitting fitting = (Fitting) _field.getCell(new Point(0, 1)).getEntity();
        assertNotNull(fitting.getWater());
    }

    @Test
    public void differentNotEqualDiameterEnd() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "f□□\n" +
                "□□d\n" +
                "UNIVERSAL;10;0;0;1;0\n" +
                "UNIVERSAL;10;0;0;0;1\n" +
                "UNIVERSAL;0;UNIVERSAL;20;null;10;null;10;null;10\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.flow();
        sleep(100);
        Fitting fitting = (Fitting) _field.getCell(new Point(0, 1)).getEntity();
        assertNull(fitting.getWater());
    }

    @Test
    public void differentUniversalDiameterEnd() throws IOException, InterruptedException {
        String level = "3 3\n" +
                "s□□\n" +
                "f□□\n" +
                "□□d\n" +
                "UNIVERSAL;10;0;0;1;0\n" +
                "UNIVERSAL;10;0;0;0;1\n" +
                "UNIVERSAL;0;UNIVERSAL;0;null;10;null;10;null;10\n";

        _field = prepareField(level);

        Source src = _field.getSource();
        Water water = src.createWater();
        water.flow();
        sleep(100);
        Fitting fitting = (Fitting) _field.getCell(new Point(0, 1)).getEntity();
        assertNotNull(fitting.getWater());
    }

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
}
