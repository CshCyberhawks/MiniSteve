package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSystem;
import frc.robot.util.IO;

public class ShootCommand extends CommandBase {
    private final ShootSystem shootSystem;
    private AutoShootCommand shootCommand;
    private final double speedMult = .55;

    public ShootCommand(ShootSystem subsystem) {
        shootSystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        if (IO.raiseShootSpeed())
            shootSystem.shootMult += 0.05;
        if (IO.lowerShootSpeed())
            shootSystem.shootMult -= 0.05;

        if (IO.autoShoot()) {
            shootCommand = new AutoShootCommand(shootSystem);
            shootCommand.schedule();
        } else if (!shootSystem.getAutoShootState()) {
            SmartDashboard.putNumber("Speed Mult", speedMult);
            shootSystem.shoot(IO.shootBall());
        }
    }
}