package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.util.Gyro;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import java.lang.Math;


public class SwerveDriveTrain extends SubsystemBase {
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
    }        


     public double calculateAngle(double translationAngle, double twist, double r, String wheel) {
          //averages the constant twistAngle with the translationAngle input from the joystick to swerve the robot?
          if (twist != 0 && r != 0) {
               return (translationAngle + Constants.twistAngleMap.get(wheel)) / 2;
          }
          else if (twist == 0) {
               return translationAngle;
          }
          else {
               return Constants.twistAngleMap.get(wheel);
          }
     }

     public double wheelSpeed(double twist, double r, String wheel) {
          //if the twist is greater than 0, return the average of twist and r (the speed of the joystick translation) * (constant speed multiplier for each wheel (either -1 or 1) * 1)
          // else same thing but the constant speed multiplier is multiplied by -1
          if (twist == 0) {
               return r;
          }
          else if (twist > 0) {
               return ((twist + r) / 2);
          }
          else {
               return -((twist + r) / 2);
          }
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


     public void drive(double[] input) {

          double theta = input[0];
          double r = input[1];
          double twist = input[2];

          double gyroAngle = gyro.getAngle();
          SmartDashboard.putNumber("gyro val", gyroAngle);
          //gyro angle subtraction should work
          double translationAngle = theta;//evaluateTheta(theta); //- gyroAngle;

          SmartDashboard.putNumber("input theta ", theta);
          SmartDashboard.putNumber("input r ", r);
          SmartDashboard.putNumber("input twist ", twist);
          SmartDashboard.putNumber("translation angle ", translationAngle);

          //this code calls the calculate angle function to get the angle for each wheel
          double frontRightAngle = calculateAngle(translationAngle, twist, r, "frontRight");
          double frontLeftAngle = calculateAngle(translationAngle, twist, r, "frontLeft");
          double backRightAngle = calculateAngle(translationAngle, twist, r, "backRight");
          double backLeftAngle = calculateAngle(translationAngle, twist, r, "backLeft");
          

          //this code calls the wheelSpeed function to get the speed for each wheel unless twist = 0, in which case the speeds only equal r
          double frontRightSpeed = wheelSpeed(twist, r, "frontRight");
          double frontLeftSpeed = wheelSpeed(twist, r, "frontLeft");
          double backRightSpeed = wheelSpeed(twist, r, "backRight");
          double backLeftSpeed = wheelSpeed(twist, r, "backLeft");

          //might need to add negatives below
          backRight.drive(backRightSpeed, backRightAngle);
          backLeft.drive(-backLeftSpeed, backLeftAngle);
          frontRight.drive(frontRightSpeed, frontRightAngle);
          frontLeft.drive(-frontLeftSpeed,frontLeftAngle);

     /*
     //old swerve drive code
        double x = inputX * Math.cos(gyroAngle) + inputY * Math.sin(gyroAngle);
        double y = -inputX * Math.sin(gyroAngle) + inputY * Math.cos(gyroAngle);

        double r = Math.sqrt((Constants.length * Constants.length) + (Constants.length * Constants.length));
 
        double a = x - theta * (Constants.length / r);
        double b = x + theta * (Constants.length / r);
        double c = y - theta * (Constants.length / r);
        double d = y + theta * (Constants.length / r);

        double backRightSpeed = Math.sqrt((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt((b * b) + (c * c));

        double backRightAngle = ((Math.atan2(a, d) / Math.PI) * 180);
        double backLeftAngle = ((Math.atan2(a, c) / Math.PI) * 180);
        double frontRightAngle = ((Math.atan2(b, d) / Math.PI) * 180);
        double frontLeftAngle = ((Math.atan2(b, c) / Math.PI) * 180);
     */

     }
}
