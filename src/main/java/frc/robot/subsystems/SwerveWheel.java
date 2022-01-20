package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.TurnEncoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.controller.PIDController;
//leaving the below imports to remember that profiledPIDControllers exist, and that feedforwards exist in case we need to use them
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveWheel extends SubsystemBase{

    private TalonSRX turnMotor;
    private CANSparkMax driveMotor;
    private RelativeEncoder driveEncoder;
    private TurnEncoder turnEncoder;
    private int m_turnEncoderPort;

    private PIDController turnPID = new PIDController(1, 0, 0);

    private PIDController drivePID = new PIDController(1, 0, 0);

    //can use wpilib encoder class for turning encoder bc they are wired into robo rio
    //need to use revrobotics encoders for neo encoders because they are wired into sparkmax motor controllers

    public SwerveWheel(int turnPort, int drivePort, int turnEncoderPort) {
        turnMotor = new TalonSRX(turnPort);
        driveMotor = new CANSparkMax(drivePort, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        turnEncoder = new TurnEncoder(turnEncoderPort);
        driveEncoder = driveMotor.getEncoder();
        m_turnEncoderPort = turnEncoderPort;
    }
 
    public SwerveModuleState getState() {
        SmartDashboard.putNumber("turn encoder" + m_turnEncoderPort, turnEncoder.getAngle());
        SmartDashboard.putNumber("drive encoder" + m_turnEncoderPort, driveEncoder.getVelocity());

        SwerveModuleState swerveModuleState = new SwerveModuleState(driveEncoder.getVelocity(), new Rotation2d(turnEncoder.getAngle()));
        return swerveModuleState;
    }

    public void setState(SwerveModuleState _desiredState) {
        SwerveModuleState desiredState = SwerveModuleState.optimize(_desiredState, new Rotation2d(turnEncoder.getAngle()));

        double turnOutput = turnPID.calculate(turnEncoder.getAngle(), desiredState.angle.getDegrees());
        double driveOutput = drivePID.calculate(driveEncoder.getVelocity(), desiredState.speedMetersPerSecond);

        driveMotor.setVoltage(driveOutput);
        turnMotor.set(ControlMode.Current, turnOutput);


    }
}
