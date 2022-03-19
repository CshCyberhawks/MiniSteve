package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSystem;
import frc.robot.util.IO;

public class ClimbCommand extends CommandBase {
    private final ClimbSystem climbSystem;
    private final double speedMult = 1;

    public ClimbCommand(ClimbSystem subsystem) {
        climbSystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        climbSystem.climb(IO.climbControl() * speedMult);
        climbSystem.controlPneumatics(IO.deployPneumatics());
    }
}