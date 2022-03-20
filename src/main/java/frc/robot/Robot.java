// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import frc.robot.commands.ManualIntakeCommand;
import frc.robot.commands.ManualTransportCommand;
import frc.robot.commands.ShootCommand;

import frc.robot.subsystems.IntakeSystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShootSystem;
import frc.robot.subsystems.TransportSystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutoCommandGroup;
import frc.robot.commands.SwerveCommand;
import frc.robot.subsystems.SwerveAuto;
import frc.robot.subsystems.SwerveDriveTrain;
//import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.SwerveOdometry;
import frc.robot.util.FieldPosition;
import frc.robot.util.Gyro;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    public static SwerveAuto swerveAuto;
    public static HttpCamera limelightFeed;
    public static SwerveDriveTrain swerveSystem;
    public static SwerveOdometry swo;
    public static SwerveCommand swerveCommand;
    // public Alliance teamColor;
    // public OldSwerveDriveTrain swerveSystem;
    // public OldSwerveDriveTrain swerveSystem;
    // public SwerveDriveTrain swerveSystem;
    public static ShootSystem shootSystem;
    public static DigitalInput frontBreakBeam;
    public static DigitalInput backBreakBeam;
    public static DigitalInput topBreakBeam;
    public static DigitalInput shootBreakBeam;

    // public OldSwerveDriveTrain swerveSystem;
    // public SwerveDriveTrain swerveSystem;
    public static IntakeSystem intakeSystem;
    public static TransportSystem transportSystem;

    public static AutoCommandGroup autoCommands;
    // public RobotContainer m_robotContainer;

    /**
     * This function is run when the robot is first started up and should be used
     * for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        limelightFeed = new HttpCamera("limelight", "http://10.28.75.11:5800");
        // CameraServer.startAutomaticCapture(limelightFeed);

        ShuffleboardTab drivShuffleboardTab = Shuffleboard.getTab("DriverStream");
        drivShuffleboardTab.add("LL", limelightFeed).withPosition(0, 0).withSize(15, 8)
                .withProperties(Map.of("Show Crosshair", true, "Show Controls", false));
        // Instantiate our RobotContainer. This will perform all our button bindings,
        // and put our
        // autonomous chooser on the dashboard.
        // teamColor = DriverStation.getAlliance();
        // m_robotContainer = new RobotContainer();
        // PortForwarder.add(5800, "limelight.local", 5800);
        frontBreakBeam = new DigitalInput(Constants.frontBreakBeam);
        backBreakBeam = new DigitalInput(Constants.backBreakBeam);
        topBreakBeam = new DigitalInput(Constants.topBreakBeam);
        shootBreakBeam = new DigitalInput(Constants.shootBreakBeam);
        shootSystem = new ShootSystem();
        intakeSystem = new IntakeSystem();
        transportSystem = new TransportSystem();

        swerveSystem = new SwerveDriveTrain();
        swo = new SwerveOdometry(new FieldPosition(0, 0, 0));
        CameraServer.startAutomaticCapture();

        // driveSystem = new DriveSystem();
        // CameraServer.startAutomaticCapture();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and
     * test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler. This is responsible for polling buttons, adding
        // newly-scheduled
        // commands, running already-scheduled commands, removing finished or
        // interrupted commands,
        // and running subsystem periodic() methods. This must be called from the
        // robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {
        swo.resetPos();
        // swerveSystem.resetPredictedOdometry();
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected by your
     * {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        if (swerveCommand != null) {
            swerveCommand.cancel();
        }
        Limelight.pipelineInit();

        // m_autonomousCommand = m_robotContainer.getAutonomousCommand();
        swerveAuto = new SwerveAuto();
        autoCommands = new AutoCommandGroup(0);

        // schedule the autonomous command (example)
        autoCommands.schedule();

    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        swo.updatePosition();

    }

    @Override
    public void teleopInit() {

        shootSystem.setDefaultCommand(new ShootCommand(shootSystem));
        intakeSystem.setDefaultCommand(new ManualIntakeCommand(intakeSystem));
        transportSystem.setDefaultCommand(new ManualTransportCommand(transportSystem));

        swerveCommand = new SwerveCommand(swerveSystem);
        swerveCommand.schedule();
        Limelight.pipelineInit();

        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        // if (autoCommands != null) {
        // autoCommands.cancel();
        // }
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        swo.updatePosition();

        SmartDashboard.putNumber("cargoStored", transportSystem.getCargoAmount());

        SmartDashboard.putBoolean("frontBreakBeam", frontBreakBeam.get());
        SmartDashboard.putBoolean("backBreakBeam", backBreakBeam.get());
        SmartDashboard.putBoolean("topBreakBeam", topBreakBeam.get());
        SmartDashboard.putBoolean("shootBreakBeam", shootBreakBeam.get());

    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();

    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {

    }

    public static IntakeSystem getIntakeSystem() {
        return intakeSystem;
    }

    public static ShootSystem getShootSystem() {
        return shootSystem;
    }

    public static TransportSystem getTransportSystem() {
        return transportSystem;
    }

    public static DigitalInput getFrontBreakBeam() {
        return frontBreakBeam;
    }

    public static DigitalInput getBackBreakBeam() {
        return backBreakBeam;
    }

    public static DigitalInput getTopBreakBeam() {
        return topBreakBeam;
    }

    public static DigitalInput getShootBreakBeam() {
        return shootBreakBeam;
    }
}