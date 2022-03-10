package frc.robot.subsystems;

import frc.robot.util.DriveEncoder;
import frc.robot.util.TurnEncoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
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
import frc.robot.Constants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoSwerveWheel {
    private TalonSRX turnMotor;
    private TalonFX driveMotor;

    private TurnEncoder turnEncoder;
    private DriveEncoder driveEncoder;

    private double oldAngle = 0;

    private int turnEncoderPort;

    public double turnValue;
    public double currentDriveSpeed;
    public double rawTurnValue;

    private PIDController speedPID;
    private PIDController turnPID;

    public AutoSwerveWheel(int turnPort, int drivePort, int turnEncoderPort) {

        turnMotor = new TalonSRX(turnPort);
        driveMotor = new TalonFX(drivePort);

        turnEncoder = new TurnEncoder(turnEncoderPort);
        driveEncoder = new DriveEncoder(driveMotor);

        this.turnEncoderPort = turnEncoderPort;

        speedPID = new PIDController(0.03, 0, 0);
        turnPID = new PIDController(0.01, 0, 0);
        turnPID.setTolerance(4);
        turnPID.enableContinuousInput(0, 360);
    }

    private double wrapAroundAngles(double input) {
        return input < 0 ? 360 + input : input;
    }

    public double convertToWheelRotations(double meters) {
        double wheelConstant = (2 * Math.PI * Constants.wheelRadius) / 60;
        return 7 * meters / wheelConstant;
    }

    public double convertToMetersPerSecond(double rpm) {
        double wheelConstant = (2 * Math.PI * Constants.wheelRadius) / 60;
        return wheelConstant * (rpm / Constants.wheelGearRatio);
    }

    public void drive(double theta, double r) {
        double currentWheelRotations = driveEncoder.getPosition();
        double desiredWheelRotations = convertToWheelRotations(r);

        double currentTurnPosition = wrapAroundAngles(turnEncoder.get());
        double desiredTurnPosition = wrapAroundAngles(theta);

        if (Math.abs(desiredTurnPosition - currentTurnPosition) > 90
                && Math.abs(desiredTurnPosition - currentTurnPosition) < 270) {
            desiredTurnPosition = (desiredTurnPosition + 180) % 360;
            desiredWheelRotations *= -1;
        }

        double wheelSpeed = speedPID.calculate(currentWheelRotations, desiredWheelRotations);
        double wheelTurn = turnPID.calculate(currentTurnPosition, desiredTurnPosition);

        driveMotor.set(ControlMode.Velocity, wheelSpeed);
        if (!turnPID.atSetpoint()) {
            turnMotor.set(ControlMode.PercentOutput, wheelTurn);
        }
    }

    public void kill() {
        driveMotor.set(ControlMode.PercentOutput, 0);
        turnMotor.set(ControlMode.PercentOutput, 0);
    }
}
