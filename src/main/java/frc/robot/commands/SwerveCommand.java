package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveDriveTrain;
import frc.robot.util.DriveState;
import frc.robot.util.Gyro;
import frc.robot.util.MathClass;
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
        if (IO.getJoystickButton5())
            Robot.swo.resetPos();
        Robot.swo.getPosition();
        if (IO.resetGyro())
            Gyro.setOffset();
        if (IO.limelightLockOn())
            swerveDriveTrain.drive(
                    IO.moveRobotX(),
                    IO.moveRobotY(),
                    MathClass.calculateDeadzone(Limelight.getHorizontalOffset(), .5) / 27,
                    IO.getJoyThrottle(),
                    DriveState.TELE);
        else
            swerveDriveTrain.drive(
                    IO.moveRobotX(),
                    IO.moveRobotY(),
                    IO.turnControl(),
                    IO.getJoyThrottle(),
                    DriveState.TELE);
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
