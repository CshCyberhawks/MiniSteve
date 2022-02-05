package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.SwerveOdometry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.FieldPosition;
import frc.robot.util.Vector2;

public class AutoGoTo extends CommandBase{
    private Vector2 desiredPosition;

    @Override
    public void initialize() {
        desiredPosition = new Vector2(10, 10);
        Robot.swo.setDesiredPosition(desiredPosition, 10);
    }

    @Override
    public void execute() {
        Robot.swo.swo();
    }
}