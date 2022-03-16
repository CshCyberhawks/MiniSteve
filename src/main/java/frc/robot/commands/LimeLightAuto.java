package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLight;

public class LimeLightAuto extends CommandBase {
    private LimeLight limeLight;

    public LimeLightAuto(LimeLight limeLight) {
        // Use addRequirements() here to declare subsystem dependencies.
        this.limeLight = limeLight;
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("limeLightDistance", limeLight.getDistance());
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
