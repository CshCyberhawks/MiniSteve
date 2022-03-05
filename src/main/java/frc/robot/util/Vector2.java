package frc.robot.util;

public class Vector2 {
    private double x;
    private double y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(double inputX, double inputY) {
        x = inputX;
        y = inputY;
    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean equals(Vector2 other) {
        return (x == other.x && y == other.y);
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.getX(), y + other.getY());
    }

    public void addSelf(Vector2 other) {
        x += other.getX();
        y += other.getY();
    }
}