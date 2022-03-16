package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLight;

public class LimeLightAuto extends CommandBase {

    public LimeLightAuto() {
        // Use addRequirements() here to declare subsystem dependencies.
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("limeLightDistance", LimeLight.getDistance());
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
