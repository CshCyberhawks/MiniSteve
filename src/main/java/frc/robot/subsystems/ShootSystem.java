package frc.robot.subsystems;

// import javax.print.CancelablePrintJob;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootSystem extends SubsystemBase {
    private CANSparkMax topMotor;
    private CANSparkMax bottomRightMotor;
    private CANSparkMax bottomLeftMotor;
    private CANSparkMax traversalMotor;
    private double topMotorMult = 1.2;
    private double traversalMult = 1;
    private RelativeEncoder topEncoder;
    // private RelativeEncoder traversalEncoder;
    private RelativeEncoder rightEncoder;
    private RelativeEncoder leftEncoder;
    private PIDController motorController;

    public ShootSystem() {
        topMotor = new CANSparkMax(Constants.topShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(Constants.leftShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(Constants.rightShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        traversalMotor = new CANSparkMax(Constants.traversalMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        // traversalEncoder = traversalMotor.getEncoder();
        topEncoder = topMotor.getEncoder();
        rightEncoder = bottomRightMotor.getEncoder();
        leftEncoder = bottomLeftMotor.getEncoder();
        motorController = new PIDController(0.01, 0, 0);
    }
    public void shoot(double power) {
        double topPIDOut = motorController.calculate(topEncoder.getVelocity(), power * topMotorMult);
        //double traversalPIDOUt = motorController.calculate(traverseEncoder.getVelocity(), power * traversalMult);
        topMotor.set(topPIDOut);
        traversalMotor.set(power * traversalMult);
        setBottom(power);
    }

    //Syncing of bottom 2 motors
    private void setBottom(double power) {
        double leftPIDOut = motorController.calculate(leftEncoder.getVelocity(), power);
        double rightPIDOut = motorController.calculate(rightEncoder.getVelocity(), power);
        bottomLeftMotor.set(leftPIDOut);//power
        bottomRightMotor.set(-rightPIDOut);
    }
}