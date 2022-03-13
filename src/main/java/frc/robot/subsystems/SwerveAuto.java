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
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.WPIUtilJNI;
import frc.robot.Robot;

public class SwerveAuto {
    private Vector2 desiredPosition;
    private double desiredAngle;

    private double positionStopRange = .1;
    private boolean isAtPosition = false;
    private boolean isAtAngle = false;

    // both below args are in m/s - first is velocity (35% of max robot velocity of
    // 3.77), and a max accel of .05 m/s
    private TrapezoidProfile.Constraints trapConstraints = new TrapezoidProfile.Constraints(0.5, 0.01);
    private TrapezoidProfile.State trapXCurrentState = new TrapezoidProfile.State(0, 0);
    private TrapezoidProfile.State trapXDesiredState;
    private TrapezoidProfile.State trapYCurrentState = new TrapezoidProfile.State(0, 0);
    private TrapezoidProfile.State trapYDesiredState;

    private PIDController xPID = new PIDController(0.01, 0, 0);
    private PIDController yPID = new PIDController(0.01, 0, 0);

    private double prevTime;

    public void setDesiredPosition(Vector2 desiredPosition) {
        this.desiredPosition = desiredPosition;
        trapXDesiredState = new TrapezoidProfile.State(this.desiredPosition.x, 0);
        trapYDesiredState = new TrapezoidProfile.State(this.desiredPosition.y, 0);

        prevTime = WPIUtilJNI.now() * 1.0e-6;
    }

    public void setDesiredAngle(double angle) {
        this.desiredAngle = angle;
    }

    public boolean isAtDesiredPosition() {
        if (MathClass.calculateDeadzone(
                Math.abs(Robot.swo.getPosition().positionCoord.x) - Math.abs(desiredPosition.x),
                positionStopRange) == 0) {

            if (MathClass.calculateDeadzone(
                    Math.abs(Robot.swo.getPosition().positionCoord.y) -
                            Math.abs(desiredPosition.y),
                    positionStopRange) == 0) {
                return true;
            }

        }
        return false;
    }

    public boolean isAtDesiredAngle() {
        if (MathClass.calculateDeadzone(Math.abs(Robot.swo.getPosition().angle) - Math.abs(desiredAngle),
                4) == 0) {
            return true;
        }
        return false;
    }

    public void translate() {
        double timeNow = WPIUtilJNI.now() * 1.0e-6;
        double trapTime = timeNow - prevTime;

        System.out.println("isTranslating");

        SmartDashboard.putNumber("Trap X Desired State", trapXDesiredState.velocity);
        SmartDashboard.putNumber("Trap Y Desired State", trapYDesiredState.velocity);

        TrapezoidProfile trapXProfile = new TrapezoidProfile(trapConstraints, trapXDesiredState, trapXCurrentState);
        TrapezoidProfile trapYProfile = new TrapezoidProfile(trapConstraints, trapYDesiredState, trapYCurrentState);

        TrapezoidProfile.State trapXOutput = trapXProfile.calculate(trapTime);
        TrapezoidProfile.State trapYOutput = trapYProfile.calculate(trapTime);

        double xVel = trapXOutput.velocity
                + xPID.calculate(Robot.swo.getVelocities()[0], trapXDesiredState.velocity);

        double yVel = trapYOutput.velocity
                + yPID.calculate(Robot.swo.getVelocities()[1], trapYDesiredState.velocity);

        Robot.swerveSystem.drive(xVel, yVel, 0, 0, "auto");

        trapXCurrentState = trapXOutput;
        trapYCurrentState = trapYOutput;

        prevTime = timeNow;

    }

    public void twist() {
        double twistValue = MathUtil.clamp(Robot.swo.getPosition().angle - desiredAngle, -1, 1);

        System.out.println("isTwisting");
        double twistInput = twistValue * .5;
        SmartDashboard.putNumber(" auto twistVal ", twistInput);
        Robot.swerveSystem.drive(0, 0, twistInput, 0, "auto");

    }

    public void kill() {
        Robot.swerveSystem.backRight.kill();
        Robot.swerveSystem.backLeft.kill();
        Robot.swerveSystem.frontRight.kill();
        Robot.swerveSystem.frontLeft.kill();
    }

}
