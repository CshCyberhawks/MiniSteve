package frc.robot.util;

public class FieldPosition {
    private static Vector2 positionCoord;
    private static Gyro gyro;

    public FieldPosition() {
        gyro = new Gyro();
        positionCoord = new Vector2();
    }

    public static void update() {
        positionCoord.setX(positionCoord.getX() + gyro.getVelX());
        positionCoord.setY(positionCoord.getY() + gyro.getVelY());
    }
}