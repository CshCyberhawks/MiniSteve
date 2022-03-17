package frc.robot.util;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.Constants;

public class TurnEncoder {
    private AnalogInput encoder;
    private int encoderPort;

    public TurnEncoder(int port) {
        encoder = new AnalogInput(port);
        encoderPort = port;
    }

    private double voltageToDegrees(double input) {
        return input * 72;
    }

    public double get() {
        // return
        // (double)Math.floor(filter.calculate(voltageToDegrees(encoder.getVoltage()) -
        // Constants.turnEncoderOffsets[encoderPort]) * 10d) / 10d;
        // return
        // (double)Math.floor(filter.calculate(voltageToDegrees(encoder.getVoltage())) *
        // 10d) / 10d;
        return voltageToDegrees(encoder.getVoltage()) - Constants.turnEncoderOffsets[encoderPort];
    }
}