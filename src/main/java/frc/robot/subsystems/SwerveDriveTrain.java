package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveDriveTrain extends SubsystemBase{
    private final double motorDist = .53; //53cm (In meters)

    private Translation2d frontLeftMotorLocation = new Translation2d(motorDist, motorDist);
    private Translation2d frontRightMotorLocation = new Translation2d(motorDist, -motorDist);
    private Translation2d backLeftMotorLocation = new Translation2d(-motorDist, motorDist);
    private Translation2d backRightMotorLocation = new Translation2d(-motorDist, -motorDist);

    private SwerveDriveKinematics swerveDriveKinematics = new SwerveDriveKinematics(frontLeftMotorLocation, frontRightMotorLocation, backLeftMotorLocation, backRightMotorLocation);

    private AHRS gyro = new AHRS(SPI.Port.kMXP);
    
    private final SwerveDriveOdometry swerveDriveOdometry = new SwerveDriveOdometry(swerveDriveKinematics, gyro.getRotation2d());

    private double velocityX = 0;
    private double velocityY = 0;
    private double theta = 0; //Rotation 

    private SwerveWheel frontLeftSwerveWheel = new SwerveWheel(Constants.frontLeftTurnMotor, Constants.frontLeftDriveMotor, Constants.frontLeftEncoder);
    private SwerveWheel frontRightSwerveWheel = new SwerveWheel(Constants.frontRightTurnMotor, Constants.frontRightDriveMotor, Constants.frontRightEncoder);
    private SwerveWheel backLeftSwerveWheel = new SwerveWheel(Constants.backLeftTurnMotor, Constants.backLeftDriveMotor, Constants.backLeftEncoder);
    private SwerveWheel backRightSwerveWheel = new SwerveWheel(Constants.backRightTurnMotor, Constants.backRightDriveMotor, Constants.backRightEncoder);

    public SwerveDriveTrain() {
        gyro.reset();
    }

    public void setVelocities(double inputX, double inputY, double inputTheta) {
        velocityX = inputX;
        velocityY = inputY;
        theta = inputTheta;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("VelocityX", velocityX);
        SmartDashboard.putNumber("VelocityY", velocityY);
        SmartDashboard.putNumber("Theta", theta);

        ChassisSpeeds chassisSpeed = ChassisSpeeds.fromFieldRelativeSpeeds(velocityX, velocityY, theta, gyro.getRotation2d());

        SwerveModuleState[] swerveStates = swerveDriveKinematics.toSwerveModuleStates(chassisSpeed);

        SwerveDriveKinematics.desaturateWheelSpeeds(swerveStates, 3.0); //Caps the speed at 3 meters per second
        frontLeftSwerveWheel.setState(swerveStates[0]);
        frontRightSwerveWheel.setState(swerveStates[1]);
        backLeftSwerveWheel.setState(swerveStates[2]);
        backRightSwerveWheel.setState(swerveStates[3]);

        swerveDriveOdometry.update(gyro.getRotation2d(), frontLeftSwerveWheel.getState(), frontRightSwerveWheel.getState(), backLeftSwerveWheel.getState(), backRightSwerveWheel.getState());
    }
}
