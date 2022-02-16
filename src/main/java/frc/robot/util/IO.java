package frc.robot.util;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class IO {
    private static Joystick joystick = new Joystick(0);
    private static Joystick joystick2 = new Joystick(1);

    private static XboxController xbox = new XboxController(2);

    private static double deadzone = 0.3;

    public static double deadZone(double input) {
        return Math.abs(input) > deadzone ? input : 0;
    }

    public static double[] getPolarCoords() {
        double[] ret = {-deadZone(joystick.getDirectionDegrees()), deadZone(joystick.getMagnitude()), deadZone(joystick.getTwist())};
        return ret;
    }

    public static double getJoyY() {
        SmartDashboard.putNumber("Joystick Y", joystick.getY());
        return deadZone(joystick.getY());
    }

    public static double getJoyX() {
        SmartDashboard.putNumber("Jotstick X", joystick.getX());
        return deadZone(joystick.getX());
    }

    public static double getJoyTwist() {
       SmartDashboard.putNumber("Joystick Twist", joystick.getTwist());
       return deadZone(joystick.getTwist());
    }

    public static boolean getJoystickButton8() {
        return joystick.getRawButtonPressed(8);
    }

    public static double getXboxLeftY() {
        return deadZone(xbox.getLeftY());
    }

    public static double getXboxLeftX() {
        return deadZone(xbox.getLeftX());
    }

    public static double getXboxRightX() {
        return deadZone(xbox.getRightX());
    }

    public static double getJoy2X() {
        return deadZone(joystick2.getX());
    }

    public static boolean getJoyButton3() {
        return joystick.getRawButton(3);
    }
}