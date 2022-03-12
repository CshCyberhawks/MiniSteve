// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import java.util.HashMap;
// import java.util.Map;

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
    public static final HashMap<String, Integer> twistSpeedMap = new HashMap<>() {
        {
            put("frontRight", -1);
            put("frontLeft", 1);
            put("backRight", -1);
            put("backLeft", 1);
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

    // Turn Motors
    public static final int frontRightTurnMotor = 5;
    public static final int frontLeftTurnMotor = 2;
    public static final int backRightTurnMotor = 1;
    public static final int backLeftTurnMotor = 3;

    // Drive Motors
    public static final int frontRightDriveMotor = 6;
    public static final int frontLeftDriveMotor = 8;
    public static final int backRightDriveMotor = 9;
    public static final int backLeftDriveMotor = 7;

    // Shoot System Motors
    public static final int rightShootMotor = 13;
    public static final int leftShootMotor = 12;
    public static final int topShootMotor = 11;
    public static final int traversalMotor = 16;

    // Intake System
    public static final int intakeMotor = 15;
    public static final int intakeSolenoid = 0;

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

    // Break Beams
    public static final int frontBreakBeam = 1;
    public static final int backBreakBeam = 0;
    public static final int topBreakBeam = 2;

    public static final double[] turnEncoderOffsets = { 256, 346, 171, 95 };
}