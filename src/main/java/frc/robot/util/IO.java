package frc.robot.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IO {
    private static Joystick joystick = new Joystick(0);
    private static Joystick joystick2 = new Joystick(1);

    private static XboxController xbox = new XboxController(2);

    private static double deadzoneVal = 0.3;


    public static double[] getPolarCoords() {
        double[] ret = {-MathClass.calculateDeadzone(joystick.getDirectionDegrees(), deadzoneVal), MathClass.calculateDeadzone(joystick.getMagnitude(), deadzoneVal), MathClass.calculateDeadzone(joystick.getTwist(), deadzoneVal)};
        return ret;
    }

    public static double getJoyY() {
        SmartDashboard.putNumber("Joystick Y", joystick.getY());
        return MathClass.calculateDeadzone(joystick.getY(), deadzoneVal);
    }

    public static double getJoyX() {
        SmartDashboard.putNumber("Jotstick X", joystick.getX());
        return MathClass.calculateDeadzone(joystick.getX(), deadzoneVal);
    }

    public static double getJoyTwist() {
       SmartDashboard.putNumber("Joystick Twist", joystick.getTwist());
       return MathClass.calculateDeadzone(joystick.getTwist(), deadzoneVal);
    }

    public static boolean getJoystickButton8() {
        return joystick.getRawButtonPressed(8);
    }

    public static double getXboxLeftY() {
        return MathClass.calculateDeadzone(xbox.getLeftY(), deadzoneVal);
    }

    public static double getXboxLeftX() {
        return MathClass.calculateDeadzone(xbox.getLeftX(), deadzoneVal);
    }

    public static double getXboxRightX() {
        return MathClass.calculateDeadzone(xbox.getRightX(), deadzoneVal);
    }

    public static double getJoy2X() {
        return MathClass.calculateDeadzone(joystick2.getX(), deadzoneVal);
    }

    public static double getJoy2Y() {
	return MathClass.calculateDeadzone(joystick2.getY(), deadzoneVal);
    }

}
