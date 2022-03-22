package frc.robot.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IO {
    private static final Joystick joystick = new Joystick(0);
    private static final Joystick joystick2 = new Joystick(1);
    private static final XboxController xbox = new XboxController(2);
    private static final double controllerDeadzone = 0.3;

    public static boolean hosas = false;

    public static double[] getPolarCoords() {
        return new double[] {
                -MathClass.calculateDeadzone(joystick.getDirectionDegrees(), controllerDeadzone),
                MathClass.calculateDeadzone(joystick.getMagnitude(), controllerDeadzone),
                MathClass.calculateDeadzone(joystick.getTwist(), controllerDeadzone)
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

    public static boolean deployClimbSolenoid() {
        return xbox.getAButtonPressed();
    }

    public static boolean getAutoIntakeCancel() {
        return xbox.getStartButtonPressed() || intakeBall() > 0;
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
        SmartDashboard.putNumber("Jotstick X", joystick.getY());
        return MathClass.calculateDeadzone(joystick.getY(), controllerDeadzone);
    }

    public static double moveRobotY() {
        SmartDashboard.putNumber("Joystick Y", joystick.getX());
        return MathClass.calculateDeadzone(joystick.getX(), controllerDeadzone);
    }

    public static boolean removeBall() {
        return xbox.getBackButton();
    }

    public static boolean getAutoShootCancel() {
        return xbox.getStartButton() || shootBall() > 0;
    }

    public static boolean resetGyro() {
        return joystick.getRawButtonPressed(8);
    }

    public static double shootBall() {
        return xbox.getRightTriggerAxis();
    }

    public static double turnControl() {
        return hosas ? MathClass.calculateDeadzone(joystick2.getX(), controllerDeadzone)
                : MathClass.calculateDeadzone(joystick.getTwist(), .1);
    }

    public static boolean getSWOReset() {
        return joystick.getRawButton(7);
    }

    public static double getJoyThrottle() {
        return hosas ? MathClass.calculateDeadzone(joystick2.getY(), .5)
                : MathClass.calculateDeadzone((-joystick.getThrottle() + 1) / 2, .05);
    }

    public static boolean getResetCargo() {
        return xbox.getBButtonPressed();
    }

    public static boolean raiseBottomShootSpeed() {
        return joystick.getRawButtonPressed(5);
    }

    public static boolean lowerBottomShootSpeed() {
        return joystick.getRawButtonPressed(10);
    }

    public static boolean raiseShootSpeed() {
        return joystick.getRawButtonPressed(6);
    }

    public static boolean lowerShootSpeed() {
        return joystick.getRawButtonPressed(9);
    }

    // public static boolean getXboxRightBumper() {
    // return xbox.getRightBumper();
    // }

    // public static boolean getXboxLeftBumper() {
    // return xbox.getLeftBumper();
    // }

    // public static double getXboxLeftX() {
    // return Math.abs(xbox.getLeftX()) > controllerMathClass.calculateDeadzone ?
    // xbox.getLeftX() : 0;
    // }

    // public static double getXboxRightX() {
    // return Math.abs(xbox.getRightX()) > controllerMathClass.calculateDeadzone ?
    // xbox.getRightX() :
    // 0;
    // }

    // public static double getJoyTwist() {
    // SmartDashboard.putNumber("Joystick Twist", joystick.getTwist());
    // return MathClass.calculateDeadzone(joystick.getTwist(),
    // controllerMathClass.calculateDeadzone);
    // }
}