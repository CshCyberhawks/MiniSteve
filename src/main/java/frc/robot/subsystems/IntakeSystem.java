package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSystem extends SubsystemBase {

    private TalonSRX intakeMotor;
    private Solenoid intakeSolenoid;

    private final double powerMult = 1;

    public IntakeSystem() {
        intakeMotor = new TalonSRX(Constants.intakeMotor);
        intakeMotor.setInverted(true);

        intakeSolenoid = new Solenoid(Constants.intakeSolenoid, PneumaticsModuleType.CTREPCM, 0);
    }

    public void intake(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
        if (speed > 0) {
            intakeSolenoid.set(true);
        } else {
            intakeSolenoid.set(false);
        }
        // bottomFeedMotor.set(speed);
        // topFeedMotor.set(speed);
        SmartDashboard.putNumber("Intake Motor Speed", speed);
    }

    public void output() {
        intakeSolenoid.set(true);
        intakeMotor.set(ControlMode.PercentOutput, -powerMult);
        // bottomFeedMotor.set(-powerMult);
        // topFeedMotor.set(-powerMult);
    }

    public void kill() {
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }
}
