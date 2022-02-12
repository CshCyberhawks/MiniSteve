package frc.robot.util;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.filter.LinearFilter;
import com.kauailabs.navx.frc.AHRS;

public class Gyro {
    private static AHRS gyro = new AHRS(SPI.Port.kMXP);
    private static double offset = 0;
    private static LinearFilter filter = LinearFilter.highPass(0.1, 0.02);

    private static double wrapAroundAngles(double input) {
        return input < 0 ? 360 + input : input;
    }

    public static double getAngle() {
        SmartDashboard.putNumber("Gyro Offset", offset);
        return wrapAroundAngles(gyro.getYaw() - offset);
        // return gyro.getYaw();
    }

    public static boolean isConnected() {
        return gyro.isConnected();
    }

    public static void reset() {
        gyro.reset();
    }

    public static void setOffset() {
        // The gyro wasn't being nice
        offset = wrapAroundAngles(gyro.getYaw());
    }

    public static double getVelZ() {
        return filter.calculate(gyro.getVelocityZ());
    }

    public static double getVelocityX() {
        return filter.calculate(gyro.getVelocityX());
        // return gyro.getVelocityX();
    }

    public static double getVelocityY() {
        return filter.calculate(gyro.getVelocityY());
        // return gyro.getVelocityY();
    }

    public static double getAccelX() {
        return filter.calculate(gyro.getWorldLinearAccelX());
    }

    public static double getAccelY() {
        return filter.calculate(gyro.getWorldLinearAccelX());
    }

    public static void calibrate() {
        gyro.calibrate();
    }

}