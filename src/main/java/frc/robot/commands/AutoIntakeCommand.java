package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeSystem;
import frc.robot.util.MathClass;

public class AutoIntakeCommand extends CommandBase {
    private final IntakeSystem intakeSystem;
    private double startTime;

    public AutoIntakeCommand(IntakeSystem subsystem) {
        intakeSystem = subsystem;
        addRequirements(subsystem);
        startTime = MathClass.getCurrentTime();
    }

    @Override
    public void execute() {
        intakeSystem.intake(1);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.getTransportSystem().move(0);
        Robot.getIntakeSystem().kill();
    }

    @Override
    public boolean isFinished() {
        // return !Robot.getFrontBreakBeam().get();
        return MathClass.getCurrentTime() - startTime > 1000;
    }
}