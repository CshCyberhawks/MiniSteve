package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
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
        double traversePower = IO.getXboxLeftY();
        double power = IO.getXboxRightTrigger();
        double intakePower = IO.getXboxLeftTrigger();
        double pneumaticsPower = IO.getXboxLeftX();

        power = power > 0 ? power : 0;
        intakePower = intakePower > 0 ? intakePower : 0;
        pneumaticsPower = Math.abs(pneumaticsPower) > 0 ? pneumaticsPower : 0;
        traversePower = Math.abs(traversePower) > 0 ? traversePower : 0;

        speedMult = SmartDashboard.getNumber("Speed Mult", speedMult);
        SmartDashboard.putNumber("Speed Mult", speedMult);

        shootSystem.intake(intakePower);
        shootSystem.shoot(power);
        shootSystem.shiftGears(pneumaticsPower);
        shootSystem.traverse(traversePower);
    }
}
