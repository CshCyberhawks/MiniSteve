package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSystem;
import frc.robot.util.IO;

public class ShootCommand extends CommandBase {
    private final ShootSystem shootSystem;
    
    public ShootCommand(ShootSystem subsystem) {
        shootSystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double speed = IO.getXboxRightTrigger();
        speed = speed > 0 ? speed : IO.getXboxRightBumper() ? -0.25 : 0;

        shootSystem.shoot(speed);
    }
}
