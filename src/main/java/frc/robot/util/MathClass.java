package frc.robot.util;

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
}
