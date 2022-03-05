package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSystem;
import frc.robot.util.IO;

public class ShootCommand extends CommandBase {
    private final ShootSystem shootSystem;
    private final double speedMult = .33;
    private final double traversalPower = .95;
    
    public ShootCommand(ShootSystem subsystem) {
        shootSystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double speed = IO.getXboxRightTrigger();
        double power =IO.getXboxRightBumper() ? speedMult : 0;
        speed = speed > 0 ? speed * speedMult : IO.getXboxLeftBumper() ? -traversalPower : 0;
        shootSystem.shoot(power);
        shootSystem.traverse(speed);
    }
}
