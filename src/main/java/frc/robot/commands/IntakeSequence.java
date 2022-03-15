package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.util.IO;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class IntakeSequence extends SequentialCommandGroup {
    //this gets called upon running intake on xbox controller
    private AutoIntakeCommand intakeCommand;
    private AutoTransportCommand transportCommand;

    public IntakeSequence() {
        intakeCommand = new AutoIntakeCommand(Robot.getIntakeSystem());
        transportCommand = new AutoTransportCommand(Robot.getTransportSystem());
        Robot.getTransportSystem().setSequenceState(true);
        addCommands(
            intakeCommand,
            transportCommand
        );
    }

    @Override
    public boolean isFinished() {
        if (!transportCommand.isFinished()) {
            return IO.getXboxStartButton();
        }
        return transportCommand.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        Robot.getTransportSystem().setSequenceState(false);
    }

}
