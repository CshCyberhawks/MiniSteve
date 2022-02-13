package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.FieldPosition;

public class AutoGoTo extends CommandBase {
    private FieldPosition desiredPosition;

    public AutoGoTo(FieldPosition _desiredPosition) {
        desiredPosition = _desiredPosition;
    }

    // this command will move the robot to the desired position x, y and twist
    // values
    // it does this by first driving full speed in the direction of the desired x
    // and y position, and then it will stop and twist until it reaches desired
    // angle

    @Override
    public void initialize() {
        // desired position = x in swos (6 inches), y in swos, and twist in degrees
        // (based on
        // robot staring position)
        Robot.swerveAuto.setDesiredPosition(desiredPosition);
    }

    @Override
    public void execute() {
        Robot.swerveAuto.drive();
        SmartDashboard.putBoolean("auto command finsihed", Robot.swerveAuto.isAtDesiredPosAng());
    }

    @Override
    public void end(boolean interrupted) {
        Robot.swerveAuto.kill();
    }

    @Override
    public boolean isFinished() {
        return Robot.swerveAuto.isAtDesiredPosAng();
    }

}