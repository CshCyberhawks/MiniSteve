package frc.robot.util;

import edu.wpi.first.wpilibj.AnalogInput;

public class TurnEncoder {
    private AnalogInput encoder;

    public TurnEncoder(int port) {
        encoder = new AnalogInput(port);
    }

    public double getAngle() {
        return encoder.getVoltage() / (2.5 / 180);
    }
}