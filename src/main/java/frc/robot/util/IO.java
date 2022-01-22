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
        return Math.abs(xbox.getLeftY()) > 0.09 ? xbox.getLeftY() : 0;
    }

    public static double getXboxLeftX() {
        return Math.abs(xbox.getLeftX()) > 0.09 ? xbox.getLeftX() : 0;
    }

    public static double getXboxRightX() {
        return Math.abs(xbox.getRightX()) > 0.09 ? xbox.getRightX() : 0;
    }
}