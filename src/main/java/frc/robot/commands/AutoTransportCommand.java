package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.TransportSystem;

public class AutoTransportCommand extends CommandBase {
    private int cargoStored;
    private boolean hitBackBreak = false;
    private TransportSystem transportSystem;

    public AutoTransportCommand(TransportSystem transportSystem) {
        this.transportSystem = transportSystem;
        this.cargoStored = transportSystem.getCargoAmount();

        addRequirements(transportSystem);
    }

    @Override
    public void execute() {
        //run elevator
        transportSystem.move(-.25);
    }

    @Override 
    public void end(boolean interrupted) {
        //sets autoClassWhatever ball number = opposite of one given
        //kills traverse
        if (transportSystem.getCargoAmount() < 2 && interrupted == false) {
            transportSystem.setCargoAmount(transportSystem.getCargoAmount() + 1);
        }
        transportSystem.move(0);
    }

    @Override
    public boolean isFinished() {
        //return true on 2nd breakbeam if ball == 1, return true on 3rd breakbeam if ball == 2
        hitBackBreak = !hitBackBreak ? !Robot.getBackBreakBeam().get() : hitBackBreak;
        if (cargoStored == 0) 
            return !Robot.getBackBreakBeam().get();
        return Robot.getBackBreakBeam().get() && hitBackBreak;
    }

}

