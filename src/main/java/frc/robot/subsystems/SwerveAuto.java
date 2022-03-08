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
    private double positionStopRange = .1;
    private boolean isAtPosition = false;
    private boolean isAtAngle = false;

    // both below args are in m/s - first is velocity (35% of max robot velocity of
    // 3.77), and a max accel of .05 m/s
    private TrapezoidProfile.Constraints trapConstraints = new TrapezoidProfile.Constraints(1.31, 0.05);
    private TrapezoidProfile.State trapXCurrentState = new TrapezoidProfile.State(0, 0);
    private TrapezoidProfile.State trapXDesiredState;
    private TrapezoidProfile.State trapYCurrentState = new TrapezoidProfile.State(0, 0);
    private TrapezoidProfile.State trapYDesiredState;

    private double startTime;

    public void setDesiredPosition(FieldPosition _desiredPosition) {
        desiredPosition = _desiredPosition;
        trapXDesiredState = new TrapezoidProfile.State(desiredPosition.positionCoord.x, 0);
        trapYDesiredState = new TrapezoidProfile.State(desiredPosition.positionCoord.y, 0);
        startTime = WPIUtilJNI.now() * 1.0e-6;
    }

    public boolean isAtDesiredPosition() {
        if (MathClass.calculateDeadzone(
                Math.abs(Robot.swo.getPosition().positionCoord.x) - Math.abs(desiredPosition.positionCoord.x),
                positionStopRange) == 0) {

            if (MathClass.calculateDeadzone(
                    Math.abs(Robot.swo.getPosition().positionCoord.y) -
                            Math.abs(desiredPosition.positionCoord.y),
                    positionStopRange) == 0) {
                return true;
            }

        }
        return false;
    }

    public boolean isAtDesiredAngle() {
        if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().angle) - Math.abs(desiredPosition.angle),
                4) == 0) {
            return true;
        }
        return false;
    }

    public boolean isAtDesiredPosAng() {
        return isAtPosition && isAtAngle;
    }

    public void drive() {
        double timeNow = WPIUtilJNI.now() * 1.0e-6;
        double trapTime = timeNow - startTime;

        isAtPosition = isAtDesiredPosition();
        isAtAngle = isAtDesiredAngle();

        // translateInputs[0] = MathUtil.clamp(translateInputs[0], -.2, .2);
        // translateInputs[1] = MathUtil.clamp(translateInputs[1], -.2, .2);

        SmartDashboard.putBoolean("isAtAngle", isAtAngle);
        SmartDashboard.putBoolean("isAtDesiredPos", isAtPosition);

        if (!isAtPosition) {
            System.out.println("isTranslating");
            TrapezoidProfile trapXProfile = new TrapezoidProfile(trapConstraints, trapXDesiredState, trapXCurrentState);
            TrapezoidProfile trapYProfile = new TrapezoidProfile(trapConstraints, trapYDesiredState, trapYCurrentState);

            double[] translateInputs = translate();

            SmartDashboard.putNumber("autoX", translateInputs[0]);
            SmartDashboard.putNumber("autoY", translateInputs[1]);

            // translateInputs[0] *= Math.abs(trapXProfile.calculate(trapTime).velocity);
            // translateInputs[1] *= Math.abs(trapYProfile.calculate(trapTime).velocity);

            SmartDashboard.putNumber("trapXOutput", translateInputs[0]);
            SmartDashboard.putNumber("trapYOutput", translateInputs[1]);

            Robot.swerveSystem.drive(translateInputs[0] * .1, translateInputs[1] * .1, 0, 0, "auto");
        }
        if (isAtPosition && !isAtAngle) {
            System.out.println("isTwisting");
            double twistInput = twist();
            SmartDashboard.putNumber(" auto twistVal ", twistInput);
            Robot.swerveSystem.drive(0, 0, twistInput, 0, "auto");
        }

        trapXCurrentState = new TrapezoidProfile.State(Robot.swo.getPosition().positionCoord.x,
                Robot.swo.getVelocities()[0]);
        trapYCurrentState = new TrapezoidProfile.State(Robot.swo.getPosition().positionCoord.y,
                Robot.swo.getVelocities()[1]);
    }

    public double[] translate() {
        double inputX = MathUtil.clamp(desiredPosition.positionCoord.x - Robot.swo.getPosition().positionCoord.x, -1,
                1);
        double inputY = MathUtil.clamp(desiredPosition.positionCoord.y - Robot.swo.getPosition().positionCoord.y, -1,
                1);

        double[] ret = { inputX, inputY };
        return isAtPosition ? new double[] { 0, 0 } : ret;
    }

    public double twist() {
        double twistValue = MathUtil.clamp(Robot.swo.getPosition().angle - desiredPosition.angle, -1, 1);
        return isAtAngle ? 0 : twistValue;
    }

    public void kill() {
        Robot.swerveSystem.backRight.kill();
        Robot.swerveSystem.backLeft.kill();
        Robot.swerveSystem.frontRight.kill();
        Robot.swerveSystem.frontLeft.kill();
    }

}
