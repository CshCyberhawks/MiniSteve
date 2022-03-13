package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TransportSystem extends SubsystemBase {
    
    private VictorSPX transportMotor;
    private double traversalMult = 2;
    public boolean isRunningSequence = false;
    public int cargoStored = 0;

    public TransportSystem() {
        transportMotor = new VictorSPX(Constants.traversalMotor);
    }

    public void move(double speed) {
        transportMotor.set(ControlMode.PercentOutput, speed * traversalMult);
    }
}
