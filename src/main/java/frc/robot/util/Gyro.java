package frc.robot.util;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class Gyro {
    private static AHRS gyro = new AHRS(SPI.Port.kMXP);
    private static double offset = 0;



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

    public static double getVelX() {
        return gyro.getVelocityX();
    }

    public static double getVelY() {
        return gyro.getVelocityY();
    }

    public static double getAngVel() {
        return gyro.getVelocityZ();
    }

}