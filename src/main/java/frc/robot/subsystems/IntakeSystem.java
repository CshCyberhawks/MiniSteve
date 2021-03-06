package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSystem extends SubsystemBase {
    private VictorSPX intakeMotor;
    private Solenoid intakeSolenoid;

    // private final double powerMult = 1;

    public IntakeSystem() {
        intakeMotor = new VictorSPX(Constants.intakeMotor);
        intakeMotor.setInverted(true);
        intakeSolenoid = new Solenoid(Constants.pcm, PneumaticsModuleType.CTREPCM, 0);
    }

    public void intake(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, -speed);
        if (speed != 0)
            intakeSolenoid.set(true);
        else
            intakeSolenoid.set(false);
        // bottomFeedMotor.set(speed);
        // topFeedMotor.set(speed);
        SmartDashboard.putNumber("Intake Motor Speed ", speed);
    }

    public void kill() {
        intakeMotor.set(ControlMode.PercentOutput, 0);
        intakeSolenoid.set(false);
    }
}