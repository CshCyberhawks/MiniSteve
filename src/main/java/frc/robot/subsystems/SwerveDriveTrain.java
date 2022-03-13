package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.FieldPosition;
import frc.robot.util.Gyro;
import frc.robot.util.MathClass;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.lang.Math;
import edu.wpi.first.util.WPIUtilJNI;
import frc.robot.util.Vector2;

public class SwerveDriveTrain extends SubsystemBase {
        public SwerveWheel backLeft;
        public SwerveWheel backRight;
        public SwerveWheel frontLeft;
        public SwerveWheel frontRight;
        public Gyro gyro;
        public double throttle = 0.35;

        public PIDController xPID;
        public PIDController yPID;

        public Vector2 predictedVelocity;

        public SwerveWheel[] wheelArr = new SwerveWheel[4];

        public boolean isTwisting = false;

        // I do this to prevent large jumps in value with first run of loop in predicted
        // odometry
        private double lastUpdateTime = -1;

        double maxSwos = 13.9458;
        double maxMeters = 3.777;

        public SwerveDriveTrain() {
                // p = 10 gets oscillation
                xPID = new PIDController(10, 0, 1);
                yPID = new PIDController(10, 0, 1);

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

                predictedVelocity = new Vector2(0, 0);

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

        public double[] calculateDrive(double x1, double y1, double theta2, double r2, double twistMult) {
                // X is 0 and Y is 1
                // Gets the cartesian coordinate of the robot's joystick translation inputs
                double[] driveCoordinate = fieldOriented(x1, y1, Gyro.getAngle());
                // Turns the twist constant + joystick twist input into a cartesian coordinates
                double[] twistCoordinate = polarToCartesian(theta2, r2 * twistMult);

                // Args are theta, r
                // Vector math adds the translation and twisting cartesian coordinates before
                // turning them into polar and returning
                // can average below instead of add - need to look into it
                double[] ret = cartesianToPolar(driveCoordinate[0] + twistCoordinate[0],
                                driveCoordinate[1] + twistCoordinate[1]);
                return ret;
        }

        public void drive(double inputX, double inputY, double inputTwist, double throttleChange, String mode) {
                double timeNow = WPIUtilJNI.now() * 1.0e-6;
                double period = lastUpdateTime >= 0 ? timeNow - lastUpdateTime : 0.0;
                double gyroAngle = Gyro.getAngle();

                throttle = MathUtil.clamp(throttle += MathUtil.clamp(throttleChange / 125, -.1, .1), .05, 1);

                SmartDashboard.putNumber("throttle ", throttle);
                SmartDashboard.putNumber("gyro val", gyroAngle);

                if (inputX == 0 && inputY == 0 && inputTwist == 0) {
                        backRight.preserveAngle();
                        backLeft.preserveAngle();
                        frontRight.preserveAngle();
                        frontLeft.preserveAngle();
                        lastUpdateTime = timeNow;
                        return;
                }

                /*
                 * double highestSpeed = Math.max(inputX, inputY) > Math.abs(Math.min(inputX,
                 * inputY))
                 * ? Math.max(inputX, inputY)
                 * : Math.abs(Math.min(inputX, inputY));
                 */
                // random decimal below is the max speed of robot in swos
                // double constantScaler = 13.9458 * highestSpeed;

                SmartDashboard.putNumber("drive inputTwist ", inputTwist);

                // FieldPosition robotPos = Robot.swo.getPosition();

                inputX = mode != "auto" ? inputX * throttle : inputX;
                inputY = mode != "auto" ? inputY * throttle : inputY;
                inputTwist = mode != "auto" ? inputTwist * throttle : inputTwist;

                SmartDashboard.putNumber("drive inputX ", inputX);
                SmartDashboard.putNumber("drive inputY ", inputY);

                // double pidPredictX = inputX * maxSwos * period;
                // double pidPredictY = inputY * maxSwos * period;

                // double pidInputX = xPID.calculate(Robot.swo.getVelocities()[0], pidPredictX)
                // / maxSwos;
                // double pidInputY = yPID.calculate(Robot.swo.getVelocities()[1], pidPredictY)
                // / maxSwos;

                // inputX += pidInputX;
                // inputY += pidInputY;

                isTwisting = inputTwist != 0;

                // calculates the speed and angle for each motor
                double[] frontRightVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("frontRight"),
                                inputTwist, Constants.twistSpeedMap.get("frontRight"));
                double[] frontLeftVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("frontLeft"),
                                inputTwist, Constants.twistSpeedMap.get("frontLeft"));
                double[] backRightVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("backRight"),
                                inputTwist,
                                Constants.twistSpeedMap.get("backRight"));
                double[] backLeftVector = calculateDrive(inputX, inputY, Constants.twistAngleMap.get("backLeft"),
                                inputTwist,
                                Constants.twistSpeedMap.get("backLeft"));

                double frontRightSpeed = frontRightVector[1];
                double frontLeftSpeed = frontLeftVector[1];
                double backRightSpeed = backRightVector[1];
                double backLeftSpeed = backLeftVector[1];

                double frontRightAngle = frontRightVector[0];
                double frontLeftAngle = frontLeftVector[0];
                double backRightAngle = backRightVector[0];
                double backLeftAngle = backLeftVector[0];

                double[] wheelSpeeds = { frontRightSpeed, frontLeftSpeed, backRightSpeed, backLeftSpeed };

                wheelSpeeds = MathClass.normalizeSpeeds(wheelSpeeds, 1, -1);

                // SmartDashboard.putNumber("frontRightAngle", frontRightAngle);
                // SmartDashboard.putNumber("frontLeftAngle", frontLeftAngle);
                // SmartDashboard.putNumber("backRightAngle", backRightAngle);
                // SmartDashboard.putNumber("backLeftAngle", backLeftAngle);

                // sets the speed and angle of each motor
                backRight.drive(wheelSpeeds[2], backRightAngle, mode);
                backLeft.drive(wheelSpeeds[3], backLeftAngle, mode);
                frontRight.drive(wheelSpeeds[0], frontRightAngle, mode);
                frontLeft.drive(wheelSpeeds[1], frontLeftAngle, mode);

                // predictedVelocity.x = inputX * maxSwos * period;
                // predictedVelocity.y = inputY * maxSwos * period;

                lastUpdateTime = timeNow;
        }

        // public void resetPredictedOdometry() {
        // predictedVelocity = new Vector2(0, 0);
        // }

}
