package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeSystem;
import frc.robot.util.IO;

public class ManualIntakeCommand extends CommandBase {
    private IntakeSystem intakeSystem;
    private double speedMult;

    public ManualIntakeCommand(IntakeSystem subsystem) {
        intakeSystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double speed = IO.getXboxLeftTrigger();

        if (IO.getXboxLeftBumper())
            intakeSystem.output();
        else
            intakeSystem.intake(speed * speedMult);
        if (IO.getXboxAButton())
            new IntakeSequence(Robot.getIntakeSystem().getBallNumber());
    }
}