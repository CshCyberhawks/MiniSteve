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
        double speed = IO.intakeBall();

        SmartDashboard.putBoolean("intakeSequenceBool", Robot.getTransportSystem().getSequenceState());

        if (IO.autoIntake()) {
            Robot.isSpitting = false;
            IntakeSequence intakeCommandSequence = new IntakeSequence();
            intakeCommandSequence.schedule();
            SmartDashboard.putBoolean("intakeSequenceBegan", true);
        } else if (!Robot.getTransportSystem().getSequenceState())
            if (IO.removeBall()) {
                Robot.isSpitting = true;
                intakeSystem.intake(-1);
                Robot.transportSystem.move(-1);
            } else {
                Robot.isSpitting = false;
                intakeSystem.intake(speed);
            }
    }
}