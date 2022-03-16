package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TransportSystem extends SubsystemBase {
    
    private VictorSPX transportMotor;
    private double traversalMult;
    private boolean isRunningSequence;
    private int cargoAmount;

    public TransportSystem() {
        transportMotor = new VictorSPX(Constants.traversalMotor);
        traversalMult = 2;
        isRunningSequence = false;
        cargoAmount = 0;
    }

    public boolean getSequenceState() {
        return isRunningSequence;
    }

    public int getCargoAmount() {
        return cargoAmount;
    }

    public void setSequenceState(boolean state) {
        isRunningSequence = state;
    }

    public void setCargoAmount(int amount) {
        cargoAmount = amount;
    }

    public void move(double speed) {
        transportMotor.set(ControlMode.PercentOutput, speed * traversalMult);
    }
}