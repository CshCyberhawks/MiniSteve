package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.SwerveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.FieldPosition;
import frc.robot.util.Vector2;

public class AutoGoTo extends CommandBase{
    private FieldPosition desiredPosition;

    @Override
    public void initialize() {
        Robot.swo.resetPos();
        desiredPosition = new FieldPosition(0, 1.5, 0);
        Robot.swerveAuto.setDesiredPosition(desiredPosition);
    }

    @Override
    public void execute() {
        Robot.swerveAuto.drive();
        System.out.println("auto command execute ran");
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("auto command finsihed", true);
    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean(" isAtPos ", Robot.swerveAuto.isAtDesiredPosition());
        return Robot.swerveAuto.isAtDesiredPosition();
    }

}