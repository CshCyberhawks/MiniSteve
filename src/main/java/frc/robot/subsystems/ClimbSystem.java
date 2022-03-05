package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSystem extends SubsystemBase{
    private CANSparkMax leftclimbMotor;
    private CANSparkMax rightclimbMotor;
    
    public ClimbSystem() {
        leftclimbMotor = new CANSparkMax(0,CANSparkMaxLowLevel.MotorType.kBrushless);
        rightclimbMotor = new CANSparkMax(0,CANSparkMaxLowLevel.MotorType.kBrushless);

    }
    public void climb(double speed) {
        leftclimbMotor.set(speed);
        rightclimbMotor.set(speed);
    }
}
