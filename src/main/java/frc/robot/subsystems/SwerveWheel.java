package frc.robot.subsystems;
import frc.robot.util.TurnEncoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
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

    // private RelativeEncoder driveEncoder;
    private TurnEncoder turnEncoder;

    private int m_turnEncoderPort;
    // private int m_drivePort;
    
    private PIDController turnPidController;
    private PIDController drivePidController;
    
    private SimpleMotorFeedforward driveFeedforward;

    public SwerveWheel(int turnPort, int drivePort, int turnEncoderPort) {

        turnMotor = new TalonSRX(turnPort);
        driveMotor = new CANSparkMax(drivePort, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        turnEncoder = new TurnEncoder(turnEncoderPort);

        // driveEncoder = driveMotor.getEncoder();
        m_turnEncoderPort = turnEncoderPort;
        // m_drivePort = drivePort;

        turnPidController = new PIDController(.01, 0, 0);
        turnPidController.setTolerance(4);
        turnPidController.enableContinuousInput(0,  360);

        drivePidController = new PIDController(.01, 0, 0);
        driveFeedforward = new SimpleMotorFeedforward(.1, 473);
    }

    private double wrapAroundAngles(double input) {
        return input < 0 ? 360 + input : input;
    }

    public double convertCentiMeterSecond(double rpm) {
        double diameter = 0.00101;//101 millimeters
        return ((rpm / 7) * ((Math.PI * diameter) / 60)) / 100;
    
        // 7:1 (Motor to wheel)
           
    }

    // private double[] optimizeWheelPositions(double angle, double encoderValue) {
    //     double[] ret  = {1, angle};
    //     double change = Math.abs(angle - encoderValue);

    //     if ((change / 180) >= 1) {
    //         ret[0] = -1;
    //         ret[1] = angle % 180;
    //     }

    //     return ret;
    // }

    public void drive(double speed, double angle) {
        SmartDashboard.putNumber(m_turnEncoderPort + " angle input", angle);
        SmartDashboard.putNumber(m_turnEncoderPort + " speed input", speed);

        double currentDriveSpeed = convertCentiMeterSecond(speed);
        double turnValue = wrapAroundAngles(turnEncoder.get());
        angle = wrapAroundAngles(angle);

        SmartDashboard.putNumber(m_turnEncoderPort + " encoder angle", turnValue);
        //double[] newPositions = optimizeWheelPositions(angle, turnValue);
        
        double turnPIDOutput = turnPidController.calculate(turnValue, angle);//MathUtil.clamp(turnPidController.calculate(turnValue, angle), -1, 1);

        double drivePIDOutput = drivePidController.calculate(currentDriveSpeed, speed);
        double driveFeedForwardOutput = driveFeedforward.calculate(currentDriveSpeed, speed);

        SmartDashboard.putNumber(m_turnEncoderPort + " drive set", drivePIDOutput + driveFeedForwardOutput);
        SmartDashboard.putNumber(m_turnEncoderPort + " turn set", turnPIDOutput);


        driveMotor.set((drivePIDOutput + driveFeedForwardOutput) * 1.2);
        if (!turnPidController.atSetpoint()) {
            turnMotor.set(ControlMode.PercentOutput, turnPIDOutput);
        }
    }
}
