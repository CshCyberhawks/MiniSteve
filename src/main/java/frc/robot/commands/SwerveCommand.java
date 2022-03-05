package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveTrain;
import frc.robot.util.Gyro;
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
        Gyro.setOffset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (IO.getJoystickButton8())
            Gyro.setOffset();
        swerveDriveTrain.drive(-IO.getJoyY(), -IO.getJoyX(), -IO.getJoy2X(), -IO.getJoy2Y(), "tele");
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
