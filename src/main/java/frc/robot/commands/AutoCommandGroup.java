package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.util.FieldPosition;
import frc.robot.Robot;

public class AutoCommandGroup extends SequentialCommandGroup{
    public AutoCommandGroup() {
        Robot.swo.resetPos();
        addCommands(
            new AutoGoTo(new FieldPosition(2, 0, 90)),
            new Wait(1000),
            new AutoGoTo(new FieldPosition(0, 0, 0))
        );
    }
}
