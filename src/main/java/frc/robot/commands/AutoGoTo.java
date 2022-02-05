package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.SwerveOdometry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.FieldPosition;
import frc.robot.util.Vector2;

public class AutoGoTo extends CommandBase{
    private FieldPosition desiredPosition;

    @Override
    public void initialize() {
        desiredPosition = new FieldPosition(100, 0, 0);
        Robot.swo.resetPos();
        Robot.swo.setDesiredPosition(desiredPosition);
    }

    @Override
    public void execute() {
        //swo is swerve drive odometry, and calling.swo calls its main loop
        Robot.swo.swo();
    }

    @Override
    public boolean isFinished() {
        return Robot.swo.isAtPosition();
    }
}