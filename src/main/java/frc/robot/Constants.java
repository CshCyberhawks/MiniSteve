// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;

import frc.robot.util.Vector2;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int frontRightTwistMult = -1;
    public static final int frontLeftTwistMult = 1;
    public static final int backRightTwistMult = -1;
    public static final int backLeftTwistMult = 1;

    // twist speed mults for each motor
    public static final HashMap<String, Double> twistSpeedMap = new HashMap<>() {
        {
            put("frontRight", .5);
            put("frontLeft", .5);
            put("backRight", .5);
            put("backLeft", .5);
        }
    };

    // twist angles for each motor
    public static final HashMap<String, Integer> twistAngleMap = new HashMap<>() {
        {
            put("frontRight", 45);
            put("frontLeft", 135);
            put("backRight", -45);
            put("backLeft", -135);
        }
    };

    // TalonSRX Motors
    public static final int frontRightTurnMotor = 5;
    public static final int frontLeftTurnMotor = 2;
    public static final int backRightTurnMotor = 1;
    public static final int backLeftTurnMotor = 3;

    // CANSparkMax Motors
    public static final int frontRightDriveMotor = 6;
    public static final int frontLeftDriveMotor = 8;
    public static final int backRightDriveMotor = 9;
    public static final int backLeftDriveMotor = 7;

    // Dimensions
    public static final double length = .53;
    public static final double width = .53;

    // Capacities
    public static final double maxVolts = 4.85;

    // Encoders
    public static final int frontRightEncoder = 0;
    public static final int frontLeftEncoder = 3;
    public static final int backRightEncoder = 1;
    public static final int backLeftEncoder = 2;

    public static final double[] turnEncoderOffsets = { 15.3, 269.7, 346.8, 159.3 };

    public static final double wheelRadius = 0.0505; // In meters
    public static final double wheelGearRatio = 7 / 1; // 7:1 gear ratio

    public static final Vector2[] redBallPositions = { new Vector2(5, 0), new Vector2(0, 0), new Vector2(0, 0),
            new Vector2(0, 0), new Vector2(0, 0) };
    public static final Vector2[] blueBallPositions = { new Vector2(0, 5), new Vector2(0, 0), new Vector2(0, 0),
            new Vector2(0, 0), new Vector2(0, 0) };

}
