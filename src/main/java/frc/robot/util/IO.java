package frc.robot.util;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;


public class IO {
    private static Joystick joystick = new Joystick(0);
    private static XboxController xbox = new XboxController(1);

    public static double getJoyY() {
        return Math.abs(joystick.getY()) > 0.05 ? joystick.getY() : 0;
    }

    public static double getJoyZ() {
        return Math.abs(joystick.getZ()) > 0.05 ? joystick.getZ() : 0;
    }

    public static double getXboxLeftY() {
        return xbox.getLeftY();
    }

    public static double getXboxLeftX() {
        return xbox.getLeftX();
    }

    public static double getXboxRightX() {
        return xbox.getRightX();
    }
}