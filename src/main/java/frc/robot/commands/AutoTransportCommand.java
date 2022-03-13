package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeSystem;
import frc.robot.subsystems.TransportSystem;

public class AutoTransportCommand extends CommandBase {
    private int cargoStored;
    private boolean hitBackBreak = false;

    public AutoTransportCommand(int cargoStored) {
        this.cargoStored = cargoStored;
    }

    @Override
    public void execute() {
        //run elevator
        Robot.getTransportSystem().move(-.25);
        SmartDashboard.putNumber("ballNumber", cargoStored);
    }

    @Override 
    public void end(boolean interrupted) {
        //sets autoClassWhatever ball number = opposite of one given
        //kills traverse
        TransportSystem transportSystem = Robot.getTransportSystem();

        if (transportSystem.cargoStored != 2) {
            transportSystem.cargoStored++;
        }
        transportSystem.move(0);
    }

    @Override
    public boolean isFinished() {
        //return true on 2nd breakbeam if ball == 1, return true on 3rd breakbeam if ball == 2
        hitBackBreak = !hitBackBreak ? !Robot.getBackBreakBeam().get() : hitBackBreak;
        if (cargoStored == 1) 
            return !Robot.getBackBreakBeam().get();
        return Robot.getBackBreakBeam().get() && hitBackBreak;
    }

}

