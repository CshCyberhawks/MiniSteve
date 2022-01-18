package frc.robot.util;
import edu.wpi.first.wpilibj.Joystick;


public class IO {
    private static Joystick joystick = new Joystick(0);

    public static double getJoystickForward() {
        return -joystick.getZ();
    }

    public static double getJoystickTurn() {
        return joystick.getY();
    }

}
