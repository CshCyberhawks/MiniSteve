package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSystem extends SubsystemBase{
    private CANSparkMax leftClimbMotor;
    private CANSparkMax rightClimbMotor;
    
    public ClimbSystem() {
        leftClimbMotor = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightClimbMotor = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
    }
    public void climb(double speed) {
        leftClimbMotor.set(speed);
        rightClimbMotor.set(speed);
    }
}