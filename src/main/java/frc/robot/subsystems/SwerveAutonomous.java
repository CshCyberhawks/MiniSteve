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
    private TrapezoidProfile.Constraints trapConstraints = new TrapezoidProfile.Constraints(0.5, 0.01);
    private TrapezoidProfile.State trapCurrentState = new TrapezoidProfile.State(0, 0);
    private TrapezoidProfile.State trapDesiredState;

    private AutoSwerveWheel backRight = new AutoSwerveWheel(0, 0, 0);
    private AutoSwerveWheel frontRight = new AutoSwerveWheel(0, 0, 0);
    private AutoSwerveWheel backLeft = new AutoSwerveWheel(0, 0, 0);
    private AutoSwerveWheel frontLeft = new AutoSwerveWheel(0, 0, 0);

    private double prevTime = 0;

    public void setDesiredPosition(Vector2 desiredPosition) {
        this.desiredPositionCart = desiredPosition;
        this.desiredPositionPolar = new PolarCoordinate(desiredPosition);

        trapDesiredState = new TrapezoidProfile.State(desiredPositionPolar.r, 0);

        SmartDashboard.putNumber("Desired X", desiredPosition.x);
        SmartDashboard.putNumber("Desired Y", desiredPosition.y);

    }

    public void setDesiredAngle(double desiredAngle) {
        this.desiredAngle = desiredAngle;
        SmartDashboard.putNumber("Desired Angle", desiredAngle);
    }

    public void translate() {
        TrapezoidProfile trapProfile = new TrapezoidProfile(trapConstraints, trapDesiredState, trapCurrentState);

        double currentTime = WPIUtilJNI.now() * 1.0e-6;
        double trapTime = currentTime - prevTime;

        double trapPosition = trapProfile.calculate(trapTime).position;

        backRight.drive(desiredPositionPolar.theta, trapPosition);
        frontRight.drive(desiredPositionPolar.theta, trapPosition);
        backLeft.drive(desiredPositionPolar.theta, trapPosition);
        frontLeft.drive(desiredPositionPolar.theta, trapPosition);

        // position = magnitude of odometry as polar (meters)
        // velocity = odometry velocity (m/s)

        double currentPosition = MathClass.cartesianToPolar(Robot.swo.getPosition().positionCoord.x,
                Robot.swo.getPosition().positionCoord.y)[1];
        double currentVelocity = MathClass.cartesianToPolar(Robot.swo.getVelocities()[0],
                Robot.swo.getVelocities()[1])[1];
        trapCurrentState = new TrapezoidProfile.State(MathClass.swoToMeters(currentPosition),
                MathClass.swoToMeters(currentVelocity));
    }

    public void twist() {
        backRight.drive(Constants.twistAngleMap.get("backRight"), .4);
        frontRight.drive(Constants.twistAngleMap.get("frontRight"), .4);
        backLeft.drive(Constants.twistAngleMap.get("backLeft"), .4);
        frontLeft.drive(Constants.twistAngleMap.get("frontLeft"), .4);
    }

    public boolean isAtDesiredPosition() {
        double timeNow = WPIUtilJNI.now() * 1.0e-6;
        double trapTime = timeNow - prevTime;
        TrapezoidProfile positionCheckProfile = new TrapezoidProfile(trapConstraints, trapDesiredState,
                trapCurrentState);

        return positionCheckProfile.calculate(trapTime).velocity == 0 ? true : false;

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

}
