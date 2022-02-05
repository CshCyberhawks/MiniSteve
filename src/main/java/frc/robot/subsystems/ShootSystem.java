package frc.robot.subsystems;

import javax.print.CancelablePrintJob;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootSystem extends SubsystemBase {
    private CANSparkMax topMotor;
    private CANSparkMax bottomRightMotor;
    private CANSparkMax bottomLeftMotor;
    private double topMotorMult = 1.2;
    private RelativeEncoder topEncoder;
    private RelativeEncoder rightEncoder;
    private RelativeEncoder leftEncoder;
    private PIDController topMotorController;
    private PIDController spinMotorController; 

    public ShootSystem() {
        topMotor = new CANSparkMax(Constants.topShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(Constants.leftShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(Constants.rightShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        topEncoder = topMotor.getEncoder();
        rightEncoder = bottomRightMotor.getEncoder();
        leftEncoder = bottomLeftMotor.getEncoder();
        topMotorController = new PIDController(0.01, 0, 0);
        spinMotorController = new PIDController(0, 0, 0);
    }
    public void shoot(double power) {
        double topPIDOut = topMotorController.calculate(topEncoder.getVelocity(), power);
        topMotor.set(topPIDOut * topMotorMult);
        setBottom(power);
    }

    //Syncing of bottom 2 motors
    private void setBottom(double power) {
        double spinPIDOut = spinMotorController.calculate(rightEncoder.getVelocity(),power);
        bottomLeftMotor.set(spinPIDOut);//power
        bottomRightMotor.set(-spinPIDOut);
    }
}