package frc.robot.subsystems;

// import javax.print.CancelablePrintJob;

import com.revrobotics.CANSparkMax;

// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class ShootSystem extends SubsystemBase {
    private CANSparkMax topMotor;
    private CANSparkMax bottomRightMotor;
    private CANSparkMax bottomLeftMotor;
    private Encoder topEncoder;
    private Encoder bottomEncoder;
    private RelativeEncoder oldEncoder;
    private PIDController topPIDController;
    private PIDController bottomPIDController;
    private final double topMotorMult = 2;
    private final int maxRPM = 5;
    private boolean autoShootRunning;
    public double bottomWheelSpeed;
    public double topWheelSpeed;

    public double shootMult = .9;

    private NetworkTableEntry bottomShootSpeed;
    private NetworkTableEntry shootMultTable;
    private NetworkTableEntry isAtSpeedTable;

    // bottom wheel encoder -3.7 for perfect shot
    // top wheel encoder 19 for perfect shot

    // top wheel encoder 22 max
    // bottom wheel encoder -24 max

    public ShootSystem() {
        topMotor = new CANSparkMax(Constants.topShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(Constants.leftShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(Constants.rightShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        oldEncoder = topMotor.getEncoder();

        // traversalEncoder = traversalMotor.getEncoder();
        topEncoder = new Encoder(2, 3);
        bottomEncoder = new Encoder(0, 1);

        // Set distance to pulse to distance in rotation (makes get rate return
        // rotations)
        // Why write readable code when I can write this 1/8192
        topEncoder.setDistancePerPulse(0.00012207031);
        bottomEncoder.setDistancePerPulse(0.00012207031);

        topPIDController = new PIDController(.01, 0, 0);
        bottomPIDController = new PIDController(.01, 0, 0);

        autoShootRunning = false;
        bottomShootSpeed = Robot.driveShuffleboardTab.add("Bottom Shoot Speed", topEncoder.getRate()).getEntry();

        shootMultTable = Robot.driveShuffleboardTab.add("Shoot Mult", shootMult).getEntry();
        isAtSpeedTable = Robot.driveShuffleboardTab.add("At Desired Speed", false).getEntry();
    }

    public Encoder getTopEncoder() {
        return topEncoder;
    }

    public Encoder getBottomEncoder() {
        return bottomEncoder;
    }

    public boolean getAutoShootState() {
        return autoShootRunning;
    }

    public void setAutoShootState(boolean state) {
        autoShootRunning = state;
    }

    // Syncing of bottom 2 motors
    private void setBottom(double power) {
        power = .23 * shootMult;

        double bottomPIDOutput = bottomPIDController.calculate(bottomEncoder.getRate(),
                Constants.bottomShootSetpoint * shootMult);

        // SmartDashboard.putNumber("rightBottomPID", bottomRightPIDOutput);

        // double rightSet = -((bottomRightPIDOutput / maxRPM) + (power / maxRPM));
        // double leftSet = ((bottomLeftPIDOutput / maxRPM) + (power / maxRPM));

        // SmartDashboard.putNumber("bottomPower", power);
        // SmartDashboard.putNumber("rightSet", rightSet);
        // SmartDashboard.putNumber("leftSet", leftSet);

        // SmartDashboard.putNumber("bottomMotorSets", MathUtil.clamp(power, -1, 1));

        bottomRightMotor.set(-(MathUtil.clamp(power + bottomPIDOutput, -1, 1)));
        bottomLeftMotor.set(MathUtil.clamp(power + bottomPIDOutput, -1, 1));
    }

    public void shoot(double power) {

        shootMultTable.setDouble(shootMult);

        SmartDashboard.putNumber("Top Encoder", topEncoder.getRate());
        SmartDashboard.putNumber("Bottom Encoder", bottomEncoder.getRate());
        bottomShootSpeed.setDouble(bottomEncoder.getRate());
        SmartDashboard.putNumber("shootPower", power);

        if (power == 0) {
            topMotor.set(0);
            bottomLeftMotor.set(0);
            bottomRightMotor.set(0);
            isAtSpeedTable.setBoolean(false);
            return;
        }
        power = .95 * shootMult;

        // double traversalPIDOUt =
        // motorController.calculate(traverseEncoder.getVelocity(), power *
        // traversalMult);

        // SmartDashboard.putNumber("Old Encoder", oldEncoder.getVelocity());
        bottomWheelSpeed = bottomEncoder.getRate();
        topWheelSpeed = topEncoder.getRate();
        isAtSpeedTable.setBoolean(topWheelSpeed >= 19);
        // power *= maxRPM; // Convert to RPM

        double topPIDOut = topPIDController.calculate(topWheelSpeed, Constants.topShootSetpoint * shootMult);

        topMotor.set(MathUtil.clamp(-(power + topPIDOut), -1, 1));

        setBottom(power);
    }
}