package frc.robot.util;
import edu.wpi.first.wpilibj.Joystick;


public class io {
    private static Joystick joystick = new Joystick(0);

    public static double getZ() {
        return joystick.getZ();
    }

    public static double getY() {
        return joystick.getY();
    }

}
