package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.util.Gyro;
import frc.robot.util.Polar;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.lang.Math;
import frc.robot.util.Vector2;

public class SwerveDriveTrain extends SubsystemBase {
    private SwerveWheel backLeft;
    private SwerveWheel backRight;
    private SwerveWheel frontLeft;
    private SwerveWheel frontRight;
    public Gyro gyro;

    public SwerveDriveTrain() {
        gyro = new Gyro();
        gyro.setOffset();
        backLeft = new SwerveWheel(
                Constants.backLeftTurnMotor,
                Constants.backLeftDriveMotor,
                Constants.backLeftEncoder);
        backRight = new SwerveWheel(
                Constants.backRightTurnMotor,
                Constants.backRightDriveMotor,
                Constants.backRightEncoder);
        frontLeft = new SwerveWheel(
                Constants.frontLeftTurnMotor,
                Constants.frontLeftDriveMotor,
                Constants.frontLeftEncoder);
        frontRight = new SwerveWheel(
                Constants.frontRightTurnMotor,
                Constants.frontRightDriveMotor,
                Constants.frontRightEncoder);
    }

    public static Vector2 polarToCartesian(double theta, double r) {
        theta = Math.toRadians(theta);
        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);

        return new Vector2(x, y);
    }

    public static Polar cartesianToPolar(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.toDegrees(Math.atan2(y, x));

        return new Polar(theta, r);
    }

    public static Vector2 fieldOriented(Vector2 position, double gyroAngle) {
        Polar polar = cartesianToPolar(position.x, position.x);
        double theta = polar.theta + gyroAngle;
        double r = polar.r;

        return polarToCartesian(theta, r);
    }

    public Polar calculateDrive(double x1, double y1, double theta2, double r2) {
        // X is 0 and Y is 1
        Vector2 driveCoordinate = fieldOriented(new Vector2(x1, y1), gyro.getAngle());
        Vector2 twistCoordinate = polarToCartesian(theta2, r2);

        // Args are theta, r
        return cartesianToPolar(driveCoordinate.x + twistCoordinate.x,
                driveCoordinate.y + twistCoordinate.y);
    }

    public void drive(double inputX, double inputY, double inputTwist) {
        double gyroAngle = gyro.getAngle();
        SmartDashboard.putNumber("gyro val", gyroAngle);

        Polar frontRightVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("frontRight"),
                inputTwist);
        Polar frontLeftVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("frontLeft"),
                inputTwist);
        Polar backRightVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("backRight"),
                inputTwist);
        Polar backLeftVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("backLeft"),
                inputTwist);

        // double frontRightSpeed = frontRightVector[1];
        // double frontLeftSpeed = frontLeftVector[1];
        // double backRightSpeed = backRightVector[1];
        // double backLeftSpeed = backLeftVector[1];

        // double frontRightAngle = frontRightVector[0];
        // double frontLeftAngle = frontLeftVector[0];
        // double backRightAngle = backRightVector[0];
        // double backLeftAngle = backLeftVector[0];

        backRight.drive(backRightVector.r, backRightVector.theta);
        backLeft.drive(-backLeftVector.r, backLeftVector.theta);
        frontRight.drive(frontRightVector.r, frontRightVector.theta);
        frontLeft.drive(-frontLeftVector.r, frontLeftVector.theta);
    }
}