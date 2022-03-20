package frc.robot.util;

public class Polar {
    public double theta;
    public double r;

    public Polar() {
        this.theta = 0;
        this.r = 0;
    }

    public Polar(double theta, double r) {
        this.theta = theta;
        this.r = r;
    }

    public boolean equals(Polar other) {
        return (theta == other.theta && r == other.r);
    }

    public Polar add(Polar other) {
        return new Polar(theta + other.theta, r + other.r);
    }
}
