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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootSystem extends SubsystemBase {
    private CANSparkMax topMotor;
    private CANSparkMax bottomRightMotor;
    private CANSparkMax bottomLeftMotor;
    private double topMotorMult = 2;
    private RelativeEncoder topEncoder;
    private RelativeEncoder rightEncoder;
    private RelativeEncoder leftEncoder;
    private PIDController motorController;
    private double maxRPM = 5000;

    public ShootSystem() {
        topMotor = new CANSparkMax(Constants.topShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(Constants.leftShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(Constants.rightShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        // traversalEncoder = traversalMotor.getEncoder();
        topEncoder = topMotor.getEncoder();
        rightEncoder = bottomRightMotor.getEncoder();
        leftEncoder = bottomLeftMotor.getEncoder();
        motorController = new PIDController(1, 0, 0);
    }

    // Syncing of bottom 2 motors
    private void setBottom(double power) {
        double leftPIDOut = motorController.calculate(leftEncoder.getVelocity(), power);
        double rightPIDOut = motorController.calculate(rightEncoder.getVelocity(), power);
        bottomLeftMotor.set(-leftPIDOut / maxRPM);// power
        bottomRightMotor.set(rightPIDOut / maxRPM);
    }

    public void shoot(double power) {
        // double traversalPIDOUt =
        // motorController.calculate(traverseEncoder.getVelocity(), power *
        // traversalMult);
        SmartDashboard.putNumber("Top Encoder", topEncoder.getVelocity());
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getVelocity());
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getVelocity());

        power *= maxRPM; // Convert to RPM

        SmartDashboard.putNumber("key", power);

        double topPIDOut = motorController.calculate(topEncoder.getVelocity(), -power * topMotorMult);

        topMotorMult = SmartDashboard.getNumber("Top Motor Mult", topMotorMult);
        topMotor.set(/*-power * topMotorMult*/topPIDOut / (maxRPM * topMotorMult));
        setBottom(power);
        SmartDashboard.putNumber("Top Motor Mult", topMotorMult);
    }
}