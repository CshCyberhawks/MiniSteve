package frc.robot.subsystems;

import javax.swing.text.StyleContext.SmallAttributeSet;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.MathClass;

public class TransportSystem extends SubsystemBase {
    private VictorSPX transportMotor;
    private final double traversalMult = 2;
    public int cargoAmount = 1;
    private boolean isRunningSequence;
    private double lastCargoPickupTime = 0;
    private double lastCargoShootTime = 0;

    private boolean cargoPickedUp = false;
    private boolean cargoShot = false;

    private NetworkTableEntry cargoAmountShuffle;

    public void cargoMonitor() {
        double shootDifference = MathClass.getCurrentTime() - lastCargoShootTime;
        double pickupDifference = MathClass.getCurrentTime() - lastCargoPickupTime;

        SmartDashboard.putNumber("pickupDiff", pickupDifference);

        cargoPickedUp = !Robot.getFrontBreakBeam().get() && pickupDifference > 1;
        cargoShot = !Robot.getShootBreakBeam().get() && cargoAmount > 0 && shootDifference > 1;

        if (cargoPickedUp) {
            lastCargoPickupTime = MathClass.getCurrentTime();
            cargoAmount++;
        }
        if (cargoShot && cargoAmount > 0) {
            lastCargoShootTime = MathClass.getCurrentTime();
            cargoAmount--;
        }

        cargoAmountShuffle.setNumber(cargoAmount);
    }

    public TransportSystem() {
        cargoAmountShuffle = Robot.driveShuffleboardTab.add("cargoAmount", cargoAmount).getEntry();

        transportMotor = new VictorSPX(Constants.traversalMotor);
        transportMotor.setNeutralMode(NeutralMode.Brake);
        isRunningSequence = false;
        cargoAmount = 1;
        // lastCargoPickupTime = MathClass.getCurrentTime();
        // lastCargoShootTime = MathClass.getCurrentTime();
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
        SmartDashboard.putNumber("transportSpeed", speed * traversalMult);
        transportMotor.set(ControlMode.PercentOutput, speed * traversalMult);
    }
}