package frc.robot.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IO {
    private static final Joystick joystick = new Joystick(0);
    private static final Joystick joystick2 = new Joystick(1);
    private static final XboxController xbox = new XboxController(2);
    private static final double controllerDeadzone = 0.3;

    public static double deadzone(double input, double zone) {
        return Math.abs(input) > zone ? input : 0;
    }

    public static double[] getPolarCoords() {
        return new double[] {
                -deadzone(joystick.getDirectionDegrees(), controllerDeadzone),
                deadzone(joystick.getMagnitude(), controllerDeadzone),
                deadzone(joystick.getTwist(), controllerDeadzone)
        };
    }

    public static boolean autoIntake() {
        return xbox.getYButton();
    }

    public static boolean autoShoot() {
        return xbox.getXButtonPressed();
    }

    public static double climbControl() {
        return xbox.getRightY();
    }

    public static boolean deployPneumatics() {
        return xbox.getAButton();
    }

    public static boolean getXboxStartButton() {
        return xbox.getStartButtonPressed();
    }

    public static double intakeBall() {
        return xbox.getLeftTriggerAxis();
    }

    public static boolean limelightLockOn() {
        return joystick.getRawButton(3);
    }

    public static double moveTransport() {
        return Math.abs(xbox.getLeftY()) > controllerDeadzone ? xbox.getLeftY() : 0;
    }

    public static double moveRobotX() {
        SmartDashboard.putNumber("Jotstick X", joystick.getX());
        return deadzone(joystick.getX(), controllerDeadzone);
    }

    public static double moveRobotY() {
        SmartDashboard.putNumber("Joystick Y", joystick.getY());
        return deadzone(joystick.getY(), controllerDeadzone);
    }

    public static boolean removeBall() {
        return xbox.getBackButton();
    }

    public static boolean resetGyro() {
        return joystick.getRawButtonPressed(8);
    }

    public static double shootBall() {
        return xbox.getRightTriggerAxis();
    }

    public static double turnControl() {
        return deadzone(joystick2.getX(), controllerDeadzone);
    }

    public static boolean getJoystickButton5() {
        return joystick.getRawButton(5);
    }

    public static double getJoyThrottle() {
        return deadzone(joystick2.getY(), .5);
    }

    // public static boolean getXboxRightBumper() {
    // return xbox.getRightBumper();
    // }

    // public static boolean getXboxLeftBumper() {
    // return xbox.getLeftBumper();
    // }

    // public static double getXboxLeftX() {
    // return Math.abs(xbox.getLeftX()) > controllerDeadzone ? xbox.getLeftX() : 0;
    // }

    // public static double getXboxRightX() {
    // return Math.abs(xbox.getRightX()) > controllerDeadzone ? xbox.getRightX() :
    // 0;
    // }

    // public static double getJoyTwist() {
    // SmartDashboard.putNumber("Joystick Twist", joystick.getTwist());
    // return deadzone(joystick.getTwist(), controllerDeadzone);
    // }
}
