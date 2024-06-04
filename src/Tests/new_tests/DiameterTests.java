package Tests.new_tests;

import classes.Diameter;
import classes.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class DiameterTests {
    @BeforeEach
    void setUp() {

    }

    @Test
    // Universal diameter
    void  zeroValue() {
        Diameter diameter = new Diameter(0);      
        assertEquals(0, diameter.getValue());
    }

    @Test
    void positiveValue() {
        Diameter diameter = new Diameter(5);
        assertEquals(5, diameter.getValue());
    }

    @Test
    void negativeValue() {
        try {
            Diameter diameter = new Diameter(-5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            
        }
    }

    @Test
    void testToString() {
        Diameter diameter = new Diameter(5);
        assertEquals("5", diameter.toString());
    }

    @Test
    void isCompatibleEqualDiameters() {
        Diameter diameter1 = new Diameter(5);
        Diameter diameter2 = new Diameter(5);
        assertTrue(diameter1.isCompatible(diameter2));
    }

    @Test
    void isCompatibleDifferentDiameters() {
        Diameter diameter1 = new Diameter(5);
        Diameter diameter2 = new Diameter(6);
        assertFalse(diameter1.isCompatible(diameter2));
    }

    @Test
    void isCompatibleUniversalDiameter() {
        Diameter diameter1 = new Diameter(5);
        Diameter diameter2 = new Diameter(0);
        assertTrue(diameter1.isCompatible(diameter2));
    }

    

}
