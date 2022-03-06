package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.util.FieldPosition;
import frc.robot.Robot;

public class AutoCommandGroup extends SequentialCommandGroup {
    public AutoCommandGroup() {
        Robot.swo.resetPos();
        // add your autonomous commands below
        // example: below will move robot 2 meters on the x and rotate to 90 degrees
        // then it will wait 1 second before moving the robot back to its starting
        // position
        addCommands(
                new AutoGoTo(new FieldPosition(6, 0, 0))
        // new Wait(1000),
        // new AutoGoTo(new FieldPosition(0, 0, 0))
        );
    }
}
