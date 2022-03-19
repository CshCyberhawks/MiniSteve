import static org.junit.Assert.*;
import org.junit.*;

import frc.robot.util.Polar;

public class PolarTest {
    Polar polarCord;

    @Before
    public void setUp() {
        polarCord = new Polar(90, 1.5);
    }

    @Test
    public void checkPolarValues() {
        assertEquals(90, polarCord.theta, 0.001);
        assertEquals(1.5, polarCord.r, 0.001);
    }
}
