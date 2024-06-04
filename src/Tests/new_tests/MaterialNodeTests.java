package Tests.new_tests;

import classes.Field;
import classes.MaterialNode;
import classes.MaterialNode.MaterialType;

import java.beans.Transient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MaterialNodeTests {
    @BeforeEach
    void setUp() {
       MaterialNode.configure();
    }

    @Test
    void equalsMaterialsCompatible() {
        MaterialNode materialNode1 = MaterialNode.getMaterial(MaterialType.METAL);
        MaterialNode materialNode2 = MaterialNode.getMaterial(MaterialType.METAL);
        assertTrue(materialNode1.isCompatible(materialNode2));
        assertTrue(materialNode2.isCompatible(materialNode1));
    }

    @Test
    void notEqualButCompatibleFirstParent() {
        MaterialNode materialNode1 = MaterialNode.getMaterial(MaterialType.METAL);
        MaterialNode materialNode2 = MaterialNode.getMaterial(MaterialType.STEEL);
        assertTrue(materialNode1.isCompatible(materialNode2));
        assertTrue(materialNode2.isCompatible(materialNode1));
    }

    @Test
    void notEqualButCompatibleSecondParent() {
        MaterialNode materialasdNode1 = MaterialNode.getRoot();
        MaterialNode materialNode1 = MaterialNode.getMaterial(MaterialType.METAL);
        MaterialNode materialNode2 = MaterialNode.getMaterial(MaterialType.CARBON_STEEL);
        assertTrue(materialNode1.isCompatible(materialNode2));
        assertTrue(materialNode2.isCompatible(materialNode1));
    }

    @Test
    void notCompatible() {
        MaterialNode materialNode1 = MaterialNode.getMaterial(MaterialType.METAL);
        MaterialNode materialNode2 = MaterialNode.getMaterial(MaterialType.PLASTIC);
        assertFalse(materialNode1.isCompatible(materialNode2));
        assertFalse(materialNode2.isCompatible(materialNode1));
    }

    @Test
    void universalMaterial() {
        MaterialNode materialNode1 = MaterialNode.getMaterial(MaterialType.UNIVERSAL);
        for (MaterialType materialType : MaterialType.values()) {
            MaterialNode materialNode2 = MaterialNode.getMaterial(materialType);
            assertTrue(materialNode1.isCompatible(materialNode2));
            assertTrue(materialNode2.isCompatible(materialNode1));
        }
    }
}
