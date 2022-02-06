package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.PIDController;

public class IntakeSystem extends SubsystemBase {
    
    private CANSparkMax intakeMotor;
    private CANSparkMax bottomFeedMotor;
    private CANSparkMax topFeedMotor;
    private PIDController intakeController;
    private PIDController bottomFeedController;
    private PIDController topFeedController;
    
    private static final double powerMult = 0.5;
    public IntakeSystem() {
        intakeMotor = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless); 
        intakeController = new PIDController(0, 0, 0);
        bottomFeedController = new PIDController(0, 0, 0);
        topFeedController = new PIDController(0, 0, 0);
    }
    public double measurement(double speed)
    {
        double r = 0; //get Radius
        double gearRatio = 0; //get gearRatio
        return (speed * gearRatio) * ((Math.PI * 2*(r)) / 60) / 100;
        //Get measurement
        
    }
    
    public void intake(double speed) {
        double intakePIDOutput = intakeController.calculate(measurement(speed), speed);
        double bottomFeedPIDOutput = bottomFeedController.calculate(measurement(speed), speed);
        double topFeedPIDOutput = topFeedController.calculate(measurement(speed), speed);

        intakeMotor.set(intakePIDOutput);
        bottomFeedMotor.set(bottomFeedPIDOutput);
        topFeedMotor.set(topFeedPIDOutput);
        SmartDashboard.putNumber("Intake Motor Speed", speed);   
    }

    public void output() {
        intakeMotor.set(-powerMult);
        bottomFeedMotor.set(-powerMult);
        topFeedMotor.set(-powerMult);
    } 
}
