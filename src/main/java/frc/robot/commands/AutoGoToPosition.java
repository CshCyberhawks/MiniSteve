package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.FieldPosition;
import frc.robot.util.Vector2;

public class AutoGoToPosition extends CommandBase {
    private Vector2 desiredPosition;
    private double desiredVelocity;

    public AutoGoToPosition(Vector2 desiredPosition, double desiredVelocity) {
        this.desiredPosition = desiredPosition;
        this.desiredVelocity = desiredVelocity;
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
        Robot.swerveAuto.setDesiredPosition(desiredPosition, desiredVelocity);
    }

    @Override
    public void execute() {
        Robot.swerveAuto.translate();
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("auto translate command finsihed", Robot.swerveAuto.isAtDesiredPosition());
        Robot.swerveAuto.kill();
    }

    @Override
    public boolean isFinished() {
        return Robot.swerveAuto.isAtDesiredPosition();
    }

}