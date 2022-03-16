// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.ManualIntakeCommand;
import frc.robot.commands.ManualTransportCommand;
import frc.robot.commands.ShootCommand;

import frc.robot.subsystems.IntakeSystem;
import frc.robot.subsystems.ShootSystem;
import frc.robot.subsystems.TransportSystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.util.net.PortForwarder;
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
  // private DriveSystem driveSystem;
  private Command m_autonomousCommand;
  // private Alliance teamColor;
  // private OldSwerveDriveTrain swerveSystem;
  // private OldSwerveDriveTrain swerveSystem;
  // private SwerveDriveTrain swerveSystem;
  private static ShootSystem shootSystem;
  private static DigitalInput frontBreakBeam;
  private static DigitalInput backBreakBeam;
  private static DigitalInput topBreakBeam;

  // private OldSwerveDriveTrain swerveSystem;
  // private SwerveDriveTrain swerveSystem;
  private static IntakeSystem intakeSystem;
  private static TransportSystem transportSystem;
  // private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    // teamColor = DriverStation.getAlliance();
    // m_robotContainer = new RobotContainer();
    // PortForwarder.add(5800, "limelight.local", 5800);
    frontBreakBeam = new DigitalInput(Constants.frontBreakBeam);
    backBreakBeam = new DigitalInput(Constants.backBreakBeam);
    topBreakBeam = new DigitalInput(Constants.topBreakBeam);
    shootSystem = new ShootSystem();
    intakeSystem = new IntakeSystem();
    transportSystem = new TransportSystem();
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
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null)
      m_autonomousCommand.schedule();
  }


  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // swerveSystem = new SwerveDriveTrain();

    shootSystem.setDefaultCommand(new ShootCommand(shootSystem));
    intakeSystem.setDefaultCommand(new ManualIntakeCommand(intakeSystem));
    transportSystem.setDefaultCommand(new ManualTransportCommand(transportSystem));
    // swerveSystem.setDefaultCommand(new OldSwerveCommand(swerveSystem));

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null)
      m_autonomousCommand.cancel();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("cargoStored", transportSystem.getCargoAmount());
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

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
    SmartDashboard.putBoolean("frontBreakBeam", frontBreakBeam.get());
    return frontBreakBeam;
  }

  public static DigitalInput getBackBreakBeam() {
    return backBreakBeam;
  }

  public static DigitalInput getTopBreakBeam() {
    return topBreakBeam;
  }
}