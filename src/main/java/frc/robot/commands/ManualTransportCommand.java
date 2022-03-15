package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TransportSystem;
import frc.robot.util.IO;

public class ManualTransportCommand extends CommandBase {
    private TransportSystem transportSystem;

    public ManualTransportCommand(TransportSystem subsystem) {
        transportSystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double transportPower = IO.getXboxLeftY();

        if (!transportSystem.getSequenceState())
            transportSystem.move(transportPower);

    }
}