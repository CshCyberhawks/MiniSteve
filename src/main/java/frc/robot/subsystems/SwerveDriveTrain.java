package frc.robot.subsystems;

import frc.robot.Constants;

public class SwerveDriveTrain {
    //https://jacobmisirian.gitbooks.io/frc-swerve-drive-programming/content/chapter1.html
    private SwerveWheel backLeft;
    private SwerveWheel backRight;
    private SwerveWheel frontLeft;
    private SwerveWheel frontRight;



    public SwerveDriveTrain()
     {
         backLeft = new SwerveWheel(Constants.backLeftTurnMotor, Constants.backLeftDriveMotor, Constants.backLeftEncoder);
         backRight = new SwerveWheel(Constants.backRightTurnMotor, Constants.backRightDriveMotor, Constants.backRightEncoder);
         frontLeft = new SwerveWheel(Constants.frontLeftTurnMotor, Constants.frontLeftDriveMotor, Constants.frontLeftEncoder);
         frontRight = new SwerveWheel(Constants.frontRightTurnMotor, Constants.frontRightDriveMotor, Constants.frontRightEncoder);
    }        

    public void drive(double x, double y, double theta)
    {
        double length = 0.53; 
        double r = Math.sqrt((length*length) + (length * length));
 
        double a = x - theta / (length / r);
        double b = x + theta / (length / r);
        double c = y - theta / (length / r);
        double d = y + theta / (length / r);

        double backRightSpeed = Math.sqrt((a * a) + (d*d));
        double backLeftSpeed = Math.sqrt((a * a) + (c *c));
        double frontRightSpeed = Math.sqrt((b*b) + (d*d));
        double frontLeftSpeed = Math.sqrt((b*b) + (c*c));

        double backRightAngle = Math.atan2(a, d) / Math.PI;
        double backLeftAngle = Math.atan2(a, c) / Math.PI;
        double frontRightAngle = Math.atan2(b, d) / Math.PI;
        double frontLeftAngle = Math.atan2(b, c) / Math.PI;

        backRight.drive(backRightSpeed, backRightAngle);
        backLeft.drive(backLeftSpeed, backLeftAngle);
        frontRight.drive(frontRightSpeed, frontRightAngle);
        frontLeft.drive(frontLeftSpeed,frontLeftAngle);
     }
    
}
