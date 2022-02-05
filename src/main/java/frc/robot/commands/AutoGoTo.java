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
        desiredPosition = new FieldPosition(1, 0, 0);
        Robot.swo.setDesiredPosition(desiredPosition, 10);
    }

    @Override
    public void execute() {
        //swo is swerve drive odometry, and calling.swo calls its main loop
        Robot.swo.swo();
    }
}