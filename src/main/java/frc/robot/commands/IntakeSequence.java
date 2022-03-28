package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.util.IO;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class IntakeSequence extends SequentialCommandGroup {
    // this gets called upon running intake on xbox controller
    private AutoIntakeCommand autoIntakeCommand;
    private AutoTransportCommand autoTransportCommand;

    public IntakeSequence() {
        autoIntakeCommand = new AutoIntakeCommand(Robot.getIntakeSystem());
        autoTransportCommand = new AutoTransportCommand(Robot.getTransportSystem());
        Robot.getTransportSystem().setSequenceState(true);
        addCommands(
                autoIntakeCommand,
                autoTransportCommand);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("intakeSequenceEnd");
        Robot.getTransportSystem().setSequenceState(false);
    }

    @Override
    public boolean isFinished() {
        if (!autoTransportCommand.isFinished()) {
            if (IO.getAutoIntakeCancel()) {
                System.out.println("manually canceled sequence");
            }
            return IO.getAutoIntakeCancel();
        }
        return autoTransportCommand.isFinished();
    }
}