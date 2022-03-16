package frc.robot.util;

// import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class Gyro {
    private AHRS gyro;
    private double offset;

    public Gyro() {
        gyro = new AHRS(SPI.Port.kMXP);
        Shuffleboard.getTab("SmartDashboard").add(gyro);
        offset = 0;
    }

    public double getVelX() {
        return gyro.getVelocityX();
    }

    public double getVelY() {
        return gyro.getVelocityY();
    }

    public double getAngVel() {
        return gyro.getVelocityZ();
    }

    public double getAngle() {
        SmartDashboard.putNumber("Gyro Offset", offset);
        return wrapAroundAngles(gyro.getYaw() - offset);
        // return gyro.getYaw();
    }

    public void setOffset() {
        // The gyro wasn't being nice
        offset = wrapAroundAngles(gyro.getYaw());
    }

    public boolean isConnected() {
        return gyro.isConnected();
    }

    private double wrapAroundAngles(double input) {
        while (input < 0)
            input += 360;
        return input;
    }

    public void reset() {
        gyro.reset();
    }
}