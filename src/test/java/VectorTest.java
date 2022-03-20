import static org.junit.Assert.*;
import org.junit.*;

import frc.robot.util.Vector2;

public class VectorTest {
    Vector2 vectorCord;

    public void assertVector(Vector2 expected, Vector2 actual) {
        assertEquals(expected.x, actual.x, 0.001);
        assertEquals(expected.y, actual.y, 0.001);
    }

    @Before
    public void setUp() {
        vectorCord = new Vector2(4, 1);
    }

    @Test
    public void checkVectorValues() {
        assertEquals(4, vectorCord.x, 0.001);
        assertEquals(1, vectorCord.y, 0.001);
    }

    @Test
    public void checkVectorAdd() {
        Vector2 vectorCord2 = new Vector2(2, 3);
        Vector2 vectorCord3 = vectorCord.add(vectorCord2);
        assertEquals(6, vectorCord3.x, 0.001);
        assertEquals(4, vectorCord3.y, 0.001);
    }

    @Test
    public void checkVectorSet() {
        Vector2 vector = new Vector2(0, 2);
        assertVector(new Vector2(0, 2), vector);
        vector.set(3, 4);
        assertVector(new Vector2(3, 4), vector);
        vector.set(new Vector2(7, 2));
        assertVector(new Vector2(7, 2), vector);
    }

    @Test
    public void checkVectorAddSelf() {
        Vector2 vector = new Vector2(1, 3);
        assertVector(new Vector2(2, 7), vector.add(new Vector2(1, 4)));
    }
}
