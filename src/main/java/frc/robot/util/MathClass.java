package frc.robot.util;

import edu.wpi.first.util.WPIUtilJNI;

public class MathClass {
    public static double calculateDeadzone(double input, double deadzone) {
        return Math.abs(input) > deadzone ? input : 0;
    }

    public static double gToMetersPerSecond(double g) {
        return g / 9.8066;
    }

    public static double[] cartesianToPolar(double x, double y) {
        // math to turn cartesian into polar
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.toDegrees(Math.atan2(y, x));

        double[] ret = { theta, r };
        return ret;
    }

    public static double[] polarToCartesian(double theta, double r) {
        // math to turn polar coordinate into cartesian
        double x = r * Math.cos(Math.toRadians(theta));
        double y = r * Math.sin(Math.toRadians(theta));

        double[] ret = { x, y };
        return ret;
    }

    public static double getMin(double[] values) {
        double min = values[0];

        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
            }
        }

        return min;
    }

    public static double getMax(double[] values) {
        double max = values[0];

        for (int i = 1; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
            }
        }

        return max;
    }

    public static double[] normalizeSpeeds(double[] speeds, double maxSpeed, double minSpeed) {
        double[] retSpeeds = speeds;

        double max = getMax(speeds);
        double min = getMin(speeds);

        for (int i = 0; i < speeds.length; i++) {
            if (max > maxSpeed && speeds[i] > 0) {
                retSpeeds[i] = speeds[i] / max * maxSpeed;
            } else if (min < minSpeed && speeds[i] < 0) {
                retSpeeds[i] = speeds[i] / min * minSpeed;
            }
        }

        return retSpeeds;
    }

    public static double optimize(double desiredAngle, double currentAngle) {
        if (Math.abs(desiredAngle - currentAngle) > 90 && Math.abs(desiredAngle - currentAngle) < 270) {
            return -1;
        }
        return 1;
    }

    public static double getCurrentTime() {
        return WPIUtilJNI.now() * 1.0e-6;
    }

    public static double swoToMeters(double swo) {
        return swo * 3.69230769231;
    }

    public static double metersToSwos(double meters) {
        return meters * 0.27083333333;
    }
}
