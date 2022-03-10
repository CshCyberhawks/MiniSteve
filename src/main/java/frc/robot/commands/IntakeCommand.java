package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSystem;
import frc.robot.util.IO;

public class IntakeCommand extends CommandBase {
    private final IntakeSystem intakeSystem;
    private final double speedMult = 1;
    
    public IntakeCommand(IntakeSystem subsystem) {
        intakeSystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double speed = IO.getXboxLeftTrigger();
        boolean out = IO.getXboxLeftBumper();

        if(speed > 0) {
            intakeSystem.intake(speed * speedMult);
        }
        else if(out) {
            intakeSystem.output();
        }
    }
}
