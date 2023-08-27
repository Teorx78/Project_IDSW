package support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementDirectionsTest {

    @Test
    void testEnumValues() {
        MovementDirections up = MovementDirections.UP;
        MovementDirections down = MovementDirections.DOWN;
        MovementDirections left = MovementDirections.LEFT;
        MovementDirections right = MovementDirections.RIGHT;

        assertEquals("UP", up.name());
        assertEquals("DOWN", down.name());
        assertEquals("LEFT", left.name());
        assertEquals("RIGHT", right.name());
    }

    @Test
    void testEnumValuesOrdinal() {
        MovementDirections[] values = MovementDirections.values();

        assertEquals(MovementDirections.UP, values[0]);
        assertEquals(MovementDirections.DOWN, values[1]);
        assertEquals(MovementDirections.LEFT, values[2]);
        assertEquals(MovementDirections.RIGHT, values[3]);
    }

    @Test
    void testEnumValueOf() {
        MovementDirections up = MovementDirections.valueOf("UP");
        MovementDirections down = MovementDirections.valueOf("DOWN");
        MovementDirections left = MovementDirections.valueOf("LEFT");
        MovementDirections right = MovementDirections.valueOf("RIGHT");

        assertEquals(MovementDirections.UP, up);
        assertEquals(MovementDirections.DOWN, down);
        assertEquals(MovementDirections.LEFT, left);
        assertEquals(MovementDirections.RIGHT, right);
    }
}
