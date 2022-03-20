package frc.robot.subsystems;

import frc.robot.util.TurnEncoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.math.controller.SimpleMotorFeedforward;
//leaving the below imports to remember that profiledPIDControllers exist, and that feedforwards exist in case we need to use them
// import edu.wpi.first.math.controller.ProfiledPIDController;
// import edu.wpi.first.math.trajectory.TrapezoidProfile;
// import edu.wpi.first.math.controller.SimpleMotorFeedforward;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.kinematics.SwerveModuleState;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;
import frc.robot.util.DriveEncoder;
import frc.robot.util.DriveState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveWheel {

    private TalonSRX turnMotor;
    private TalonFX driveMotor;
    private TurnEncoder turnEncoder;
    private DriveEncoder driveEncoder;

    private double oldAngle = 0;

    private int m_turnEncoderPort;

    // below is in m / 20 ms
    private double maxAcceleration = .01;
    private double lastSpeed = 0;

    public double turnValue;
    public double currentDriveSpeed;
    public double rawTurnValue;

    private PIDController turnPidController;
    private PIDController drivePidController;
    private PIDController speedPID;

    public SwerveWheel(int turnPort, int drivePort, int turnEncoderPort) {
        turnMotor = new TalonSRX(turnPort);
        driveMotor = new WPI_TalonFX(drivePort);

        driveEncoder = new DriveEncoder(driveMotor);
        turnEncoder = new TurnEncoder(turnEncoderPort);

        driveMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        driveMotor.config_kF(0, 0);
        driveMotor.config_kP(0, 0.01);
        driveMotor.config_kI(0, 0);
        driveMotor.config_kD(0, 0);

        driveMotor.setNeutralMode(NeutralMode.Brake);

        m_turnEncoderPort = turnEncoderPort;

        turnPidController = new PIDController(.01, 0, 0);
        turnPidController.setTolerance(4);
        turnPidController.enableContinuousInput(0, 360);

        speedPID = new PIDController(0.03, 0, 0);

        drivePidController = new PIDController(0.01, 0, 0);

        if (turnEncoderPort == 1 || turnEncoderPort == 2) {
            driveMotor.setInverted(true);
        } else {
            driveMotor.setInverted(false);
        }
    }

    private double wrapAroundAngles(double input) {
        return input < 0 ? 360 + input : input;
    }

    public double convertToMetersPerSecond(double rpm) {
        double radius = 0.0505;
        // Gear ratio is 7:1
        return ((2 * Math.PI * radius) / 60) * (rpm / 7);
    }

    public double convertToMetersPerSecondFromSecond(double rps) {
        double radius = 0.0505;

        return (2 * Math.PI * radius) * (rps / 7);
    }

    public double convertToWheelRotations(double meters) {
        double wheelConstant = (2 * Math.PI * Constants.wheelRadius) / 60;
        return 7 * meters / wheelConstant;
    }

    public void drive(double speed, double angle, DriveState mode) {
        oldAngle = angle;

        switch (mode) {
            case TELE:
                maxAcceleration = 0.05;
                break;
            case AUTO:
                maxAcceleration = 0.01;
                break;
        }

        double driveVelocity = driveEncoder.getVelocity();
        currentDriveSpeed = convertToMetersPerSecondFromSecond(driveVelocity);
        // SmartDashboard.putNumber(m_turnEncoderPort + " wheel rotations",
        // driveVelocity);
        turnValue = wrapAroundAngles(turnEncoder.get());
        rawTurnValue = turnEncoder.get();
        angle = wrapAroundAngles(angle);

        // Optimization Code stolen from
        // https://github.com/Frc2481/frc-2015/blob/master/src/Components/SwerveModule.cpp
        if (Math.abs(angle - turnValue) > 90 && Math.abs(angle - turnValue) < 270) {
            angle = ((int) angle + 180) % 360;
            speed = -speed;
        }

        // if (mode == "tele") {
        if (Math.abs(speed - lastSpeed) > maxAcceleration) {
            if (speed > lastSpeed) {
                speed = lastSpeed + maxAcceleration;
            } else {
                speed = lastSpeed - maxAcceleration;
            }
        }
        // }

        lastSpeed = speed;

        speed = convertToMetersPerSecond(speed * 5000); // Converting the speed to m/s with a max rpm of 5000 (GEar
        // ratio is 7:1)

        double turnPIDOutput = turnPidController.calculate(turnValue, angle);

        // maybe reason why gradual deecleration isn't working is because the PID
        // controller is trying to slow down by going opposite direction in stead of
        // just letting wheels turn? maybe we need to skip the PID for slowing down?
        // maybe needs more tuning?
        double drivePIDOutput = drivePidController.calculate(currentDriveSpeed, speed);

        // SmartDashboard.putNumber(m_turnEncoderPort + " pid value", drivePIDOutput);

        // double driveFeedForwardOutput = driveFeedforward.calculate(currentDriveSpeed,
        // speed);

        // SmartDashboard.putNumber(m_turnEncoderPort + " currentDriveSpeed",
        // currentDriveSpeed);
        // SmartDashboard.putNumber(m_turnEncoderPort + " turn set", turnPIDOutput);

        // SmartDashboard.putNumber(m_turnEncoderPort + " driveSet", (speed / 3.777) +
        // drivePIDOutput);
        // SmartDashboard.putNumber(m_turnEncoderPort + " turnSet", turnPIDOutput);
        // 70% speed is about 5.6 feet/second
        driveMotor.set(ControlMode.PercentOutput, MathUtil.clamp((speed / 3.777)/* + drivePIDOutput */, -1, 1));
        if (!turnPidController.atSetpoint()) {
            turnMotor.set(ControlMode.PercentOutput, MathUtil.clamp(turnPIDOutput, -1, 1));
        }
    }

    public void preserveAngle() {
        drive(0, oldAngle, DriveState.OTHER);
    }

    public void kill() {
        driveMotor.set(ControlMode.PercentOutput, 0);
        turnMotor.set(ControlMode.PercentOutput, 0);
    }
}
