package frc.robot.util;
import java.util.HashMap;
import frc.robot.util.Vector2;

public class FieldPosition {
    private static double angle = 0;
    private static Vector2 positionCoord = new Vector2();

    private static Gyro gyro = new Gyro();

    public static void update() {

        positionCoord.x += gyro.getVelX();
        positionCoord.y += gyro.getVelY();

        


    }


}
