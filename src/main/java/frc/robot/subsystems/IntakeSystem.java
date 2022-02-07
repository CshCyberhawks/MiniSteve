package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSystem extends SubsystemBase {
    
    private CANSparkMax intakeMotor;
    private CANSparkMax bottomFeedMotor;
    private CANSparkMax topFeedMotor;
    
    private static final double powerMult = 1;
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
