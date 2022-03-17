package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.util.Gyro;
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

    public static Vector2 cartesianToPolar(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.toDegrees(Math.atan2(y, x));

        return new Vector2(theta, r);
    }

    public static Vector2 fieldOriented(Vector2 position, double gyroAngle) {
        Vector2 polar = cartesianToPolar(position.getX(), position.getY());
        double theta = polar.getX() + gyroAngle;
        double r = polar.getY();

        return polarToCartesian(theta, r);
    }

    public Vector2 calculateDrive(double x1, double y1, double theta2, double r2) {
        // X is 0 and Y is 1
        Vector2 driveCoordinate = fieldOriented(new Vector2(x1, y1), gyro.getAngle());
        Vector2 twistCoordinate = polarToCartesian(theta2, r2);

        // Args are theta, r
        return cartesianToPolar(driveCoordinate.getX() + twistCoordinate.getX(),
                driveCoordinate.getY() + twistCoordinate.getY());
    }

    public void drive(double inputX, double inputY, double inputTwist) {
        double gyroAngle = gyro.getAngle();
        SmartDashboard.putNumber("gyro val", gyroAngle);

        Vector2 frontRightVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("frontRight"),
                inputTwist);
        Vector2 frontLeftVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("frontLeft"),
                inputTwist);
        Vector2 backRightVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("backRight"),
                inputTwist);
        Vector2 backLeftVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("backLeft"),
                inputTwist);

        // double frontRightSpeed = frontRightVector[1];
        // double frontLeftSpeed = frontLeftVector[1];
        // double backRightSpeed = backRightVector[1];
        // double backLeftSpeed = backLeftVector[1];

        // double frontRightAngle = frontRightVector[0];
        // double frontLeftAngle = frontLeftVector[0];
        // double backRightAngle = backRightVector[0];
        // double backLeftAngle = backLeftVector[0];

        backRight.drive(backRightVector.getY(), backRightVector.getX());
        backLeft.drive(-backLeftVector.getY(), backLeftVector.getX());
        frontRight.drive(frontRightVector.getY(), frontRightVector.getX());
        frontLeft.drive(-frontLeftVector.getY(), frontLeftVector.getX());
    }
}