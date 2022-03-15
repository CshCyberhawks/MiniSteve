package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TransportSystem extends SubsystemBase {
    
    private VictorSPX transportMotor;
    private double traversalMult = 2;
    private boolean isRunningSequence = false;
    private int cargoStored = 0;

    public TransportSystem() {
        transportMotor = new VictorSPX(Constants.traversalMotor);
    }

    public boolean getSequenceState() {
        return isRunningSequence;
    }

    public int getCargoAmount() {
        return cargoStored;
    }

    public void setSequenceState(boolean state) {
        isRunningSequence = state;
    }

    public void setCargoAmount(int amount) {
        cargoStored = amount;
    }

    public void move(double speed) {
        transportMotor.set(ControlMode.PercentOutput, speed * traversalMult);
    }
}