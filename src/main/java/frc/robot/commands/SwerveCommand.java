package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveDriveTrain;
import frc.robot.util.IO;

public class SwerveCommand extends CommandBase {
    private final SwerveDriveTrain swerveDriveTrain;
    private boolean isLimeLockToggle = false;
    
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
        
        if (IO.getJoystickButton8())
            swerveDriveTrain.gyro.setOffset();
        if (IO.getJoyButton3())
            swerveDriveTrain.drive(-IO.getJoyY(), -IO.getJoyX(), deadzone(-Limelight.getHorizontalOffset() / 27));
        else
            swerveDriveTrain.drive(-IO.getJoyY(), -IO.getJoyX(), -IO.getJoy2X());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    public void centerWithLimelight() {
        
    }

    public static double deadzone(double input) {
        return Math.abs(input) > 15 ? input : 0;
    }
}
