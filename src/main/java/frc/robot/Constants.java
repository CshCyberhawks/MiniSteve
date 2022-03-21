// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;
// import java.util.Map;

import frc.robot.util.FieldPosition;
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
                        put("frontRight", .6);
                        put("frontLeft", .6);
                        put("backRight", .6);
                        put("backLeft", .6);
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
        public static final int frontRightTurnMotor = 1;
        public static final int frontLeftTurnMotor = 3;
        public static final int backRightTurnMotor = 5;
        public static final int backLeftTurnMotor = 2;

        // Falcon Motors
        public static final int frontRightDriveMotor = 9;
        public static final int frontLeftDriveMotor = 7;
        public static final int backRightDriveMotor = 6;
        public static final int backLeftDriveMotor = 8;

        // Shoot System Motors
        public static final int rightShootMotor = 11;
        public static final int leftShootMotor = 13;
        public static final int topShootMotor = 12;
        public static final int traversalMotor = 16;

        // Intake System
        public static final int intakeMotor = 15;
        public static final int intakeSolenoid = 4;

        // Climb System
        public static final int climbMotor = 10;

        // Dimensions
        public static final double length = .53;
        public static final double width = .53;

        // Capacities
        public static final double maxVolts = 4.85;

        public static final double wheelRadius = 0.0505; // In meters
        public static final double wheelGearRatio = 7 / 1; // 7:1 gear ratio

        public static final Vector2[] redBallPositions = { new Vector2(-2.2, 3.2), new Vector2(0.7, 3.8),
                        new Vector2(3.2, 2.2),
                        new Vector2(3.3, -2.1), new Vector2(0.9, -3.8), new Vector2(-3.8, -0.9) };
        public static final Vector2[] blueBallPositions = { new Vector2(-3.3, 2.1), new Vector2(-0.9, 3.8),
                        new Vector2(3.8, 0.9),
                        new Vector2(2.3, -3.2), new Vector2(-0.6, -3.8), new Vector2(-3.2, -2.2) };

        public static final Vector2[] blueShootingPositions = {
                        new Vector2(0, 0), new Vector2(0, 0)
        };

        public static final Vector2[] redShootingPositions = {
                        new Vector2(0, 0), new Vector2(0, 0)
        };

        public static final FieldPosition[] blueStartingPositions = {
                        new FieldPosition(-0.3, -1.7, 0), new FieldPosition(-2.8, 1, 0)
        };

        public static final FieldPosition[] redStartingPositions = {
                        new FieldPosition(1.7, 0.3, 0), new FieldPosition(2.8, -1, 0)
        };

        // Break Beams
        public static final int frontBreakBeam = 5;
        public static final int backBreakBeam = 4;
        public static final int topBreakBeam = 6;
        public static final int shootBreakBeam = 7;

        // Encoders
        public static final int frontRightEncoder = 0;
        public static final int frontLeftEncoder = 1;
        public static final int backRightEncoder = 3;
        public static final int backLeftEncoder = 2;

        public static final double[] turnEncoderOffsets = { 293.906219904, 79.013663784, 71.367180192, 101.337880248 };
}