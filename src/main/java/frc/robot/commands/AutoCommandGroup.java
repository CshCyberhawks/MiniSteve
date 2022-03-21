package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.util.Gyro;
import frc.robot.util.Vector2;

public class AutoCommandGroup extends SequentialCommandGroup {
    public AutoCommandGroup(int configuration) {
        // add your autonomous commands below
        // example: below will move robot 2 meters on the x and rotate to 90 degrees
        // then it will wait 1 second before moving the robot back to its starting
        // position

        if (configuration == 0) {
            addCommands(
                    // Gyro.offset =
                    // new AutoBall(4),
                    new AutoGoToCenterAndShoot(0, false),
                    new AutoBall(4),
                    new AutoGoToCenterAndShoot(0, true),
                    new AutoGoToPosition(new Vector2(6, 0), 0));
        }
    }
}
