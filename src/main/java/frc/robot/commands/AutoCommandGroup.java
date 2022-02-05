package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoCommandGroup extends SequentialCommandGroup{
    public AutoCommandGroup() {
        addCommands(new AutoGoTo());
    }
}
