package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveDriveTrain;
import frc.robot.util.IO;

public class SwerveCommand extends CommandBase {
    private final SwerveDriveTrain swerveDriveTrain;

    public SwerveCommand(SwerveDriveTrain subsystem) {
        swerveDriveTrain = subsystem;
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        swerveDriveTrain.gyro.setOffset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (IO.resetGyro())
            swerveDriveTrain.gyro.setOffset();
        if (IO.limelightLockOn())
            swerveDriveTrain.drive(-IO.moveRobotY(), -IO.moveRobotX(),
                    -IO.deadzone(Limelight.getHorizontalOffset(), .5) / 27);
        else
            swerveDriveTrain.drive(-IO.moveRobotY(), -IO.moveRobotX(), -IO.turnControl());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
