package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSystem;
import frc.robot.util.io;
import edu.wpi.first.math.MathUtil;


public class DriveCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private DriveSystem driveSystem;
  
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public DriveCommand(DriveSystem subsystem) {
      driveSystem = subsystem;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    public void drive(double zInput, double yInput) {
        double leftSpeed = -MathUtil.clamp(yInput + (zInput * .5), -1, 1);
        double rightSpeed = MathUtil.clamp(yInput - (zInput * .5), -1, 1);
        driveSystem.setSpeed(leftSpeed, rightSpeed);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        driveSystem = new DriveSystem();

    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drive(-io.getZ(), -io.getY());
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
