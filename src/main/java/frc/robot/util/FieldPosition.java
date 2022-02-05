package frc.robot.util;
import java.util.HashMap;
import frc.robot.util.Vector2;

public class FieldPosition {
    public static double angle = 0;
    public static Vector2 positionCoord;

    private static Gyro gyro = new Gyro();

    public static void spawn(double startX, double startY, double startAngle) {
        positionCoord = new Vector2(startX, startY);
        angle = startAngle;
    }

    public static void update() {
        positionCoord.x += gyro.getVelX();
        positionCoord.y += gyro.getVelY();

        angle = gyro.getAngle();


    }


}
