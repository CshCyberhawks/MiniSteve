package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSystem extends SubsystemBase {
    private CANSparkMax climbMotor;
    private RelativeEncoder encoder;
    private Solenoid leftSolenoid;
    private Solenoid rightSolenoid;
    private PIDController climbPidController;

    public ClimbSystem() {
        climbMotor = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
        encoder = climbMotor.getEncoder();
        leftSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
        rightSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
        climbPidController = new PIDController(0, 0, 0);
    }

    public void climb(double speed) {
        speed *= 2000;
        climbMotor.set(climbPidController.calculate(encoder.getVelocity(), speed));
    }

    public void controlPneumatics(boolean control) {
        leftSolenoid.set(control);
        rightSolenoid.set(control);
    }
}