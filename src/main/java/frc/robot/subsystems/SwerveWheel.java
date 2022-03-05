package frc.robot.subsystems;

import frc.robot.util.MathClass;
import frc.robot.util.TurnEncoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

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

    private double oldAngle = 0;

    private int m_turnEncoderPort;

    // below is in m/s
    private double maxAcceleration = .1;
    private double lastSpeed = 0;

    public double turnValue;
    public double currentDriveSpeed;
    public double rawTurnValue;

    private PIDController turnPidController;
    private PIDController drivePidController;

    // private SimpleMotorFeedforward driveFeedforward;

    public SwerveWheel(int turnPort, int drivePort, int turnEncoderPort) {

        turnMotor = new TalonSRX(turnPort);
        driveMotor = new CANSparkMax(drivePort, CANSparkMaxLowLevel.MotorType.kBrushless);

        driveEncoder = driveMotor.getEncoder();
        turnEncoder = new TurnEncoder(turnEncoderPort);

        m_turnEncoderPort = turnEncoderPort;

        turnPidController = new PIDController(0.01, 0, 0);
        turnPidController.setTolerance(4);
        turnPidController.enableContinuousInput(0, 360);

        drivePidController = new PIDController(0, 0, 0);
        // driveFeedforward = new SimpleMotorFeedforward(.1, 473);
    }

    private double wrapAroundAngles(double input) {
        return input < 0 ? 360 + input : input;
    }

    public double convertToMetersPerSecond(double rpm) {
        double radius = 0.0505;
        // Gear ratio is 7:1
        return ((2 * Math.PI * radius) / 60) * (rpm / 7);
    }

    public double[] calculateDrive(double speed, double angle) {

        oldAngle = angle;

        SmartDashboard.putNumber("speedPreM/S", speed);
        speed = convertToMetersPerSecond(speed * 5000); // Converting the speed to m/s with a max rpm of 5000 (GEar
        // ratio is 7:1)

        currentDriveSpeed = convertToMetersPerSecond(driveEncoder.getVelocity());
        turnValue = wrapAroundAngles(turnEncoder.get());
        rawTurnValue = turnEncoder.get();
        angle = wrapAroundAngles(angle);

        lastSpeed = currentDriveSpeed;

        // Optimization Code stolen from
        // https://github.com/Frc2481/frc-2015/blob/master/src/Components/SwerveModule.cpp

        if (Math.abs(angle - turnValue) > 90 && Math.abs(angle - turnValue) < 270) {
            angle = ((int) angle + 180) % 360;
            speed = -speed;
        }

        /*
         * if (Math.abs(speed) - Math.abs(lastSpeed) > maxAcceleration && speed != 0) {
         * speed = speed < 0 ? -(Math.abs(lastSpeed) + maxAcceleration) : lastSpeed +
         * maxAcceleration;
         * }
         * 
         * else if (Math.abs(lastSpeed) - Math.abs(speed) > maxAcceleration && speed !=
         * 0) {
         * speed = speed < 0 ? -(Math.abs(lastSpeed) - maxAcceleration) : lastSpeed -
         * maxAcceleration;
         * }
         */

        double turnPIDOutput = turnPidController.calculate(turnValue, angle);
        double drivePIDOutput = drivePidController.calculate(currentDriveSpeed, speed);
        // double driveFeedForwardOutput = driveFeedforward.calculate(speed);

        // 70% speed is about 5.6 feet/second

        double driveMotorSet = speed; // drivePIDOutput + speed;
        double turnMotorSet = turnPIDOutput;

        SmartDashboard.putNumber("driveMotorSet " + m_turnEncoderPort, driveMotorSet);
        SmartDashboard.putNumber("turnMotorSet " + m_turnEncoderPort, turnMotorSet);

        // SmartDashboard.putNumber("driveFeedForwardOutput", driveFeedForwardOutput);

        return new double[] { driveMotorSet, turnMotorSet };

    }

    public void drive(double driveSet, double turnSet) {
        SmartDashboard.putNumber("finalTurnSet", turnSet);
        SmartDashboard.putNumber("finalDriveSet", driveSet);
        driveMotor.set(driveSet);
        if (!turnPidController.atSetpoint()) {
            turnMotor.set(ControlMode.PercentOutput, turnSet);
        }
    }

    public void preserveAngle() {
        double[] preserveOutputs = calculateDrive(0, oldAngle);
        SmartDashboard.putNumber("preserveOutputsAngle", preserveOutputs[1]);
        drive(0, preserveOutputs[1]);
    }

    public void kill() {
        driveMotor.set(0);
        turnMotor.set(ControlMode.PercentOutput, 0);
    }
}
