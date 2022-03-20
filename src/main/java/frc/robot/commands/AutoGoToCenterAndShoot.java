package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.util.Vector2;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoGoToCenterAndShoot extends SequentialCommandGroup {
    private Vector2[] shootPositions;

    public AutoGoToCenterAndShoot(int shootPosition) {
        // add your autonomous commands below
        // example: below will move robot 2 meters on the x and rotate to 90 degrees
        // then it will wait 1 second before moving the robot back to its starting
        // position
        if (DriverStation.getAlliance() == Alliance.Blue) {
            shootPositions = Constants.blueShootingPositions;
        } else {
            shootPositions = Constants.redShootingPositions;
        }
        addCommands(
            new AutoGoToAngle(0),
            new AutoGoToPosition(shootPositions[shootPosition], 0),
            new AutoShootCommand(Robot.getShootSystem())
        );
    }
}
