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
    private double wheelAngle;

    public void setDesiredPosition(FieldPosition _desiredPosition) {
        desiredPosition = _desiredPosition;
        wheelAngle = Robot.swerveSystem.cartesianToPolar(desiredPosition.positionCoord.x, desiredPosition.positionCoord.y)[0];
    }

    public boolean isAtDesiredPosition() {
        if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().positionCoord.x) - Math.abs(desiredPosition.positionCoord.x), .05) == 0) {
            if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().positionCoord.y) - Math.abs(desiredPosition.positionCoord.y), .05) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isAtDesiredAngle() {
        if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().angle) - Math.abs(desiredPosition.angle), 5) == 0) {
            return true;
        }
            return false;
    }

    public void drive() {


        SmartDashboard.putNumber(" wheel angles auto ", wheelAngle);
        
        System.out.println("auto drive loop ran");
        Robot.swerveSystem.backRight.drive(1, wheelAngle);
        Robot.swerveSystem.backLeft.drive(-.85, wheelAngle);
        Robot.swerveSystem.frontRight.drive(1, wheelAngle);
        Robot.swerveSystem.frontLeft.drive(-.85, wheelAngle);
    }

    public void twist() {
        double twistValue = desiredPosition.angle - Robot.swo.getPosition().angle > 0 ? 1 : 0;
        Robot.swerveSystem.drive(0, 0, twistValue);
    }

    public void kill() {
        Robot.swerveSystem.backRight.kill();
        Robot.swerveSystem.backLeft.kill();
        Robot.swerveSystem.frontRight.kill();
        Robot.swerveSystem.frontLeft.kill();  
    }

}
