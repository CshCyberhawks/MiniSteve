package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class AutoCommandGroup extends SequentialCommandGroup {
    public AutoCommandGroup(int configuration) {
        Robot.swo.resetPos();
        // add your autonomous commands below
        // example: below will move robot 2 meters on the x and rotate to 90 degrees
        // then it will wait 1 second before moving the robot back to its starting
        // position

        if (configuration == 0) {
            addCommands(
                    new AutoBall(4),
                    new AutoGoToCenterAndShoot(0),
                    new AutoBall(5),
                    new AutoGoToCenterAndShoot(0));

        }
    }
}
