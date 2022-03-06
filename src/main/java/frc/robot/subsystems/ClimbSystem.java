package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSystem extends SubsystemBase{
    private CANSparkMax climbMotor;
    private RelativeEncoder encoder;
    Solenoid leftSolenoid;
    Solenoid rightSolenoid;
    
    public ClimbSystem() {
        climbMotor = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
        encoder = climbMotor.getEncoder();
        leftSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
        rightSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
    }

    public void climb(double speed) {
        climbMotor.set(speed);
    }

    public void setPneumatics(boolean control) {
        leftSolenoid.set(control);
        rightSolenoid.set(control);
    }
}