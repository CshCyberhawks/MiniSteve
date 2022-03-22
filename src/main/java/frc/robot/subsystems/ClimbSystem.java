package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSystem extends SubsystemBase {
    private TalonFX climbMotor;
    private Solenoid leftSolenoid;
    private Solenoid rightSolenoid;
    private PIDController climbPidController;

    public ClimbSystem() {
        climbMotor = new TalonFX(Constants.climbMotor);
        leftSolenoid = new Solenoid(Constants.pcm, PneumaticsModuleType.CTREPCM, 2);
        // rightSolenoid = new Solenoid(Constants.pcm, PneumaticsModuleType.CTREPCM, 1);

        climbMotor.config_kF(0, 0);
        climbMotor.config_kP(0, .01);
        climbMotor.config_kI(0, 0);
        climbMotor.config_kD(0, 0);
    }

    public void climb(double speed) {
        climbMotor.set(ControlMode.PercentOutput, speed);
    }

    public void controlPneumatics() {
        leftSolenoid.toggle();
        // rightSolenoid.set(control);
    }
}