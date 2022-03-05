package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.util.Gyro;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import java.lang.Math;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.lang.Math;

public class SwerveDriveTrain extends SubsystemBase {
     private SwerveWheel backLeft;
     private SwerveWheel backRight;
     private SwerveWheel frontLeft;
     private SwerveWheel frontRight;
     public Gyro gyro;

     public SwerveDriveTrain() {
         gyro = new Gyro();
         backLeft = new SwerveWheel(Constants.backLeftTurnMotor, Constants.backLeftDriveMotor, Constants.backLeftEncoder);
         backRight = new SwerveWheel(Constants.backRightTurnMotor, Constants.backRightDriveMotor, Constants.backRightEncoder);
         frontLeft = new SwerveWheel(Constants.frontLeftTurnMotor, Constants.frontLeftDriveMotor, Constants.frontLeftEncoder);
         frontRight = new SwerveWheel(Constants.frontRightTurnMotor, Constants.frontRightDriveMotor, Constants.frontRightEncoder);
         gyro.setOffset();
     }
 
     public double[] polarToCartesian(double theta, double r) {
          double x = r * Math.cos(Math.toRadians(theta));
          double y = r * Math.sin(Math.toRadians(theta));

          double[] ret = {x, y};
          return ret;
     }

     public double[] cartesianToPolar(double x, double y) {
          double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
          double theta = Math.toDegrees(Math.atan2(y, x));

          double[] ret = {theta, r};
          return ret;
     }

     public double[] fieldOriented(double x, double y, double gyroAngle) {
          double[] polar = cartesianToPolar(x, y);
          double theta = polar[0] + gyroAngle;

          double r = polar[1];

          double[] ret = polarToCartesian(theta, r);
          return ret;
     }

     public double[] calculateDrive(double x1, double y1, double theta2, double r2) {
          //X is 0 and Y is 1
          double[] driveCoordinate = fieldOriented(x1, y1, gyro.getAngle());
          double[] twistCoordinate = polarToCartesian(theta2, r2);

          //Args are theta, r
          double[] ret = cartesianToPolar(driveCoordinate[0] + twistCoordinate[0], driveCoordinate[1] + twistCoordinate[1]);
          return ret;
     }

     public void drive(double inputX, double inputY, double inputTwist) {
          if (inputX == 0 && inputY == 0 && inputTwist == 0) {
               backRight.preserveAngle();
               backLeft.preserveAngle();
               frontRight.preserveAngle();
               frontLeft.preserveAngle();
               return;
          }

          double gyroAngle = gyro.getAngle();
          SmartDashboard.putNumber("gyro val", gyroAngle);

          double[] frontRightVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("frontRight"), inputTwist);
          double[] frontLeftVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("frontLeft"), inputTwist);
          double[] backRightVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("backRight"), inputTwist);
          double[] backLeftVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("backLeft"), inputTwist);

          double frontRightSpeed = frontRightVector[1];
          double frontLeftSpeed = frontLeftVector[1];
          double backRightSpeed = backRightVector[1];
          double backLeftSpeed = backLeftVector[1];

          double frontRightAngle = frontRightVector[0];
          double frontLeftAngle = frontLeftVector[0];
          double backRightAngle = backRightVector[0];
          double backLeftAngle = backLeftVector[0];

          backRight.drive(backRightSpeed, backRightAngle);
          backLeft.drive(-backLeftSpeed, backLeftAngle);
          frontRight.drive(frontRightSpeed, frontRightAngle);
          frontLeft.drive(-frontLeftSpeed,frontLeftAngle);
     }
}
