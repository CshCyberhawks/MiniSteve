package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ShootSystem;
import frc.robot.subsystems.TransportSystem;

public class AutoShootCommand extends CommandBase {
    private final ShootSystem shootSystem;
    private TransportSystem transportSystem;
    private final double desiredShootSpeed = 1500;;
    private double lastTopEncoderSpeed;    

    public AutoShootCommand(ShootSystem subsystem) {
        shootSystem = subsystem;
        shootSystem.setAutoShootState(true);
        transportSystem = Robot.getTransportSystem();
        lastTopEncoderSpeed = 0; 
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double currentTopEncoderSpeed = shootSystem.getTopEncoder().getRate() / 8192;
        double encoderDifference = currentTopEncoderSpeed - lastTopEncoderSpeed;

        if (encoderDifference < 0 && transportSystem.getCargoAmount() > 0)
            transportSystem.setCargoAmount(transportSystem.getCargoAmount() - 1);
        if (currentTopEncoderSpeed > desiredShootSpeed)
            transportSystem.move(.25);

        shootSystem.shoot(1);
        lastTopEncoderSpeed = currentTopEncoderSpeed;
    }

    @Override
    public void end(boolean interrupted) {
        shootSystem.shoot(0);
        transportSystem.move(0);
        shootSystem.setAutoShootState(true);
    }

    @Override
    public boolean isFinished() {
        return transportSystem.getCargoAmount() == 0;
    }
}