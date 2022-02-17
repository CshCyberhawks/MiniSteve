package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.FieldPosition;
import frc.robot.util.Gyro;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.lang.Math;
import edu.wpi.first.util.WPIUtilJNI;

public class SwerveDriveTrain extends SubsystemBase {
     public SwerveWheel backLeft;
     public SwerveWheel backRight;
     public SwerveWheel frontLeft;
     public SwerveWheel frontRight;
     public Gyro gyro;
     public double throttle = 0.0;

     public PIDController xPID;
     public PIDController yPID;

     public SwerveWheel[] wheelArr = new SwerveWheel[4];

     public boolean isTwisting = false;

     private double lastUpdateTime = 1;

     public SwerveDriveTrain() {
          xPID = new PIDController(8, 0, 0);
          yPID = new PIDController(8, 0, 0);

          backLeft = new SwerveWheel(Constants.backLeftTurnMotor, Constants.backLeftDriveMotor,
                    Constants.backLeftEncoder);
          backRight = new SwerveWheel(Constants.backRightTurnMotor, Constants.backRightDriveMotor,
                    Constants.backRightEncoder);
          frontLeft = new SwerveWheel(Constants.frontLeftTurnMotor, Constants.frontLeftDriveMotor,
                    Constants.frontLeftEncoder);
          frontRight = new SwerveWheel(Constants.frontRightTurnMotor, Constants.frontRightDriveMotor,
                    Constants.frontRightEncoder);

          wheelArr[0] = backLeft;
          wheelArr[1] = backRight;
          wheelArr[2] = frontLeft;
          wheelArr[3] = frontRight;

          Gyro.setOffset();
     }

     public double[] polarToCartesian(double theta, double r) {
          // math to turn polar coordinate into cartesian
          double x = r * Math.cos(Math.toRadians(theta));
          double y = r * Math.sin(Math.toRadians(theta));

          double[] ret = { x, y };
          return ret;
     }

     public double[] cartesianToPolar(double x, double y) {
          // math to turn cartesian into polar
          double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
          double theta = Math.toDegrees(Math.atan2(y, x));

          double[] ret = { theta, r };
          return ret;
     }

     public double[] fieldOriented(double x, double y, double gyroAngle) {
          // turns the translation input into polar
          double[] polar = cartesianToPolar(x, y);
          // subtracts the gyro angle from the polar angle of the translation of the robot
          // makes it field oriented
          double theta = polar[0] + gyroAngle;

          double r = polar[1];

          // returns the new field oriented translation but converted to cartesian
          double[] ret = polarToCartesian(theta, r);
          return ret;
     }

     public double[] calculateDrive(double x1, double y1, double theta2, double r2) {
          // X is 0 and Y is 1
          // Gets the cartesian coordinate of the robot's joystick translation inputs
          double[] driveCoordinate = fieldOriented(x1, y1, Gyro.getAngle());
          // Turns the twist constant + joystick twist input into a cartesian coordinates
          double[] twistCoordinate = polarToCartesian(theta2, r2);

          // Args are theta, r
          // Vector math adds the translation and twisting cartesian coordinates before
          // turning them into polar and returning
          double[] ret = cartesianToPolar(driveCoordinate[0] + twistCoordinate[0],
                    driveCoordinate[1] + twistCoordinate[1]);
          return ret;
     }

     public void drive(double inputX, double inputY, double inputTwist, double throttleChange) {
          double timeNow = WPIUtilJNI.now() * 1.0e-6;
          double period = lastUpdateTime >= 0 ? timeNow - lastUpdateTime : 0.0;

          // mult by 10 is just a guess
          throttle = MathUtil.clamp(throttle += MathUtil.clamp(throttleChange / 200, -.1, .1), .05, 1);

          SmartDashboard.putNumber("throttle ", throttle);
          SmartDashboard.putNumber("drive inputX ", inputX);
          SmartDashboard.putNumber("drive inputY ", inputY);
          SmartDashboard.putNumber("drive inputTwist ", inputTwist);

          FieldPosition robotPos = Robot.swo.getPosition();

          // 7.3 f/s
          // below .292 controls speed
          // take speed in ft/s
          // convert to swos(6 in)
          // multiply by 0.02 (update loop)
	 double pidPredictX = robotPos.positionCoord.x + (inputX * (24.72 * period));
	 double pidPredictY = robotPos.positionCoord.y + (inputY * (24.72 * period));

	 SmartDashboard.putNumber("pidPredictX", pidPredictX);
	 SmartDashboard.putNumber("pidPredictY", pidPredictY);

          double pidInputX = xPID.calculate(robotPos.positionCoord.x, pidPredictX);
          double pidInputY = yPID.calculate(robotPos.positionCoord.y, pidPredictY);

	

          inputX = pidInputX * throttle;
          inputY = pidInputY  * throttle;

          SmartDashboard.putNumber("testInputX ", pidInputX);
          SmartDashboard.putNumber("testInputY ", pidInputY);

          isTwisting = inputTwist != 0;

          double gyroAngle = Gyro.getAngle();
          SmartDashboard.putNumber("gyro val", gyroAngle);

          // calculates the speed and angle for each motor
          double[] frontRightVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("frontRight"),
                    inputTwist);
          double[] frontLeftVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("frontLeft"),
                    inputTwist);
          double[] backRightVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("backRight"), inputTwist); double[] backLeftVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("backLeft"), inputTwist);
          double frontRightSpeed = frontRightVector[1];
          double frontLeftSpeed = frontLeftVector[1];
          double backRightSpeed = backRightVector[1];
          double backLeftSpeed = backLeftVector[1];

          double frontRightAngle = frontRightVector[0];
          double frontLeftAngle = frontLeftVector[0];
          double backRightAngle = backRightVector[0];
          double backLeftAngle = backLeftVector[0];

          // sets the speed and angle of each motor
          backRight.drive(backRightSpeed, backRightAngle);
          backLeft.drive(-backLeftSpeed, backLeftAngle);
          frontRight.drive(frontRightSpeed, frontRightAngle);
          frontLeft.drive(-frontLeftSpeed, frontLeftAngle);

          lastUpdateTime = timeNow;
     }
}
