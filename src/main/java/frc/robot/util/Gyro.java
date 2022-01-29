package frc.robot.util;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class Gyro {
    private AHRS gyro;
    private LinearFilter filter;
    private double offset;

    public Gyro() {
        gyro = new AHRS(SPI.Port.kMXP);
        Shuffleboard.getTab("SmartDashboard").add(gyro);
        filter = LinearFilter.movingAverage(2);
        offset = 0;
    }

    private double wrapAroundAngles(double input) {
        return input < 0 ? 360 + input : input;
    }

    public double getAngle() {
        SmartDashboard.putNumber("Gyro Offset", offset);
        return wrapAroundAngles(gyro.getYaw() - offset);
        // return gyro.getYaw();
    }

    public boolean isConnected() {
        return gyro.isConnected();
    }

    public void reset() {
        gyro.reset();
    }

    public void setOffset() {
        // The gyro wasn't being nice
        offset = wrapAroundAngles(gyro.getYaw());
    }
}