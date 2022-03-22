package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeSystem;

public class AutoIntakeCommand extends CommandBase {
    private final IntakeSystem intakeSystem;
    private double storedCargoAtStart;

    public AutoIntakeCommand(IntakeSystem subsystem) {
        intakeSystem = subsystem;
        storedCargoAtStart = Robot.transportSystem.cargoAmount;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        intakeSystem.intake(1);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("frontBrokeIntake");
        Robot.getTransportSystem().move(0);
        Robot.getIntakeSystem().kill();
    }

    @Override
    public boolean isFinished() {
        return !Robot.getFrontBreakBeam().get();
    }
}