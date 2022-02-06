package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSystem extends SubsystemBase{
    private CANSparkMax climbMotor;
    
    public ClimbSystem()
    {
        climbMotor = new CANSparkMax(0,CANSparkMaxLowLevel.MotorType.kBrushless);
    }
    public void climb(double speed)
    {
        climbMotor.set(speed);
    }
}
