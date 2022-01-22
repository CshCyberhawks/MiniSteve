package frc.robot.util;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.math.filter.LinearFilter;

import frc.robot.Constants;

public class TurnEncoder {
    private AnalogInput encoder;
    private LinearFilter filter;
    private int encoderPort;

    public TurnEncoder(int port) {
        encoder = new AnalogInput(port);
        encoderPort = port;
        filter = LinearFilter.movingAverage(1000);
    }

    private double voltageToDegrees(double input) {
        return input / (2.5/180);
    }

    public double getAngle() {
        return (double)Math.floor(filter.calculate(voltageToDegrees(encoder.getVoltage()) - Constants.turnEncoderOffsets[encoderPort]) * 10d) / 10d;
    }
}