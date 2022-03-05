package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootSystem extends SubsystemBase {
    private CANSparkMax topMotor;
    private CANSparkMax bottomRightMotor;
    private CANSparkMax bottomLeftMotor;
    private VictorSPX traversalMotor;
    private double topMotorMult = 1.2;
    private double traversalMult = 2;
    // private RelativeEncoder topEncoder;
    // private RelativeEncoder traversalEncoder;
    // private RelativeEncoder rightEncoder;
    // private RelativeEncoder leftEncoder;
    // private PIDController motorController;

    public ShootSystem() {
        topMotor = new CANSparkMax(Constants.topShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(Constants.leftShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(Constants.rightShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        traversalMotor = new VictorSPX(Constants.traversalMotor);
        // traversalEncoder = traversalMotor.getEncoder();
        // topEncoder = topMotor.getEncoder();
        // rightEncoder = bottomRightMotor.getEncoder();
        // leftEncoder = bottomLeftMotor.getEncoder();
        //motorController = new PIDController(0.01, 0, 0);
    }
    public void shoot(double power) {
        //double traversalPIDOUt = motorController.calculate(traverseEncoder.getVelocity(), power * traversalMult);
        topMotor.set(-power * topMotorMult);
        setBottom(power);
    }
    public void traverse(double power) {
        traversalMotor.set(ControlMode.PercentOutput,power * traversalMult);
    }
    //Syncing of bottom 2 motors
    private void setBottom(double power) {
        // double leftPIDOut = motorController.calculate(leftEncoder.getVelocity(), power);
        // double rightPIDOut = motorController.calculate(rightEncoder.getVelocity(), power);
        bottomLeftMotor.set(-power);//power
        bottomRightMotor.set(power);
    }
}