package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class IntakeSequence extends SequentialCommandGroup {
    //this gets called upon running intake on xbox controller
    public IntakeSequence(int ballNum) {
        addCommands(
            new AutoIntakeCommand(Robot.getIntakeSystem()),
            new ElevatorCommand(ballNum)//1st or 2nd ball)
        );
    }
}
