package frc.robot.util;
import edu.wpi.first.wpilibj.Joystick;


public class IO {
    private static Joystick joystick = new Joystick(0);

    public static double getY() {
        return Math.abs(joystick.getY()) > 0.05 ? joystick.getY() : 0;
    }

    public static double getZ() {
        return Math.abs(joystick.getZ()) > 0.05 ? joystick.getZ() : 0;
    }
    
    public static double getX() {
        return Math.abs(joystick.getX()) > 0.05 ? joystick.getX() : 0;
    }

    public static double getThrottle() {
        return joystick.getThrottle();
    }
}