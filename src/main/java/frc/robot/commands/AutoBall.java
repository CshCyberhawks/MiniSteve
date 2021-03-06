package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.util.MathClass;
import frc.robot.util.Vector2;
import frc.robot.Robot;

public class AutoBall extends CommandBase {
    private double startTime = 0;
    private IntakeSequence intakeSequence;
    private AutoGoToPosition autoPos;

    public AutoBall(int ballNumber) {
        startTime = MathClass.getCurrentTime();
        Robot.swo.resetPos();
        // add your autonomous commands below
        // example: below will move robot 2 meters on the x and rotate to 90 degrees
        // then it will wait 1 second before moving the robot back to its starting
        // position
        Vector2 desiredPosition = Robot.swerveAuto.ballPositions[ballNumber];
        double desiredAngle = 180
                - (MathClass.cartesianToPolar(desiredPosition.x - Robot.swo.getPosition().positionCoord.x,
                        desiredPosition.y - Robot.swo.getPosition().positionCoord.y)[0]);
        Robot.driveShuffleboardTab.add("desiredAngleAuto", desiredAngle);
        SmartDashboard.putNumber("desiredAngleAuto", desiredAngle);
        intakeSequence = new IntakeSequence();
        autoPos = new AutoGoToPosition(ballNumber, 0);
        // new AutoGoToAngle((desiredAngle + 180) % 360), // desiredAngle),
        // new LimeLightAuto());
    }

    @Override
    public void initialize() {
        autoPos.schedule();
        intakeSequence.schedule();

    }

    @Override
    public boolean isFinished() {
        return intakeSequence.isFinished(); // || MathClass.getCurrentTime() - startTime > 5;
    }
}
