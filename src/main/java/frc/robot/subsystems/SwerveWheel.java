package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.TurnEncoder;

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.CAN;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
//leaving the below imports to remember that profiledPIDControllers exist, and that feedforwards exist in case we need to use them
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SwerveWheel {
    private TalonSRX turnMotor;
    private CANSparkMax driveMotor;

    private RelativeEncoder driveEncoder;
    private TurnEncoder turnEncoder;

    private int m_turnEncoderPort;
    private int m_drivePort;
    //
    private PIDController turnPidController;

    private PIDController drivePidController;
    private SimpleMotorFeedforward driveFeedforward;

    public SwerveWheel(int turnPort, int drivePort, int turnEncoderPort) {

        turnMotor = new TalonSRX(turnPort);
        driveMotor = new CANSparkMax(drivePort, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        turnEncoder = new TurnEncoder(turnEncoderPort);

        driveEncoder = driveMotor.getEncoder();
        m_turnEncoderPort = turnEncoderPort;
        m_drivePort = drivePort;

        turnPidController = new PIDController(10, 0, 0);
        turnPidController.enableContinuousInput(-1,  1);

        drivePidController = new PIDController(.01, 0, 0);
        driveFeedforward = new SimpleMotorFeedforward(.1, 473);
    }


    public double convertCentiMeterSecond(double rpm)
    {
        double diameter = 0.00101;//101 millimeters
        return ((rpm / 7) * ((Math.PI * diameter) / 60)) / 100;
    
        // 7:1 (Motor to wheel)
           
    }

    public void drive(double speed, double angle) {
        SmartDashboard.putNumber(m_turnEncoderPort + " angle input", angle);
        SmartDashboard.putNumber(m_turnEncoderPort + " speed input", speed);
        double currentDriveSpeed = convertCentiMeterSecond(speed);
        double turnValue = turnEncoder.get() / 5;
        SmartDashboard.putNumber(m_turnEncoderPort + " encoder angle", turnValue);
        double turnPIDOutput = turnPidController.calculate(turnValue, angle);

        double drivePIDOutput = drivePidController.calculate(currentDriveSpeed, speed);
        double driveFeedForwardOutput = driveFeedforward.calculate(currentDriveSpeed, speed);

        SmartDashboard.putNumber(m_turnEncoderPort + " drive set", drivePIDOutput + driveFeedForwardOutput);
        SmartDashboard.putNumber(m_turnEncoderPort + " turn set", turnPIDOutput);

        //driveMotor.set(drivePIDOutput + driveFeedForwardOutput);
        turnMotor.set(ControlMode.PercentOutput, turnPIDOutput);
        
    }
}
