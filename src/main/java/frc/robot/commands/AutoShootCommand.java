package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ShootSystem;
import frc.robot.subsystems.TransportSystem;
import frc.robot.util.IO;

public class AutoShootCommand extends CommandBase {
    private final ShootSystem shootSystem;
    private double lastTopEncoderSpeed = 0;
    private double desiredShootSpeed = 1500;
    private TransportSystem transportSystem;

    public AutoShootCommand(ShootSystem subsystem) {
        shootSystem = subsystem;
        shootSystem.autoShootRunning = true;
        transportSystem = Robot.getTransportSystem();
        addRequirements(subsystem);

    }

    @Override
    public void execute() {
        double currentTopEncoderSpeed = shootSystem.topEncoder.getRate() / 8192;

        double encoderDifference = currentTopEncoderSpeed - lastTopEncoderSpeed;

        if (encoderDifference < 0 && transportSystem.cargoStored > 0) {
            transportSystem.cargoStored--;
        }

        if (currentTopEncoderSpeed > desiredShootSpeed) {
            transportSystem.move(1);
        }

        shootSystem.shoot(1);
        lastTopEncoderSpeed = currentTopEncoderSpeed;

    }

    @Override
    public void end(boolean interrupted) {
        shootSystem.shoot(0);
        transportSystem.move(0);
        shootSystem.autoShootRunning = true;

    }

    @Override
    public boolean isFinished() {
        return transportSystem.cargoStored == 0;
    }
}
