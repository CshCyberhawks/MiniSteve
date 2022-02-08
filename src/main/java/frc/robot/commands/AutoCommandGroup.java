package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.util.FieldPosition;

public class AutoCommandGroup extends SequentialCommandGroup{
    public AutoCommandGroup() {
        addCommands(new AutoGoTo(new FieldPosition(2, 0, 90)));
    }
}
