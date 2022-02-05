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

    public void setDesiredPosition(FieldPosition _desiredPosition) {
        desiredPosition = _desiredPosition;
    }

    public boolean isAtDesiredPosition() {
        if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().positionCoord.x) - Math.abs(desiredPosition.positionCoord.x), 1) == 0) {
            if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().positionCoord.y) - Math.abs(desiredPosition.positionCoord.y), 1) == 0) {
                return true;
            }
        }
        return false;
    }

    public void drive() {
        double wheelAngle = Robot.swerveSystem.cartesianToPolar(desiredPosition.positionCoord.x, desiredPosition.positionCoord.y)[0];


        SmartDashboard.putNumber(" wheel angles auto ", wheelAngle);
        
        System.out.println("auto drive loop ran");
        Robot.swerveSystem.backRight.drive(1, wheelAngle);
        Robot.swerveSystem.backLeft.drive(-1, wheelAngle);
        Robot.swerveSystem.frontRight.drive(1, wheelAngle);
        Robot.swerveSystem.frontLeft.drive(-1, wheelAngle);
    }

    public void kill() {
        Robot.swerveSystem.drive(0, 0, 0);
    }

}
