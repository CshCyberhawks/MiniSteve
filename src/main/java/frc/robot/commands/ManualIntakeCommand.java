package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeSystem;
import frc.robot.util.IO;

public class ManualIntakeCommand extends CommandBase {
    private IntakeSystem intakeSystem;
    // private double speedMult;

    public ManualIntakeCommand(IntakeSystem subsystem) {
        intakeSystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        boolean reverse = IO.removeBall();
        double speed = IO.getXboxLeftTrigger();

        SmartDashboard.putBoolean("intakeSequenceBool", Robot.getTransportSystem().getSequenceState());
        
        if (IO.autoIntake()) {
            IntakeSequence intakeCommandSequence = new IntakeSequence();
            intakeCommandSequence.schedule();
            SmartDashboard.putBoolean("intakeSequenceBegan", true);
        }
        else if (!Robot.getTransportSystem().getSequenceState())
            if (reverse)
                intakeSystem.intake(-.5);
            else
                intakeSystem.intake(speed);
    }
}