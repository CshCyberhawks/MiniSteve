package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.util.Vector2;
import frc.robot.Robot;

public class AutoBall extends SequentialCommandGroup {
    public AutoBall(int ballNumber) {
        Robot.swo.resetPos();
        // add your autonomous commands below
        // example: below will move robot 2 meters on the x and rotate to 90 degrees
        // then it will wait 1 second before moving the robot back to its starting
        // position
        Vector2 desiredPosition = Robot.swerveAuto.ballPositions[ballNumber];
        double desiredAngle = Math.atan2(desiredPosition.y, desiredPosition.x);
        Robot.driveShuffleboardTab.add("desiredAngleAuto", desiredAngle);
        addCommands(
                new AutoGoToAngle(90), // desiredAngle),
                new AutoGoToPosition(ballNumber, 0),
                new LimeLightAuto());
    }
}
