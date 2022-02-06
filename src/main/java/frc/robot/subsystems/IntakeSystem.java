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
        
    }
    public void intake(double speed) {
        intakeMotor.set(speed);
        bottomFeedMotor.set(speed);
        topFeedMotor.set(speed);
        SmartDashboard.putNumber("Intake Motor Speed", speed);   
    }

    public void output() {
        intakeMotor.set(-powerMult);
        bottomFeedMotor.set(-powerMult);
        topFeedMotor.set(-powerMult);
    } 
}
