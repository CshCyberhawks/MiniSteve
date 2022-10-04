// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import org.ejml.dense.fixed.CommonOps_DDF2;

import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutoCommandGroup;
import frc.robot.commands.SwerveCommand;
import frc.robot.subsystems.SwerveAuto;
import frc.robot.subsystems.SwerveDriveTrain;
//import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.SwerveOdometry;
import frc.robot.util.FieldPosition;
import frc.robot.util.Gyro;
import frc.robot.util.IO;

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

  // private OldSwerveDriveTrain swerveSystem;
  public static SwerveAuto swerveAuto;
  public static SwerveDriveTrain swerveSystem;
  public static SwerveOdometry swo;
  private static SwerveCommand swerveCommand;

  private Command autoCommands;

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
    // m_robotContainer = new RobotContainer();

    // driveSystem = new DriveSystem();
    // CameraServer.startAutomaticCapture();
    // swerveSystem = new SwerveDriveTrain();
    // swo = new SwerveOdometry(new FieldPosition(0, 0, 0), swerveSystem);

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
    // if (swerveCommand != null) {
    // swerveCommand.cancel();
    // }
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    // swerveAuto = new SwerveAuto();
    // autoCommands = new AutoCommandGroup();

    // schedule the autonomous command (example)
    // autoCommands.schedule();

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // swo.updatePosition();

  }

  @Override
  public void teleopInit() {

    // swerveCommand = new SwerveCommand(swerveSystem);
    // swerveCommand.schedule();

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
    // swo.updatePosition();

  }

  Orchestra orchestra;
  TalonFX falcon;
  String[] songs = new String[] {
      "africa",
      "imperialmarch"
  };
  double freq = 500;

  private void loadSong(int idx) {
    orchestra.loadMusic("music/" + songs[idx] + ".chrp");
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();

    falcon = new TalonFX(4);

    ArrayList<TalonFX> instruments = new ArrayList<>(Arrays.asList(falcon));

    orchestra = new Orchestra(instruments);

    loadSong(0);

    orchestra.play();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    // if (IO.getXboxUp())\
    // freq += 2;
    // if (IO.getXboxDown())
    // freq -= 2;

    // SmartDashboard.putNumber("Frequency", freq);
    // falcon.set(ControlMode.MusicTone, freq);
  }
}
