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

        return new double[] { theta, r };
    }

    public static double[] polarToCartesian(double theta, double r) {
        // math to turn polar coordinate into cartesian
        theta = Math.toRadians(theta);
        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);

        return new double[] { x, y };
    }

    public static double[] getMinMax(double[] values) {
        double min = values[0];
        double max = values[0];

        for (double v : values) {
            if (v < min)
                min = v;
            if (v > max)
                max = v;
        }
        return new double[] {min, max};
    }

    public static double[] normalizeSpeeds(double[] speeds, double maxSpeed, double minSpeed) {
        double[] retSpeeds = speeds;

        double[] minMax = getMinMax(speeds);
        double divSpeed = Math.abs(minMax[0]) > minMax[1] ? Math.abs(minMax[0]) : minMax[1];
        double highestSpeed = minMax[1] > maxSpeed ? maxSpeed : minMax[1];
        double lowestSpeed = minMax[0] < minSpeed ? minSpeed : minMax[0];

        for (int i = 0; i < speeds.length; i++) {
            if (minMax[1] > maxSpeed && speeds[i] > 0)
                retSpeeds[i] = speeds[i] / divSpeed * highestSpeed;
            else if (minMax[0] < minSpeed && speeds[i] < 0)
                retSpeeds[i] = speeds[i] / -divSpeed * lowestSpeed;
        }
        return retSpeeds;
    }

    public static double optimize(double desiredAngle, double currentAngle) {
        if (Math.abs(desiredAngle - currentAngle) > 90 && Math.abs(desiredAngle - currentAngle) < 270)
            return -1;
        return 1;
    }

    public static double getCurrentTime() {
        return WPIUtilJNI.now() * 1.0e-6;
    }

    public static double metersToSwos(double meters) {
        return meters * 3.69230769231;
    }

    public static double swosToMeters(double swos) {
        return swos * 0.27083333333;
    }

    public static double wrapAroundAngles(double angle) {
        return angle < 0 ? 360 + angle : angle;
    }
}