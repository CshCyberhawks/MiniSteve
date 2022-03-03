package frc.robot.subsystems;

import frc.robot.util.TurnEncoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXPIDSetConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.controller.SimpleMotorFeedforward;
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
    private TalonFX driveMotor;

    private TurnEncoder turnEncoder;
    private RelativeEncoder driveEncoder;

    private int m_turnEncoderPort;

    private PIDController turnPidController;
    private PIDController drivePidController;

    private double oldAngle = 0;

    private double maxSpeed = 5000; // rpm

    // private SimpleMotorFeedforward driveFeedforward;

    public SwerveWheel(int turnPort, int drivePort, int turnEncoderPort) {

        turnMotor = new TalonSRX(turnPort);
        // driveMotor = new CANSparkMax(drivePort,
        // CANSparkMaxLowLevel.MotorType.kBrushless);
        driveMotor = new TalonFX(drivePort);

        // driveEncoder = driveMotor.getEncoder();
        turnEncoder = new TurnEncoder(turnEncoderPort);

        m_turnEncoderPort = turnEncoderPort;

        turnPidController = new PIDController(.01, 0, 0);
        turnPidController.setTolerance(4);
        turnPidController.enableContinuousInput(0, 360);

        drivePidController = new PIDController(.3, 0, 0);
        // driveFeedforward = new SimpleMotorFeedforward(.1, 473);
    }

    private double wrapAroundAngles(double input) {
        while (input < 0) {
            input += 360;
        }
        return input;
    }

    public double convertToMetersPerSecond(double rpm) {
        double radius = 0.0505;
        // Gear ratio is 7:1
        return ((2 * Math.PI * radius) / 60) * (rpm / 7);
    }

    public void drive(double speed, double angle) {
        oldAngle = angle;
        speed = convertToMetersPerSecond(speed * maxSpeed); // Converting the speed to m/s with a max rpm of 5000 (Gear
        // ratio is 7:1)

        SmartDashboard.putNumber(m_turnEncoderPort + " angle input", angle);
        SmartDashboard.putNumber(m_turnEncoderPort + " speed input", speed);

        SmartDashboard.putNumber(m_turnEncoderPort + " raw drive encoder value", driveEncoder.getVelocity());

        double currentDriveSpeed = convertToMetersPerSecond((driveMotor.getSelectedSensorVelocity() / 2048) * maxSpeed);
        double turnValue = wrapAroundAngles(turnEncoder.get());
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

        // double driveFeedForwardOutput = driveFeedforward.calculate(currentDriveSpeed,
        // speed);

        // SmartDashboard.putNumber(m_turnEncoderPort + " feedforward value",
        // driveFeedForwardOutput);

        SmartDashboard.putNumber(m_turnEncoderPort + " drive set", MathUtil.clamp(drivePIDOutput /*
                                                                                                  * +
                                                                                                  * driveFeedForwardOutput
                                                                                                  */, -.7, .7));
        // SmartDashboard.putNumber(m_turnEncoderPort + " turn set", turnPIDOutput);

        // 70% speed is about 5.6 feet/second
        double output = drivePIDOutput;

        // if (drivePIDOutput > 0) {
        // output += driveFeedForwardOutput;
        // } else {
        // output -= driveFeedForwardOutput;
        // }

        // driveMotor.set(MathUtil.clamp(output, -.7, .7));
        driveMotor.set(ControlMode.PercentOutput, MathUtil.clamp(output, -.7, .7));
        if (!turnPidController.atSetpoint()) {
            turnMotor.set(ControlMode.PercentOutput, MathUtil.clamp(turnPIDOutput, -.7, .7));
        }
    }

    public void preserveAngle() {
        drive(0, oldAngle);
    }
}
