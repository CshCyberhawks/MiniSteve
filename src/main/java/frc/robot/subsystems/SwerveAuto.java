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
    }

    public boolean isAtDesiredPosition() {
        if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().positionCoord.x) - Math.abs(desiredPosition.positionCoord.x), .1) == 0) {
            if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().positionCoord.y) - Math.abs(desiredPosition.positionCoord.y), .1) == 0) {
                return isAtDesiredAngle();
            }
        }
        return false;
    }


    public boolean isAtDesiredAngle() {
        if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().angle) - Math.abs(desiredPosition.angle), 10) == 0) {
            return true;
        }
            return false;
    }

    public double[] calculateInputs() {

        double differenceX = desiredPosition.positionCoord.x - Robot.swo.getPosition().positionCoord.x;
        double differenceY = desiredPosition.positionCoord.y - Robot.swo.getPosition().positionCoord.y;

        double angleDifference = desiredPosition.angle - Robot.swo.getPosition().angle;
        //if the angle difference = 0 return zero, else if angle difference > 0 return 1 else return -1
        double twist = MathUtil.clamp(desiredPosition.angle - Robot.swo.getPosition().angle, -1, 1);

        double[] ret = {MathUtil.clamp(differenceX, -1, 1), MathUtil.clamp(differenceY, -1, 1), twist};
        return ret;
    }

    public void drive() {

        double inputs[] = calculateInputs();

        Robot.swerveSystem.drive(inputs[0], inputs[1], inputs[2]);
        
        System.out.println("auto drive loop ran");
    }


    public void kill() {
        Robot.swerveSystem.backRight.kill();
        Robot.swerveSystem.backLeft.kill();
        Robot.swerveSystem.frontRight.kill();
        Robot.swerveSystem.frontLeft.kill();  
    }

}
