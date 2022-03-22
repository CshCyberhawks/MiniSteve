package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.TransportSystem;

public class AutoTransportCommand extends CommandBase {
    private TransportSystem transportSystem;
    private int cargoStored;
    private boolean hitBackBreak;

    public AutoTransportCommand(TransportSystem transportSystem) {
        this.transportSystem = transportSystem;
        this.cargoStored = transportSystem.getCargoAmount();
        hitBackBreak = false;

        addRequirements(transportSystem);
    }

    @Override
    public void execute() {
        transportSystem.move(.25);
    }

    @Override
    public void end(boolean interrupted) {
        // if (transportSystem.getCargoAmount() < 2 && interrupted == false)
        // transportSystem.setCargoAmount(transportSystem.getCargoAmount() + 1);
        transportSystem.move(0);
    }

    @Override
    public boolean isFinished() {
        boolean backBeam = Robot.getBackBreakBeam().get();
        hitBackBreak = !hitBackBreak ? !backBeam : hitBackBreak;
        if (cargoStored <= 1)
            return !backBeam;
        return backBeam && hitBackBreak;
    }
}