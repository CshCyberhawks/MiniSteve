package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArcadeDrive;
import frc.robot.util.IO;

public class ArcadeCommand extends CommandBase {
    ArcadeDrive arcadeDrive = new ArcadeDrive();

    public ArcadeCommand(ArcadeDrive subsystem) {
        arcadeDrive = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double forwardSpeed = IO.getJoyY();
        double turnSpeed = IO.getJoyTwist();

        double leftSpeed = MathUtil.clamp(forwardSpeed - (turnSpeed / 2), -1, 1);
        double rightSpeed = MathUtil.clamp(forwardSpeed + (turnSpeed / 2), -1, 1);
        arcadeDrive.setSpeed(leftSpeed, rightSpeed);
    }
}
