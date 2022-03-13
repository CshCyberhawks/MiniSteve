package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSystem;
import frc.robot.util.IO;

public class ShootCommand extends CommandBase {
    private final ShootSystem shootSystem;
    private double speedMult = .55;

    public ShootCommand(ShootSystem subsystem) {
        shootSystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double power = IO.getXboxRightTrigger();

        speedMult = SmartDashboard.getNumber("Speed Mult", speedMult);
        SmartDashboard.putNumber("Speed Mult", speedMult);

        shootSystem.shoot(power);

    }
}
