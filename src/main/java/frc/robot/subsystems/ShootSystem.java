package frc.robot.subsystems;

import javax.print.CancelablePrintJob;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
<<<<<<< HEAD
import frc.robot.Constants;
=======
import com.revrobotics.RelativeEncoder;

>>>>>>> c2765311d0d3eb5e029c2f192cb1de28be389657
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
<<<<<<< HEAD
    private Encoder topEncoder;
    private Encoder bottomEncoder;
    private PIDController shootPID;
    private SimpleMotorFeedforward shootFeedFoward;
    
    public ShootSystem() {
        
        topMotor = new CANSparkMax(Constants.spinShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(Constants.leftShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(Constants.rightShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);

        topEncoder = new Encoder(0, 0);
        bottomEncoder = new Encoder(0, 0);

        shootPID = new PIDController(0, 0, 0);
        shootFeedFoward = new SimpleMotorFeedforward(0, 473);

        SmartDashboard.putNumber("Top Shoot Encoder", topEncoder.getRate());
        SmartDashboard.putNumber("Bottom Shoot Encoder", bottomEncoder.getRate());
=======
    private double topMotorMult = 1.2;

    public ShootSystem() {
        topMotor = new CANSparkMax(Constants.topShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(Constants.leftShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(Constants.rightShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
>>>>>>> c2765311d0d3eb5e029c2f192cb1de28be389657
    }

    public void shoot(double power) {
        topMotor.set(-power * topMotorMult);
        setBottom(power);
    }

    //Syncing of bottom 2 motors
    private void setBottom(double power) {
        bottomLeftMotor.set(power);
        bottomRightMotor.set(-power);
    }
}