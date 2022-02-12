package frc.robot.util;

public class MathClass {
    public static double calculateDeadzone(double input, double deadzone) {
        return Math.abs(input) > deadzone ? input : 0;
    }

    public static double gToMetersPerSecond(double g) {
        return g / 9.8066;
    }
}
