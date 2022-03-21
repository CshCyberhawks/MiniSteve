package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.MathClass;

public class TransportSystem extends SubsystemBase {
    private VictorSPX transportMotor;
    private final double traversalMult = 2;
    private int cargoAmount = 1;
    private boolean isRunningSequence;
    private double lastCargoPickupTime = 0;
    private double lastCargoShootTime = 0;

    private boolean cargoPickedUp = false;
    private boolean cargoShot = false;

    @Override
    public void periodic() {

        double shootDifference = MathClass.getCurrentTime() - lastCargoShootTime;
        double pickupDifference = MathClass.getCurrentTime() - lastCargoPickupTime;

        cargoPickedUp = !Robot.getFrontBreakBeam().get() && cargoAmount < 2 && pickupDifference > 60;
        cargoShot = !Robot.getShootBreakBeam().get() && cargoAmount > 0 && shootDifference > 60;

        if (cargoPickedUp) {
            lastCargoPickupTime = MathClass.getCurrentTime();
            cargoAmount++;
        }
        if (cargoShot) {
            lastCargoShootTime = MathClass.getCurrentTime();
            cargoAmount--;
        }
    }

    public TransportSystem() {
        transportMotor = new VictorSPX(Constants.traversalMotor);
        isRunningSequence = false;
        cargoAmount = 1;
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