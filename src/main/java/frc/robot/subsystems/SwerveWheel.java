package frc.robot.subsystems;

import frc.robot.util.TurnEncoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.math.controller.SimpleMotorFeedforward;
//leaving the below imports to remember that profiledPIDControllers exist, and that feedforwards exist in case we need to use them
// import edu.wpi.first.math.controller.ProfiledPIDController;
// import edu.wpi.first.math.trajectory.TrapezoidProfile;
// import edu.wpi.first.math.controller.SimpleMotorFeedforward;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.kinematics.SwerveModuleState;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveWheel {
    private TalonSRX turnMotor;
    private CANSparkMax driveMotor;
    private TurnEncoder turnEncoder;
    private RelativeEncoder driveEncoder;
    private PIDController turnPidController;
    private PIDController drivePidController;
    //private SimpleMotorFeedforward driveFeedforward;
    private int m_turnEncoderPort;

    public SwerveWheel(int turnPort, int drivePort, int turnEncoderPort) {
        turnMotor = new TalonSRX(turnPort);
        driveMotor = new CANSparkMax(drivePort, CANSparkMaxLowLevel.MotorType.kBrushless);

        driveEncoder = driveMotor.getEncoder();
        turnEncoder = new TurnEncoder(turnEncoderPort);

        m_turnEncoderPort = turnEncoderPort;

        turnPidController = new PIDController(.01, 0, 0);
        turnPidController.setTolerance(4);
        turnPidController.enableContinuousInput(0, 360);

        drivePidController = new PIDController(.3, 0, 0);
        //driveFeedforward = new SimpleMotorFeedforward(.1, 473);
    }

    private double wrapAroundAngles(double input) {
        return input < 0 ? 360 + input : input;
    }

    public double convertToMetersPerSecond(double rpm) {
        //double radius = 0.0505;
        // Gear ratio is 7:1
        // weird number = 2 * radius * pi / 60 (this is precaulculated)
        return 0.000755478233363 * rpm;
    }

    public double convertCentiMeterSecond(double rpm) {
        //double diameter = 0.00101;// 101 millimeters
        // weird number = (pi * diameter) / 60 (precalculated for speed)
        return convertToMetersPerSecond(rpm) / 100;
        // 7:1 (Motor to wheel)

    }

    // private double[] optimizeWheelPositions(double angle, double encoderValue) {
    // double[] ret = {1, angle};
    // double change = Math.abs(angle - encoderValue);

    // if ((change / 180) >= 1) {
    // ret[0] = -1;
    // ret[1] = angle % 180;
    // }

    // return ret;
    // }

    public void drive(double speed, double angle) {
        speed = convertToMetersPerSecond(speed * 3000); // Converting the speed to m/s with a max rpm of 3000 (GEar
                                                        // ratio is 7:1)

        SmartDashboard.putNumber(m_turnEncoderPort + " angle input", angle);
        SmartDashboard.putNumber(m_turnEncoderPort + " speed input", speed);

        SmartDashboard.putNumber(m_turnEncoderPort + " raw drive encoder value", driveEncoder.getVelocity());

        double currentDriveSpeed = convertToMetersPerSecond(driveEncoder.getVelocity());
        double turnValue = wrapAroundAngles(turnEncoder.getRotations());
        angle = wrapAroundAngles(angle);

        // Optimization Code stolen from
        // https://github.com/Frc2481/frc-2015/blob/master/src/Components/SwerveModule.cpp
        if (Math.abs(angle - turnValue) > 90 && Math.abs(angle - turnValue) < 270) {
            angle = ((int) angle + 180) % 360;
            speed = -speed;
        }

        SmartDashboard.putNumber(m_turnEncoderPort + " encoder angle", turnValue);

        SmartDashboard.putNumber(m_turnEncoderPort + " drive encoder ", currentDriveSpeed);

        double turnPIDOutput = turnPidController.calculate(turnValue, angle);
        double drivePIDOutput = drivePidController.calculate(currentDriveSpeed, speed);

        // SmartDashboard.putNumber(m_turnEncoderPort + " pid value", drivePIDOutput);

        //double driveFeedForwardOutput = driveFeedforward.calculate(currentDriveSpeed, speed);

        // SmartDashboard.putNumber(m_turnEncoderPort + " feedforward value",
        // driveFeedForwardOutput);

        SmartDashboard.putNumber(
            m_turnEncoderPort + " drive set", 
            MathUtil.clamp(drivePIDOutput /*+ driveFeedForwardOutput*/, -.7, .7));
        // SmartDashboard.putNumber(m_turnEncoderPort + " turn set", turnPIDOutput);

        // 70% speed is about 5.6 feet/second
        driveMotor.set(MathUtil.clamp(drivePIDOutput /* + driveFeedForwardOutput */, -.7, .7));
        if (!turnPidController.atSetpoint()) {
            turnMotor.set(ControlMode.PercentOutput, MathUtil.clamp(turnPIDOutput, -.7, .7));
        }
    }
}