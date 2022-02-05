package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.IO;

public class IntakeSystem extends SubsystemBase {
    
    TalonSRX motor = new TalonSRX(0);

    public IntakeSystem() {
    }

    @Override
    public void periodic() {
        if(IO.getXboxLeftTrigger() != 0)
            motor.set(ControlMode.PercentOutput, IO.getXboxRightTrigger());
        else if(IO.getXboxLeftBumper())
            motor.set(ControlMode.PercentOutput, -0.5);
    }
    
}
