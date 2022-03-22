package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ShootSystem;
import frc.robot.subsystems.TransportSystem;
import frc.robot.util.IO;
import frc.robot.util.MathClass;

public class AutoShootCommand extends CommandBase {
    private final ShootSystem shootSystem;
    private TransportSystem transportSystem;
    private final double desiredShootSpeed = 4.3;
    private double lastBottomEncoderSpeed;
    private double startTime = 0;
    private double lastShootTime = 0;

    public AutoShootCommand(ShootSystem subsystem) {
        shootSystem = subsystem;
        shootSystem.setAutoShootState(true);
        transportSystem = Robot.getTransportSystem();
        lastBottomEncoderSpeed = 0;
        startTime = MathClass.getCurrentTime();
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double currentBottomEncoderSpeed = shootSystem.bottomWheelSpeed;
        double encoderDifference = currentBottomEncoderSpeed - lastBottomEncoderSpeed;

        // if (!Robot.getShootBreakBeam().get())
        // transportSystem.setCargoAmount(transportSystem.getCargoAmount() - 1);
        if (Math.abs(currentBottomEncoderSpeed) > Math.abs(desiredShootSpeed)) {
            transportSystem.move(.5);
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
        lastShootTime = transportSystem.getCargoAmount() <= 0 && lastShootTime == 0 ? MathClass.getCurrentTime()
                : lastShootTime;
        boolean cargoReturn = transportSystem.getCargoAmount() <= 0 && MathClass.getCurrentTime() - lastShootTime > .1;
        return (transportSystem.getCargoAmount() <= 0 && cargoReturn) || IO.getAutoShootCancel()
                || (MathClass.getCurrentTime() - startTime) > 5;
    }
}
