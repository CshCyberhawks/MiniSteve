package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.util.Gyro;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;


public class SwerveDriveTrain {
     //https://jacobmisirian.gitbooks.io/frc-swerve-drive-programming/content/chapter1.html
     private SwerveWheel backLeft;
     private SwerveWheel backRight;
     private SwerveWheel frontLeft;
     private SwerveWheel frontRight;
     private Gyro gyro;

     public SwerveDriveTrain() {
         gyro = new Gyro();
         backLeft = new SwerveWheel(Constants.backLeftTurnMotor, Constants.backLeftDriveMotor, Constants.backLeftEncoder);
         backRight = new SwerveWheel(Constants.backRightTurnMotor, Constants.backRightDriveMotor, Constants.backRightEncoder);
         frontLeft = new SwerveWheel(Constants.frontLeftTurnMotor, Constants.frontLeftDriveMotor, Constants.frontLeftEncoder);
         frontRight = new SwerveWheel(Constants.frontRightTurnMotor, Constants.frontRightDriveMotor, Constants.frontRightEncoder);
         gyro.reset();
    }        


    public double evaluateTheta(double theta) {
          //quadrant 1
          if (theta <= 90) {
               return 90 - theta;
          }
          //quadrant 2
          else if (theta > 90 && theta <= 180) {
               return -(90 - (180 - theta));
          }
          //quadrant 3
          else if (theta > 180 && theta <= 270) {
               return -(180 - (270 - theta));
          }
          //quadrant 4
          else {
               return -(270 - (360 - theta));
          }
     }

     public double thetaToPolar(double theta) {
          if (theta <= 90 && theta >= -180) {
               return -(theta) + 90;
          } else if (theta == 135) {
               return theta + 180;
          } else if (theta > 90 && theta < 135) {
               return (theta + 180) - (2 * (theta - 135));
          }
          else {
               return (theta + 180) + (2 * (theta - 135));
          }
     }

     public double[] fieldOriented(double x, double y, double gyroAngle) {
          
          double[] polar = cartesianToPolar(x, y);
          double theta = polar[0] + gyroAngle;

          double r = polar[1];

          double[] ret = polarToCartesian(theta, r);
          return ret;
     }


     public double[] calculateDrive(double x1, double y1, double theta2, double r2) {
          double[] coord1 = fieldOriented(x1, y1, gyro.getAngle());
          double[] coord2 = polarToCartesian(theta2, r2);

          double[] ret = cartesianToPolar(coord1[0] + coord2[0], coord1[1] + coord2[1]);
          return ret;
     }

     public double[] polarToCartesian(double theta, double r) {
          double[] ret = {r * Math.cos(Math.toRadians(theta)), r * Math.sin(Math.toRadians(theta))};
          return ret;
     }

     public double[] cartesianToPolar(double x, double y) {
          double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
          double theta = Math.toDegrees(Math.atan2(y, x));

          double[] ret = {theta, r};
          return ret;
     }

     public void drive(double inputX, double inputY, double inputTwist) {

          // double theta = input[0];
          // double r = input[1];
          // double twist = input[2];

          double gyroAngle = gyro.getAngle();
          SmartDashboard.putNumber("gyro val", gyroAngle);
          //gyro angle subtraction should work
          double translationAngle = inputTwist;//evaluateTheta(theta);


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


          SmartDashboard.putNumber("backleft s", backLeftSpeed);
          SmartDashboard.putNumber("backleft a", backLeftAngle);

          backRight.drive(backRightSpeed, backRightAngle);
          backLeft.drive(-backLeftSpeed, backLeftAngle);
          frontRight.drive(frontRightSpeed, frontRightAngle);
          frontLeft.drive(-frontLeftSpeed,frontLeftAngle);


     }
}
