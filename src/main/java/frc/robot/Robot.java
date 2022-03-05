// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShootSystem;
// import frc.robot.subsystems.SwerveDriveTrain;
import frc.robot.subsystems.IntakeSystem;
// import frc.robot.subsystems.Limelight;
import frc.robot.commands.SwerveCommand;
import frc.robot.subsystems.SwerveDriveTrain;
import edu.wpi.first.cameraserver.CameraServer;
//import frc.robot.subsystems.SwerveSubsystem;
// import frc.robot.util.IO;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // private DriveSystem driveSystem;
  private Command m_autonomousCommand;
  private ShootSystem shootSystem;
  //private OldSwerveDriveTrain swerveSystem;
  private SwerveDriveTrain swerveSystem;

  // private Limelight limelight;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    CameraServer.startAutomaticCapture();
    // m_robotContainer = new RobotContainer();
    shootSystem = new ShootSystem();
    CameraServer.startAutomaticCapture();
    swerveSystem = new SwerveDriveTrain();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    //m_autonomousCommand = m_robotContainer.getAutonomousCommand();

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
    //swerveSystem.setDefaultCommand(new OldSwerveCommand(swerveSystem));
    swerveSystem.setDefaultCommand(new SwerveCommand(swerveSystem));

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null)
      m_autonomousCommand.cancel();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  // private CANSparkMax motor = new CANSparkMax(12, MotorType.kBrushless);

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
