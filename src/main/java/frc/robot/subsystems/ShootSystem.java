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
    public Encoder topEncoder;
    public Encoder bottomEncoder;
    private PIDController topPIDController;
    private PIDController bottomRightPIDController;
    private PIDController bottomLeftPIDController;
    private double maxRPM = 5000;
    public boolean autoShootRunning = false;

    public ShootSystem() {
        topMotor = new CANSparkMax(Constants.topShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomLeftMotor = new CANSparkMax(Constants.leftShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottomRightMotor = new CANSparkMax(Constants.rightShootMotor, CANSparkMaxLowLevel.MotorType.kBrushless);

        // traversalEncoder = traversalMotor.getEncoder();
        topEncoder = new Encoder(3, 4);
        bottomEncoder = new Encoder(5, 6);

        // Set distance to pulse to distance in rotation (makes get rate return
        // rotations)
        topEncoder.setDistancePerPulse(1);
        bottomEncoder.setDistancePerPulse(1);
        topPIDController = new PIDController(1, 0, 0);
        bottomRightPIDController = new PIDController(1, 0, 0);
        bottomLeftPIDController = new PIDController(1, 0, 0);

    }

    // Syncing of bottom 2 motors
    private void setBottom(double power) {
        double bottomRightPIDOutput = bottomRightPIDController.calculate(bottomEncoder.getRate() / 8192, power);
        double bottomLeftPIDOutput = bottomLeftPIDController.calculate(bottomEncoder.getRate() / 8192, power);

        double rightSet = -((bottomRightPIDOutput / maxRPM) + (power / maxRPM));
        double leftSet = ((bottomLeftPIDOutput / maxRPM) + (power / maxRPM));

        SmartDashboard.putNumber("bottomPower", power);

        SmartDashboard.putNumber("rightSet", rightSet);
        SmartDashboard.putNumber("leftSet", leftSet);

        bottomRightMotor.set(rightSet);
        bottomLeftMotor.set(leftSet);
    }

    public void shoot(double power) {
        // double traversalPIDOUt =
        // motorController.calculate(traverseEncoder.getVelocity(), power *
        // traversalMult);
        SmartDashboard.putNumber("Top Encoder", topEncoder.getRate() / 8192);
        SmartDashboard.putNumber("Bottom Encoder", bottomEncoder.getRate() / 8192);

        power *= maxRPM; // Convert to RPM

        SmartDashboard.putNumber("shootPower", power);

        double topPIDOut = topPIDController.calculate(bottomEncoder.getRate() / 8192, power);

        topMotor.set(-(topPIDOut / maxRPM) + -(power / maxRPM));
        setBottom(power);
    }
}
