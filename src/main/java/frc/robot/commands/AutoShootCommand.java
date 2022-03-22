package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ShootSystem;
import frc.robot.subsystems.TransportSystem;
import frc.robot.util.IO;

public class AutoShootCommand extends CommandBase {
    private final ShootSystem shootSystem;
    private TransportSystem transportSystem;
    private final double desiredShootSpeed = 4.2;
    private double lastBottomEncoderSpeed;

    public AutoShootCommand(ShootSystem subsystem) {
        shootSystem = subsystem;
        shootSystem.setAutoShootState(true);
        transportSystem = Robot.getTransportSystem();
        lastBottomEncoderSpeed = 0;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double currentBottomEncoderSpeed = shootSystem.bottomWheelSpeed;
        double encoderDifference = currentBottomEncoderSpeed - lastBottomEncoderSpeed;

        // if (!Robot.getShootBreakBeam().get())
        // transportSystem.setCargoAmount(transportSystem.getCargoAmount() - 1);
        if (Math.abs(currentBottomEncoderSpeed) > Math.abs(desiredShootSpeed)) {
            transportSystem.move(.25);
        }

        shootSystem.shoot(1);
        lastBottomEncoderSpeed = currentBottomEncoderSpeed;
    }

    @Override
    public void end(boolean interrupted) {
        shootSystem.shoot(0);
        transportSystem.move(0);
        shootSystem.setAutoShootState(false);
    }

    @Override
    public boolean isFinished() {
        return transportSystem.getCargoAmount() <= 0 || IO.getAutoShootCancel();
    }
}
