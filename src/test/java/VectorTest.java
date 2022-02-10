import static org.junit.Assert.*;
import org.junit.*;

import frc.robot.util.Vector2;

public class VectorTest {
    Vector2 vectorCord;

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
}
