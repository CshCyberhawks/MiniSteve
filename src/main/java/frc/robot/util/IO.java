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
        return new double[] {-deadZone(joystick.getDirectionDegrees()), deadZone(joystick.getMagnitude()), deadZone(joystick.getTwist())};
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

    public static boolean resetGyro() {
        return joystick.getRawButtonPressed(8);
    }

    public static double getXboxLeftY() {
        return Math.abs(xbox.getLeftY()) > deadzone ? xbox.getLeftY() : 0;
    }

    public static double getXboxLeftX() {
        return Math.abs(xbox.getLeftX()) > deadzone ? xbox.getLeftX() : 0;
    }

    public static double getXboxRightX() {
        return Math.abs(xbox.getRightX()) > deadzone ? xbox.getRightX() : 0;
    }

    public static boolean getXboxStartButton() {
        return xbox.getStartButtonPressed();
    }

    public static boolean removeBall() {
        return xbox.getBackButton();
    }

    public static double getJoy2X() {
        return deadZone(joystick2.getX());
    }

    public static boolean limelightLockOn() {
        return joystick.getRawButton(3);
    }

    public static double shootBall() {
        return xbox.getRightTriggerAxis();
    }

    public static boolean getXboxRightBumper() {
        return xbox.getRightBumper();
    }

    public static double getXboxLeftTrigger() {
        return xbox.getLeftTriggerAxis();
    }

    public static boolean getXboxLeftBumper() {
        return xbox.getLeftBumper();
    }

    public static boolean getXboxXButton() {
        return xbox.getXButtonPressed();
    }

    public static boolean getXboxAButton() {
        return xbox.getAButton();
    }

    public static boolean autoIntake() {
        return xbox.getYButton();
    }
}