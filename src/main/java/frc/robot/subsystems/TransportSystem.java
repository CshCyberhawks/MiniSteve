package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TransportSystem extends SubsystemBase {
    private VictorSPX transportMotor;
    private final double traversalMult = 2;
    private int cargoAmount;
    private boolean isRunningSequence;

    public TransportSystem() {
        transportMotor = new VictorSPX(Constants.traversalMotor);
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