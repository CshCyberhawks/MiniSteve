package frc.robot.util;

public class FieldPosition {
    private static Vector2 positionCoord = new Vector2();

    private static Gyro gyro = new Gyro();

    public static void update() {
        positionCoord.setX(positionCoord.getX() + gyro.getVelX());
        positionCoord.setY(positionCoord.getY() + gyro.getVelY());
    }
}
