package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.util.Gyro;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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

    public void drive(double inputX, double inputY, double theta) {
        double gyroAngle = gyro.getRotation2d();
        SmartDashboard.putNumber("gyro val", gyroAngle);
        SmartDashboard.putBoolean("gyro connected", gyro.isConnected());

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

        backRight.drive(backRightSpeed, backRightAngle);
        backLeft.drive(-backLeftSpeed, backLeftAngle);
        frontRight.drive(frontRightSpeed, frontRightAngle);
        frontLeft.drive(-frontLeftSpeed,frontLeftAngle);
     }
}
