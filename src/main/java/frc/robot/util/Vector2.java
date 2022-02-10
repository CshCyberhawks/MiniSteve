package frc.robot.util;

public class Vector2 {
    public double x;
    public double y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(double inputX, double inputY) {
        x = inputX;
        y = inputY;
    }

    public boolean equals(Vector2 other) {
        return (x == other.x && y == other.y);
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }
}