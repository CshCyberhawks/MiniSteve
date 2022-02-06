package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootSystem {
    private CANSparkMax topMotor;
    private CANSparkMax bottomRightMotor;
    private CANSparkMax bottomLeftMotor;
    private Encoder topEncoder;
    private Encoder bottomEncoder;
    private PIDController shootPID;
    private SimpleMotorFeedforward shootFeedFoward;

    public ShootSystem(int topMotorPort, int bottomLeftMotorPort, int bottomRightMotorPort) {
        topMotor = new CANSparkMax(topMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(bottomLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(bottomRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        topEncoder = new Encoder(0, 0);
        bottomEncoder = new Encoder(0, 0);

        shootPID = new PIDController(0, 0, 0);
        shootFeedFoward = new SimpleMotorFeedforward(0, 473);

        SmartDashboard.putNumber("Top Shoot Encoder", topEncoder.getRate());
        SmartDashboard.putNumber("Bottom Shoot Encoder", bottomEncoder.getRate());
    }

    public void shoot(double power) {
        double shootPIDOutput = shootPID.calculate(0, 0);
        double shootFeedFowardOutput = shootFeedFoward.calculate(power, 0);

        topMotor.set(shootPIDOutput + shootFeedFowardOutput);
        setBottom(shootPIDOutput + shootFeedFowardOutput);

        SmartDashboard.putNumber("Motor Power", power);
    }

    //Syncing of bottom 2 motors
    private void setBottom(double input) {
        bottomLeftMotor.set(input);
        bottomRightMotor.set(input);
    }
}
