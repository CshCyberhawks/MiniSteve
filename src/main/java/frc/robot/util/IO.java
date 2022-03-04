package frc.robot.util;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class IO {
    // private static Joystick joystick = new Joystick(0);
    
    private static XboxController xbox = new XboxController(1);
    private static Joystick joystick = new Joystick(0);

    private static double deadzone = 0.3;

    public static double deadZone(double input) {
        return Math.abs(input) > deadzone ? input : 0;
    }
    public static double[] getPolarCoords() {
        double[] ret = {-deadZone(joystick.getDirectionDegrees()), deadZone(joystick.getMagnitude()), deadZone(joystick.getTwist())};
        return ret;
    }

    // public static double getJoyY() {
    //     SmartDashboard.putNumber("Joystick Y", joystick.getY());
    //     return Math.abs(joystick.getY()) > deadzone ? joystick.getY() : 0;
    // }

    // public static double getJoyX() {
    //     SmartDashboard.putNumber("Jotstick X", joystick.getX());
    //     return Math.abs(joystick.getX()) > deadzone ? joystick.getX() : 0;
    // }

    // public static double getJoyTwist() {
    //    SmartDashboard.putNumber("Joystick Twist", joystick.getTwist());
    //    return Math.abs(joystick.getTwist()) > deadzone ? joystick.getTwist() : 0;
    // }

    public static double getXboxLeftY() {
        return Math.abs(xbox.getLeftY()) > deadzone ? xbox.getLeftY() : 0;
    }
    public static double getXboxLeftX() {
        return Math.abs(xbox.getLeftX()) > deadzone ? xbox.getLeftX() : 0;
    }
    public static double getXboxRightX() {
        return Math.abs(xbox.getRightX()) > deadzone ? xbox.getRightX() : 0;
    }
    public static boolean getXboxRightBumper() {
        return (xbox.getRightBumperPressed() ? xbox.getRightBumper() : false);
    }
    public static boolean getXboxLeftBumper() {
        return (xbox.getLeftBumperPressed() ? xbox.getLeftBumper() : false);
    }
    public static double getXboxLeftTrigger() {
        return Math.abs(xbox.getLeftTriggerAxis()) > deadzone ? xbox.getLeftTriggerAxis() : 0;
    }
    public static double getXboxRightTrigger() {
        return Math.abs(xbox.getRightTriggerAxis()) > deadzone ? xbox.getRightTriggerAxis() : 0;
    }
    public static boolean getXboxXButton() {
        return (xbox.getXButtonPressed() ? xbox.getXButton() : false);
    }
}