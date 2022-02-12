package frc.robot.subsystems;
import frc.robot.util.MathClass;
import frc.robot.util.FieldPosition;
import frc.robot.util.Gyro;
import frc.robot.util.IO;
import frc.robot.util.Vector2;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Vector;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.WPIUtilJNI;
import frc.robot.Robot;

public class SwerveAuto {
    private FieldPosition desiredPosition;
    private double positionStopRange = 0.05;
    private boolean isAtPosition = false;
    private boolean isAtAngle = false;

    public void setDesiredPosition(FieldPosition _desiredPosition) {
        desiredPosition = _desiredPosition;

    }

    public boolean isAtDesiredPosition() {
        if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().positionCoord.x) - Math.abs(desiredPosition.positionCoord.x), positionStopRange) == 0) {
            if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().positionCoord.y) - Math.abs(desiredPosition.positionCoord.y), positionStopRange) == 0) {
                isAtPosition = true;
                return true;
            }
        }
        isAtPosition = false;
        return false;
    }

    public boolean isAtDesiredAngle() {
        if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().angle) - Math.abs(desiredPosition.angle), 5) == 0) {
            isAtAngle = true;
            return true;
        }
            isAtAngle = false;
            return false;
    }

    public double[] translate() {
        double inputX = MathUtil.clamp(desiredPosition.positionCoord.x - Robot.swo.getPosition().positionCoord.x, -1, 1);
        double inputY = MathUtil.clamp(desiredPosition.positionCoord.y - Robot.swo.getPosition().positionCoord.y, -1, 1);

        double[] ret = {inputX, inputY};
        return isAtPosition ? new double[] {0, 0} : ret;
    }

    public void drive() {
        double[] translateInputs = translate();
        double twistInput = twist();

        Robot.swerveSystem.drive(translateInputs[0], translateInputs[1], 0/*twistInput*/);
    }

    public double twist() {
        double twistValue = MathUtil.clamp(desiredPosition.angle - Robot.swo.getPosition().angle, -1, 1);
        return isAtAngle ? 0 : twistValue;
    }

    public boolean isAtDesiredPosAng() {
        return isAtDesiredPosition() && isAtDesiredAngle();
    }

    public void kill() {
        Robot.swerveSystem.backRight.kill();
        Robot.swerveSystem.backLeft.kill();
        Robot.swerveSystem.frontRight.kill();
        Robot.swerveSystem.frontLeft.kill();  
    }

}
