import static org.junit.Assert.*;
import org.junit.*;
import frc.robot.Robot;
import frc.robot.subsystems.SwerveAuto;

public class AutonomousTwistTest {
    SwerveAuto swerveAuto = Robot.swerveAuto;

    @Before
    public void setUp() {
        swerveAuto.setDesiredAngle(0, true);
    }

    @Test
    public void testIsAtTwist() {
        assertEquals(swerveAuto.isAtDesiredAngle(), true);
    }
}