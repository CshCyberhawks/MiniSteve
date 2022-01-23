package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.TurnEncoder;

import edu.wpi.first.wpilibj.AnalogEncoder;
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

public class SwerveWheel extends SubsystemBase {

    private TalonSRX turnMotor;
    private CANSparkMax driveMotor;
    private RelativeEncoder driveEncoder;
    private TurnEncoder turnEncoder;
    private int m_turnEncoderPort;
    private int m_drivePort;

    private PIDController turnPID = new PIDController(.02, 0, 0);

    private PIDController drivePID = new PIDController(.02, 0, 0);

    // Can use wpilib encoder class for turning encoder bc they are wired into robo rio
    // need to use revrobotics encoders for neo encoders because they are wired into sparkmax motor controllers

    public SwerveWheel(int turnPort, int drivePort, int turnEncoderPort) {
        turnMotor = new TalonSRX(turnPort);
        driveMotor = new CANSparkMax(drivePort, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        turnEncoder = new TurnEncoder(turnEncoderPort);

        driveEncoder = driveMotor.getEncoder();
        m_turnEncoderPort = turnEncoderPort;
        m_drivePort = drivePort;

        turnPID.setTolerance(3);
        turnPID.enableContinuousInput(-180, 180);
    }
    
    public SwerveModuleState getState() {

        SwerveModuleState swerveModuleState = new SwerveModuleState(driveEncoder.getVelocity(), new Rotation2d(turnEncoder.getAngle()));
        return swerveModuleState;
    }

    private double wrapAroundAngles(double input) {
        return input < 0 ? 360 + input : input;
    }
    
    private double optimize(double currentState, double setPoint) {
        double add = wrapAroundAngles(setPoint) - wrapAroundAngles(currentState); //350
        double subtract = -360 + add; //-10
        if (Math.abs(add) <= Math.abs(subtract)) {
            return add;
        } 
        else {
            return subtract;
        }
    }
    public double convertMeterSecond(double rpm)
    {
        double diameter = 0.00101;//101 millimeters
        return (rpm / 7) * ((Math.PI * diameter) / 60);
    
        // 7:1 (Motor to wheel)
           
    }
    public void setState(SwerveModuleState desiredState) {
        double encoderAngle = turnEncoder.getAngle();

        SwerveModuleState _desiredState = SwerveModuleState.optimize(desiredState, new Rotation2d(encoderAngle * (Math.PI / 180)));
        
        double setPoint = optimize(encoderAngle, desiredState.angle.getDegrees());
        // SwerveModuleState desiredState = _desiredState;

        double turnOutput = turnPID.calculate(encoderAngle, desiredState.angle.getDegrees());
        SmartDashboard.putNumber(m_turnEncoderPort + "current velo drive: ", convertMeterSecond(driveEncoder.getVelocity()));
        SmartDashboard.putNumber(m_turnEncoderPort + "desired drive: ", desiredState.speedMetersPerSecond);

        SmartDashboard.putNumber(m_turnEncoderPort + " current angle: ", encoderAngle);

        double driveOutput = drivePID.calculate(convertMeterSecond(driveEncoder.getVelocity()), desiredState.speedMetersPerSecond);

        if (driveOutput > 0) {
            if (m_drivePort == 6 || m_drivePort == 8) {
                driveMotor.set(-driveOutput);
            }
            else {
                driveMotor.set(driveOutput);
            }
        } else {
            if (m_drivePort == 7 || m_drivePort == 9) {
                driveMotor.set(-driveOutput);
            }
            else {
                driveMotor.set(driveOutput);
            }
        }

        turnMotor.set(ControlMode.PercentOutput, turnOutput);
        SmartDashboard.putNumber(m_turnEncoderPort + " wheel turn: ", turnOutput);
        SmartDashboard.putNumber(m_turnEncoderPort + " Drive", driveOutput);
    }
}