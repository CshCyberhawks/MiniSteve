package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.ADXL345_I2C.AllAxes;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.util.DriveEncoder;
import frc.robot.util.Gyro;
import frc.robot.util.Vector2;

public class AutoCommandGroup extends SequentialCommandGroup {
    public AutoCommandGroup(int configuration) {
        // add your autonomous commands below
        // example: below will move robot 2 meters on the x and rotate to 90 degrees
        // then it will wait 1 second before moving the robot back to its starting
        // position

        if (configuration == 0 && DriverStation.getAlliance() == Alliance.Blue) {
            addCommands(
                    // new AutoBall(1),
                    new AutoGoToCenterAndShoot(0, false),
                    // new AutoBall(0));
                    new AutoGoToPosition(new Vector2(3, 0), 0));
            // new AutoGoToCenterAndShoot(0, true),

        } else if (configuration == 0 && DriverStation.getAlliance() == Alliance.Red) {
            addCommands(
                    // new Wait(3),
                    new AutoGoToCenterAndShoot(0, false),
                    new AutoGoToPosition(new Vector2(3, 0), 0));
            // new AutoBall(0));
            // new AutoGoToCenterAndShoot(0, true),
            // new AutoGoToCenterAndShoot(0, true));
            // new AutoGoToCenterAndShoot(0, false),
            // new AutoGoToPosition(new Vector2(2, 0), 0));
            // new AutoGoToPosition(new Vector2(.7, 3.8), 0));
        }
    }
}
