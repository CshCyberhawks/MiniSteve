package frc.robot.util;

import frc.robot.util.Vector2;

public class PolarCoordinate {
    public double theta;
    public double r;

    public PolarCoordinate(double theta, double r) {
        this.theta = theta;
        this.r = r;
    }

    public PolarCoordinate(Vector2 cartCoord) {
        double[] polarCoord = MathClass.cartesianToPolar(cartCoord.x, cartCoord.y);
        this.theta = polarCoord[0];
        this.r = polarCoord[1];
    }
}