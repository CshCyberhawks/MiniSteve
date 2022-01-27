package frc.robot.subsystems;

import javax.print.CancelablePrintJob;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootSystem extends SubsystemBase {
    private CANSparkMax topMotor;
    private CANSparkMax bottomRightMotor;
    private CANSparkMax bottomLeftMotor;
    private Encoder topEncoder;
    private Encoder bottomEncoder;
    private PIDController shootPID;
    private SimpleMotorFeedforward shootFeedFoward;

    public ShootSystem() {
        topMotor = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);

        topEncoder = new Encoder(0, 0);
        bottomEncoder = new Encoder(0, 0);

        shootPID = new PIDController(0, 0, 0);
        shootFeedFoward = new SimpleMotorFeedforward(0, 473);

        SmartDashboard.putNumber("Top Shoot Encoder", topEncoder.getRate());
        SmartDashboard.putNumber("Bottom Shoot Encoder", bottomEncoder.getRate());
    }

    public void shoot(double power) {
        double topShootPIDOutput = shootPID.calculate(topEncoder.getRate(), power);
        double bottomShootPIDOutput = shootPID.calculate(bottomEncoder.getRate(), power);
        double topShootFeedFowardOutput = shootFeedFoward.calculate(topEncoder.getRate(), power);
        double bottomShootFeedFowardOutput = shootFeedFoward.calculate(topEncoder.getRate(), power);

        topMotor.set(topShootPIDOutput + topShootFeedFowardOutput);
        setBottom(bottomShootPIDOutput + bottomShootFeedFowardOutput);

    }

    //Syncing of bottom 2 motors
    private void setBottom(double power) {
        bottomLeftMotor.set(power);
        bottomRightMotor.set(power);
    }
}