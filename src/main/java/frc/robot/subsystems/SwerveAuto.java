package frc.robot.subsystems;

import frc.robot.util.MathClass;
import frc.robot.util.Gyro;
import frc.robot.util.Vector2;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.WPIUtilJNI;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.DriveState;

public class SwerveAuto {
    private Vector2 desiredPosition;
    private double desiredAngle;

    private Alliance team;
    public Vector2[] ballPositions;

    private boolean byBall = false;
    private double ballDistanceDeadzone = 1;
    private double normalDistanceDeadzone = .1;

    private double positionStopRange = .1;
    private boolean isAtPosition = false;
    private boolean isAtAngle = false;

    private boolean trapXFinished = false;
    private boolean trapYFinsihed = false;

    // both below args are in m/s - first is velocity (35% of max robot velocity of
    // 3.77), and a max accel of .05 m/s
    private TrapezoidProfile.Constraints trapConstraints = new TrapezoidProfile.Constraints(3.777, 1);
    private TrapezoidProfile.State trapXCurrentState;
    private TrapezoidProfile.State trapXDesiredState;
    private TrapezoidProfile.State trapYCurrentState;
    private TrapezoidProfile.State trapYDesiredState;

    private PIDController xPID = new PIDController(1, 0, 0);
    private PIDController yPID = new PIDController(1, 0, 0);

    private double prevTime = 0;

    public SwerveAuto() {
        team = DriverStation.getAlliance();
        ballPositions = team == Alliance.Blue ? Constants.blueBallPositions : Constants.redBallPositions;

        trapXCurrentState = new TrapezoidProfile.State(
                Robot.swo.getPosition().positionCoord.x, Robot.swo.getVelocities()[0]);

        trapYCurrentState = new TrapezoidProfile.State(
                Robot.swo.getPosition().positionCoord.y, Robot.swo.getVelocities()[1]);
    }

    public void setDesiredPosition(Vector2 desiredPosition, double desiredVelocity) {
        byBall = false;
        this.desiredPosition = desiredPosition;

        double[] polarPosition = MathClass.cartesianToPolar(desiredPosition.x, desiredPosition.y);
        double[] desiredVelocities = MathClass.polarToCartesian(polarPosition[0], desiredVelocity);

        trapXDesiredState = new TrapezoidProfile.State(this.desiredPosition.x, desiredVelocities[0]);
        trapYDesiredState = new TrapezoidProfile.State(this.desiredPosition.y, desiredVelocities[0]);
    }

    public void setDesiredPositionBall(int ballNumber, double desiredVelocity) {
        setDesiredPosition(ballPositions[ballNumber], desiredVelocity);
        byBall = true;
    }

    public void setDesiredPositionDistance(double distance) {

        double[] desiredPositionCart = MathClass.polarToCartesian(Gyro.getAngle(), distance);

        setDesiredPosition(new Vector2(desiredPositionCart[0], desiredPositionCart[1]), 0);

    }

    public void setDesiredAngle(double angle, boolean robotRelative) {

        if (robotRelative) {
            this.desiredAngle = MathClass.wrapAroundAngles(angle - Gyro.getAngle());
        } else {
            this.desiredAngle = angle;
        }
    }

    public boolean isAtDesiredPosition() {
        if (byBall) {
            return MathClass.calculateDeadzone(
                    desiredPosition.x - MathClass.swosToMeters(Robot.swo.getPosition().positionCoord.x),
                    ballDistanceDeadzone) == 0
                    && MathClass.calculateDeadzone(
                            desiredPosition.y - MathClass.swosToMeters(Robot.swo.getPosition().positionCoord.y),
                            ballDistanceDeadzone) == 0;
        } else {
            return MathClass.calculateDeadzone(
                    desiredPosition.x - MathClass.swosToMeters(Robot.swo.getPosition().positionCoord.x),
                    normalDistanceDeadzone) == 0
                    && MathClass.calculateDeadzone(
                            desiredPosition.y - MathClass.swosToMeters(Robot.swo.getPosition().positionCoord.y),
                            normalDistanceDeadzone) == 0;

        }
    }

    public boolean isAtDesiredAngle() {
        if (MathClass.calculateDeadzone(
                MathClass.wrapAroundAngles(Robot.swo.getPosition().angle) - MathClass.wrapAroundAngles(desiredAngle),
                10) == 0) {
            return true;
        }
        return false;
    }

    public void translate() {
        double timeNow = WPIUtilJNI.now() * 1.0e-6;
        double trapTime = prevTime == 0 ? 0 : timeNow - prevTime;

        TrapezoidProfile trapXProfile = new TrapezoidProfile(trapConstraints, trapXDesiredState, trapXCurrentState);
        TrapezoidProfile trapYProfile = new TrapezoidProfile(trapConstraints, trapYDesiredState, trapYCurrentState);

        trapXFinished = trapXProfile.isFinished(trapTime);
        trapYFinsihed = trapYProfile.isFinished(trapTime);

        TrapezoidProfile.State trapXOutput = trapXProfile.calculate(trapTime);
        TrapezoidProfile.State trapYOutput = trapYProfile.calculate(trapTime);

        double xPIDOutput = xPID.calculate(MathClass.swosToMeters(Robot.swo.getPosition().positionCoord.x),
                trapXOutput.position);

        double yPIDOutput = yPID.calculate(MathClass.swosToMeters(Robot.swo.getPosition().positionCoord.y),
                trapYOutput.position);

        double xVel = trapXOutput.velocity
                + xPIDOutput;

        double yVel = trapYOutput.velocity
                + yPIDOutput;

        SmartDashboard.putNumber("xPID", xVel / 3.777);
        SmartDashboard.putNumber("yPID", yVel / 3.777);

        Robot.swerveSystem.drive(xVel / 3.777, yVel / 3.777, 0, 0, DriveState.AUTO);

        trapXCurrentState = trapXOutput;
        trapYCurrentState = trapYOutput;

        prevTime = timeNow;

    }

    public void twist() {
        double twistValue = MathUtil.clamp(Robot.swo.getPosition().angle - desiredAngle, -1, 1);

        double twistInput = twistValue * .3;
        SmartDashboard.putNumber(" auto twistVal ", twistInput);
        Robot.swerveSystem.drive(0, 0, twistInput, 0, DriveState.AUTO);

    }

    public void kill() {
        Robot.swerveSystem.backRight.kill();
        Robot.swerveSystem.backLeft.kill();
        Robot.swerveSystem.frontRight.kill();
        Robot.swerveSystem.frontLeft.kill();
    }
}
