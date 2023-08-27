package support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2Test {

    @Test
    void testConstructor() {
        Vector2 vector = new Vector2(3, 4);
        assertEquals(3, vector.getX());
        assertEquals(4, vector.getY());
    }

    @Test
    void testSetters() {
        Vector2 vector = new Vector2(1, 1);
        vector.setX(5);
        vector.setY(6);
        assertEquals(5, vector.getX());
        assertEquals(6, vector.getY());
    }

    @Test
    void testAddSubtract() {
        Vector2 vector = new Vector2(2, 3);
        vector.addX(1);
        vector.addY(2);
        assertEquals(3, vector.getX());
        assertEquals(5, vector.getY());

        vector.subX(2);
        vector.subY(1);
        assertEquals(1, vector.getX());
        assertEquals(4, vector.getY());
    }

    @Test
    void testEquality() {
        Vector2 vector1 = new Vector2(2, 3);
        Vector2 vector2 = new Vector2(2, 3);
        Vector2 vector3 = new Vector2(3, 4);

        assertTrue(vector1.isEqual(vector2));
        assertFalse(vector1.isEqual(vector3));
    }

    @Test
    void testIsBetween() {
        Vector2 vector1 = new Vector2(1, 5);
        Vector2 vector2 = new Vector2(5, 9);
        Vector2 vector3 = new Vector2(2, 4);

        assertTrue(vector3.isBetween(vector1));
        assertFalse(vector2.isBetween(vector1));
    }
}
