package frc.robot.subsystems;

// import javax.print.CancelablePrintJob;

import com.revrobotics.CANSparkMax;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootSystem extends SubsystemBase {
    private CANSparkMax topMotor;
    private CANSparkMax bottomRightMotor;
    private CANSparkMax bottomLeftMotor;
    private double topMotorMult = -.01;
    private Encoder topEncoder;
    private Encoder bottomEncoder;
    private PIDController motorController;
    private double maxRPM = 5000;

    public ShootSystem() {
        topMotor = new CANSparkMax(Constants.topShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(Constants.leftShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(Constants.rightShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        // traversalEncoder = traversalMotor.getEncoder();
        topEncoder = new Encoder(3, 4);
        bottomEncoder = new Encoder(5, 6);
        motorController = new PIDController(1, 0, 0);

        // Set distance to pulse to distance in rotation (makes get rate return
        // rotations)
        topEncoder.setDistancePerPulse(1 / 8192);
        bottomEncoder.setDistancePerPulse(1 / 8192);
    }

    // Syncing of bottom 2 motors
    private void setBottom(double power) {
        double leftPIDOut = motorController.calculate(bottomEncoder.getRate() / 8192, power);
        double rightPIDOut = motorController.calculate(bottomEncoder.getRate() / 8192, -power);

        SmartDashboard.putNumber("Left PID", leftPIDOut);
        SmartDashboard.putNumber("Right PID", rightPIDOut);

        bottomLeftMotor.set(leftPIDOut / Math.abs(maxRPM));// power
        bottomRightMotor.set(rightPIDOut / Math.abs(maxRPM));
    }

    public void shoot(double power) {
        // double traversalPIDOUt =
        // motorController.calculate(traverseEncoder.getVelocity(), power *
        // traversalMult);
        SmartDashboard.putNumber("Top Encoder", topEncoder.getRate() / 8192);
        SmartDashboard.putNumber("Bottom Encoder", bottomEncoder.getRate() / 8192);

        power *= maxRPM; // Convert to RPM

        SmartDashboard.putNumber("Shoot Power", power);

        double topPIDOut = motorController.calculate(topEncoder.getRate() / 8192, -power * topMotorMult);

        SmartDashboard.putNumber("Top PID", topPIDOut);

        topMotor.set(topPIDOut / (Math.abs(maxRPM) * Math.abs(topMotorMult)));
        setBottom(power);
    }
}