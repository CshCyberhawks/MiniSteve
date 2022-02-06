package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSystem;
import frc.robot.util.IO;

public class ClimbCommand extends CommandBase {
    private final ClimbSystem climbSystem;
    private final double speedMult = .43;
    
    public ClimbCommand(ClimbSystem subsystem) {
        climbSystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        if (IO.getXboxXButton())
        {
            climbSystem.climb(speedMult);
        }

        //IntakeSystem.shoot(speed);
    }
}
