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
    private final int desiredShootSpeed = 5;
    private double lastTopEncoderSpeed;
    private double startTime;

    public AutoShootCommand(ShootSystem subsystem) {
        shootSystem = subsystem;
        shootSystem.setAutoShootState(true);
        transportSystem = Robot.getTransportSystem();
        lastTopEncoderSpeed = 0;

        startTime = MathClass.getCurrentTime();

        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double currentTopEncoderSpeed = shootSystem.getTopEncoder().getRate() / 8192;
        double encoderDifference = currentTopEncoderSpeed - lastTopEncoderSpeed;

        // if (!Robot.getShootBreakBeam().get())
        // transportSystem.setCargoAmount(transportSystem.getCargoAmount() - 1);
        if (currentTopEncoderSpeed > desiredShootSpeed) {
            SmartDashboard.putBoolean("moving", true);
            transportSystem.move(.25);
        } else {
            SmartDashboard.putBoolean("moving", false);
        }

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
        // return transportSystem.getCargoAmount() == 0;
        return MathClass.getCurrentTime() - startTime > 800;
    }
}
