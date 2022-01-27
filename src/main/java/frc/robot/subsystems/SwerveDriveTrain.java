package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.util.Gyro;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

import javax.xml.namespace.QName;


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
    }

     private double[] calculateXY(double x, double y) {
          double xy[] = new double[2];

          //Turn Cartesian coordinates into polar coordinates
          double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
          double theta = Math.toDegrees(Math.atan2(y, x));

          theta = theta - gyro.getAngle();

          //Turn polar coordinates into cartesian coordinates
          xy[0] = r * Math.cos(Math.toRadians(theta));
          xy[1] = r * Math.sin(Math.toRadians(theta));

          return xy;
     }

     public void drive(double inputX, double inputY, double theta) {
          double newCords[] = calculateXY(inputX, inputY);
          double x = newCords[0];
          double y = newCords[1];

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

          backLeft.drive(backLeftSpeed, backLeftAngle);
          backRight.drive(backRightSpeed, backRightAngle);
          frontLeft.drive(frontLeftSpeed, frontLeftAngle);
          frontRight.drive(frontRightSpeed, frontRightAngle);
     }
}
