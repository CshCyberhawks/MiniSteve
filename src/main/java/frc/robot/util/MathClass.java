package frc.robot.util;

public class MathClass {
    public static double calculateDeadzone(double input, double deadzone) {
        return Math.abs(input) > deadzone ? input : 0;
    }

    public static double normalize(double input, double low, double high) {
        

        return 0;
    }

    public static double optimize(double desiredAngle, double currentAngle) {
        if (Math.abs(desiredAngle - currentAngle) > 90 && Math.abs(desiredAngle - currentAngle) < 270) {
			return -1;
		}
        return 1;
    }
}
