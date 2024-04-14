package Tests;

import classes.Water;
import classes.entities.water_tanks.Source;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SourceTests {

    @Test
    public void testCreateWater() {
        Source source = new Source();
        Water water = source.createWater();
        
        assertNotNull(water);
        
        assertEquals(water, source.getWater());
    }

    @Test void testCreateTwoWaters() {
        Source source = new Source();
        Water water = source.createWater();
        try {
            Water water2 = source.createWater();
            fail("Expected an exception");
        } catch (Exception e) {

        }
        
        
        assertEquals(water, source.getWater());
    }

    @Test
    public void testToString() {
        Source source = new Source();
        String expected = "s";
        String actual = source.toString();
        
        assertEquals(expected, actual);
    }
}
