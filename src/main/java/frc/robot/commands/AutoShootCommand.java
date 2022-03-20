package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ShootSystem;
import frc.robot.subsystems.TransportSystem;
import frc.robot.util.MathClass;

public class AutoShootCommand extends CommandBase {
    private final ShootSystem shootSystem;
    private TransportSystem transportSystem;
    private final int desiredShootSpeed = 3;
    private double lastBottomEncoderSpeed;
    private double startTime;

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
        double currentBottomEncoderSpeed = shootSystem.getBottomEncoder().getRate();
        double encoderDifference = currentBottomEncoderSpeed - lastBottomEncoderSpeed;

        // if (!Robot.getShootBreakBeam().get())
        // transportSystem.setCargoAmount(transportSystem.getCargoAmount() - 1);
        if (currentBottomEncoderSpeed > Math.abs(desiredShootSpeed)) {
            transportSystem.move(.25);
            SmartDashboard.putBoolean("moving", true);
        } else {
            SmartDashboard.putBoolean("moving", false);
        }

        shootSystem.shoot(1);
        lastBottomEncoderSpeed = currentBottomEncoderSpeed;
    }

    @Override
    public void end(boolean interrupted) {
        shootSystem.shoot(0);
        transportSystem.move(0);
        shootSystem.setAutoShootState(true);
    }

    @Override
    public boolean isFinished() {
        // return transportSystem.getCargoAmount() == 0;
        return MathClass.getCurrentTime() - startTime > 800;
    }
}
