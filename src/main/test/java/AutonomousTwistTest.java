import static org.junit.Assert.*;
import org.junit.*;
import frc.robot.Robot;
import frc.robot.subsystems.SwerveAutonomous;

public class AutonomousTwistTest {
    SwerveAutonomous swerveAuto = Robot.swerveAutonomous;

    @Before
    public void setUp() {
        swerveAuto.setDesiredAngle(0);
    }

    @Test
    public void testIsAtTwist() {
        assertEquals(swerveAuto.isAtDesiredAngleTest(0), true);
    }
}