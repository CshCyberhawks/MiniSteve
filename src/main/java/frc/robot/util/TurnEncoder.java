package frc.robot.util;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class TurnEncoder {
    private AnalogInput encoder;
    private LinearFilter filter;
    private int encoderPort;

    public TurnEncoder(int port) {
        encoder = new AnalogInput(port);
        encoderPort = port;
        filter = LinearFilter.movingAverage(2);
    }

    private double voltageToDegrees(double input) {
        return input / (2.5/180);
    }

    public double get() {
        //return (double)Math.floor(filter.calculate(voltageToDegrees(encoder.getVoltage()) - Constants.turnEncoderOffsets[encoderPort]) * 10d) / 10d;
        //return (double)Math.floor(filter.calculate(encoder.getVoltage()) * 10d) / 10d;
        return voltageToDegrees(encoder.getVoltage()) * (Math.PI / 180);
    }
}