

// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.math.kinematics.*;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import com.kauailabs.navx.frc.AHRS;
// import edu.wpi.first.wpilibj.SPI;

// public class OldSwerveSubsystem extends SubsystemBase {
  
//    public static final double motorDist = .53; //53cm

//    private Translation2d motorFrontLeftLocation = new Translation2d(motorDist, motorDist);
//    private Translation2d motorFrontRightLocation = new Translation2d(motorDist, -motorDist);
//    private Translation2d motorBackLeftLocation = new Translation2d(-motorDist, motorDist);
//    private Translation2d motorBackRightLocation = new Translation2d(-motorDist, -motorDist);

//    AHRS gyro = new AHRS(SPI.Port.kMXP);
   
//    private SwerveDriveKinematics swerveDriveKinematics = new SwerveDriveKinematics(motorFrontLeftLocation, motorFrontRightLocation, motorBackLeftLocation, motorBackRightLocation);
   
//    private double velocityX = 1;
//    private double velocityY = 1;
//    private double distanceX; //Dim of field X
//    private double distanceY; //Dim of field Y
//    private double theta = 0; //Rotation aka theta (radians)
   
//    public OldSwerveSubsystem() {
       
//    }

//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//     ChassisSpeeds chassisSpeed = ChassisSpeeds.fromFieldRelativeSpeeds(velocityX, velocityY, theta, gyro.getRotation2d());
//     // SwerveDriveOdometry swerveDriveOdometry = new SwerveDriveOdometry(swerveDriveKinematics, getGyroHeading(), new Pose2d(distanceX, distanceY, new Rotation2d())); //Placeholder 
    
//     SwerveModuleState[] swerveStates = swerveDriveKinematics.toSwerveModuleStates(chassisSpeed);

//     SwerveModuleState frontLeftMotorState = swerveStates[0];
//     SwerveModuleState frontRightMotorState = swerveStates[1];
//     SwerveModuleState backLeftMotorState = swerveStates[2];
//     SwerveModuleState backRightMotorState = swerveStates[3];
    
//     Rotation2d gyroAngle = Rotation2d.fromDegrees(-gyro.getAngle());
//     // Pose2d swerveDriveUpdate = swerveDriveOdometry.update(gyroAngle, frontLeftMotorState, frontRightMotorState, backRightMotorState);

    

//   }

//   public void setVelocities(double inputX, double inputY, double inputTheta) {
//     velocityX = inputX;
//     velocityY = inputY;
//     theta = inputTheta;
//   }


//   @Override
//   public void simulationPeriodic() {
//     // This method will be called once per scheduler run during simulation
//   }
// }