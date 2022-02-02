package frc.robot.subsystems;
import frc.robot.util.TurnEncoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
//leaving the below imports to remember that profiledPIDControllers exist, and that feedforwards exist in case we need to use them
// import edu.wpi.first.math.controller.ProfiledPIDController;
// import edu.wpi.first.math.trajectory.TrapezoidProfile;
// import edu.wpi.first.math.controller.SimpleMotorFeedforward;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.kinematics.SwerveModuleState;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SwerveWheel {
    private TalonSRX turnMotor;
    private CANSparkMax driveMotor;

    private TurnEncoder turnEncoder;

    private int m_turnEncoderPort;
    
    private PIDController turnPidController;
    private PIDController drivePidController;
    
    private SimpleMotorFeedforward driveFeedforward;

    public SwerveWheel(int turnPort, int drivePort, int turnEncoderPort) {

        turnMotor = new TalonSRX(turnPort);
        driveMotor = new CANSparkMax(drivePort, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        turnEncoder = new TurnEncoder(turnEncoderPort);

        m_turnEncoderPort = turnEncoderPort;

        turnPidController = new PIDController(.01, 0, 0);
        turnPidController.setTolerance(4);
        turnPidController.enableContinuousInput(0,  360);

        drivePidController = new PIDController(.3, 0, 0);
        driveFeedforward = new SimpleMotorFeedforward(.1, 473);
    }

    private double wrapAroundAngles(double input) {
        return input < 0 ? 360 + input : input;
    }

    public double convertCentiMeterSecond(double rpm) {
        double diameter = 0.00101; //101 millimeters
        //Math to convert from rotations per minute to centimeters per second
        return ((rpm / 7) * ((Math.PI * diameter) / 60)) / 100;
        // Ratio is 7:1 (Motor to wheel)
    }


    private double[] optimizeAngles(double angle, double encoderValue, double speed) {
        // TODO: Fix the optimization breaking at 360 degres
        double oppositeAngle = (angle + 180) % 360;
        double oppositeAngleDistance = Math.abs(encoderValue - oppositeAngle);

        double angleDistance = Math.abs(encoderValue - angle);

        if (angleDistance < oppositeAngleDistance) {
            return new double[] {speed, angle};
        }

        return new double[] {-speed, oppositeAngle}; 
    }

    public void drive(double speed, double angle) {
        // TODO: fix PIDs becuase they are currently limiting the speed to 0.1

        SmartDashboard.putNumber(m_turnEncoderPort + " angle input", angle);
        SmartDashboard.putNumber(m_turnEncoderPort + " speed input", speed);

        double currentDriveSpeed = convertCentiMeterSecond(speed);
        double turnValue = wrapAroundAngles(turnEncoder.get());
        angle = wrapAroundAngles(angle);
        double[] optimizedAngles = optimizeAngles(angle, turnValue, speed);
        angle = optimizedAngles[1];
        speed = optimizedAngles[0];

        SmartDashboard.putNumber(m_turnEncoderPort + " encoder angle", turnValue);
        
        SmartDashboard.putNumber(m_turnEncoderPort + " drive encoder ", currentDriveSpeed);

        double turnPIDOutput = turnPidController.calculate(turnValue, angle);

        double drivePIDOutput = drivePidController.calculate(currentDriveSpeed, speed);

        // SmartDashboard.putNumber(m_turnEncoderPort + " pid value", drivePIDOutput);

        double driveFeedForwardOutput = driveFeedforward.calculate(currentDriveSpeed, speed);

        // SmartDashboard.putNumber(m_turnEncoderPort + " feedforward value", driveFeedForwardOutput);

        SmartDashboard.putNumber(m_turnEncoderPort + " drive set", MathUtil.clamp(drivePIDOutput + driveFeedForwardOutput, -.7, .7));
        // SmartDashboard.putNumber(m_turnEncoderPort + " turn set", turnPIDOutput);

        //70% speed is about 5.6 feet/second
        driveMotor.set(MathUtil.clamp(drivePIDOutput + driveFeedForwardOutput, -.7, .7));
        if (!turnPidController.atSetpoint()) {
           turnMotor.set(ControlMode.PercentOutput, MathUtil.clamp(turnPIDOutput, -.7, .7));
        }
    }
}
