package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoGoToAngle extends CommandBase {
    private double desiredAngle;

    public AutoGoToAngle(double desiredAngle) {
        this.desiredAngle = desiredAngle;
    }

    // this command will move the robot to the desired position x, y and twist
    // values
    // it does this by first driving full speed in the direction of the desired x
    // and y position, and then it will stop and twist until it reaches desired
    // angle

    @Override
    public void initialize() {
        // desired position = x in swos
        // (https://www.notion.so/Odometry-baacd114086e4218a5eedb5ef45a223f) (.27
        // meters), y in swos, and twist in degrees
        // (based on
        // robot staring position)
        Robot.swerveAuto.setDesiredAngle(desiredAngle, true);
    }

    @Override
    public void execute() {
        Robot.swerveAuto.twist();
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("auto angle command finsihed", Robot.swerveAuto.isAtDesiredAngle());
        Robot.swerveAuto.kill();
    }

    @Override
    public boolean isFinished() {
        return Robot.swerveAuto.isAtDesiredAngle();
    }

}