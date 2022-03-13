package frc.robot.subsystems;

import frc.robot.util.MathClass;
import frc.robot.util.PolarCoordinate;
import frc.robot.util.Gyro;
import frc.robot.util.IO;
import frc.robot.subsystems.AutoSwerveWheel;
import frc.robot.util.Vector2;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Vector;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.WPIUtilJNI;
import frc.robot.Robot;
import frc.robot.Constants;

public class SwerveAutonomous {
    private Vector2 desiredPositionCart;
    private PolarCoordinate desiredPositionPolar;
    private double desiredAngle;

    private double positionStopRange = .1;
    private boolean isAtPosition = false;
    private boolean isAtAngle = false;

    // both below args are in m/s - first is velocity (35% of max robot velocity of
    // 3.77), and a max accel of .05 m/s
    private TrapezoidProfile.Constraints trapConstraints = new TrapezoidProfile.Constraints(1, 0.1);
    private TrapezoidProfile.State trapCurrentState = new TrapezoidProfile.State(0, .5);
    private TrapezoidProfile.State trapDesiredState;

    private SwerveWheel backRight = Robot.swerveSystem.backRight;
    private SwerveWheel frontRight = Robot.swerveSystem.frontRight;
    private SwerveWheel backLeft = Robot.swerveSystem.backLeft;
    private SwerveWheel frontLeft = Robot.swerveSystem.frontLeft;

    private double prevTime = 0;

    private double startTime = 0;

    public void setDesiredPosition(Vector2 desiredPosition) {
        this.desiredPositionCart = desiredPosition;
        this.desiredPositionPolar = new PolarCoordinate(desiredPosition);
        SmartDashboard.putNumber("desiredPositionPolarR", desiredPositionPolar.r);

        trapDesiredState = new TrapezoidProfile.State(desiredPositionPolar.r, 0);

        SmartDashboard.putNumber("polarAngle", desiredPositionPolar.theta);

        startTime = WPIUtilJNI.now() * 1.0e-6;
    }

    public void setDesiredAngle(double desiredAngle) {
        this.desiredAngle = desiredAngle;
        SmartDashboard.putNumber("Desired Angle", desiredAngle);
    }

    public void translate() {
        TrapezoidProfile trapProfile = new TrapezoidProfile(trapConstraints, trapDesiredState, trapCurrentState);

        double currentTime = WPIUtilJNI.now() * 1.0e-6;

        double trapTime = currentTime - prevTime;

        SmartDashboard.putNumber("trapTime", trapTime);

        TrapezoidProfile.State trapOutput = trapProfile.calculate(trapTime);

        SmartDashboard.putNumber("trapVelocity", trapOutput.velocity);
        SmartDashboard.putNumber("trapPosition", trapOutput.position);

        backRight.autoDrive(desiredPositionPolar.theta, trapOutput.velocity);
        frontRight.autoDrive(desiredPositionPolar.theta, trapOutput.velocity);
        backLeft.autoDrive(desiredPositionPolar.theta, trapOutput.velocity);
        frontLeft.autoDrive(desiredPositionPolar.theta, trapOutput.velocity);

        // position = magnitude of odometry as polar (meters)
        // velocity = odometry velocity (m/s)

        double currentPosition = MathClass.cartesianToPolar(Robot.swo.getPosition().positionCoord.x,
                Robot.swo.getPosition().positionCoord.y)[1];
        double currentVelocity = MathClass.cartesianToPolar(Robot.swo.getVelocities()[0],
                Robot.swo.getVelocities()[1])[1];
        trapCurrentState = new TrapezoidProfile.State(MathClass.swosToMeters(currentPosition),
                MathClass.swosToMeters(currentVelocity));

        SmartDashboard.putNumber("trapCurrentStateVel", trapCurrentState.velocity);
        SmartDashboard.putNumber("trapCurrentStatePos", trapCurrentState.position);

        // trapCurrentState = trapOutput;

        prevTime = currentTime;
    }

    public void twist() {
        backRight.autoDrive(Constants.twistAngleMap.get("backRight"), .4);
        frontRight.autoDrive(Constants.twistAngleMap.get("frontRight"), .4);
        backLeft.autoDrive(Constants.twistAngleMap.get("backLeft"), .4);
        frontLeft.autoDrive(Constants.twistAngleMap.get("frontLeft"), .4);
    }

    public boolean isAtDesiredPosition() {
        double timeNow = WPIUtilJNI.now() * 1.0e-6;
        double trapTime = timeNow - prevTime;
        TrapezoidProfile positionCheckProfile = new TrapezoidProfile(trapConstraints, trapDesiredState,
                trapCurrentState);

        return false;// positionCheckProfile.calculate(trapTime).velocity == 0 ? true : false;

    }

    public boolean isAtDesiredAngle() {
        if (Gyro.getAngle() - desiredAngle > 180) {
            if (MathClass.calculateDeadzone((360 - Gyro.getAngle()) - desiredAngle, 4) == 0) {
                return true;
            }
        } else if (Gyro.getAngle() - desiredAngle <= 180) {
            if (MathClass.calculateDeadzone(Gyro.getAngle() - desiredAngle, 4) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isAtDesiredAngleTest(double angle) {
        if (angle - desiredAngle > 180) {
            if (MathClass.calculateDeadzone((360 - angle) - desiredAngle, 4) == 0) {
                return true;
            }
        } else if (angle - desiredAngle <= 180) {
            if (MathClass.calculateDeadzone(angle - desiredAngle, 4) == 0) {
                return true;
            }
        }
        return false;
    }

    public void kill() {
        backRight.kill();
        frontRight.kill();
        backLeft.kill();
        frontLeft.kill();
    }

}
