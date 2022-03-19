package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.util.FieldPosition;
import frc.robot.util.Vector2;
import frc.robot.Robot;
import frc.robot.subsystems.LimeLight;

public class AutoCommandGroup extends SequentialCommandGroup {
    public AutoCommandGroup() {
        Robot.swo.resetPos();
        // add your autonomous commands below
        // example: below will move robot 2 meters on the x and rotate to 90 degrees
        // then it will wait 1 second before moving the robot back to its starting
        // position
        addCommands(
                new AutoGoToPosition(new Vector2(10, 4), 1)
        // new LimeLightAuto()
        // new Wait(1000),
        // new AutoGoToPosition(0, 0)
        );
    }
}
