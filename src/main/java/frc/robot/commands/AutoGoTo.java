package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.SwerveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.FieldPosition;
import frc.robot.util.Vector2;

public class AutoGoTo extends CommandBase{
    private FieldPosition desiredPosition;
    private boolean isMoveFinished = false;
    private boolean isTwistFinished = false;

    //this command will move the robot to the desired position x, y and twist values
    //it does this by first driving full speed in the direction of the desired x and y position, and then it will stop and twist until it reaches desired angle

    @Override
    public void initialize() {
        // Robot.swo.resetPos();
        //desired position = x in meters, y in meters, and twist in degrees (based on robot staring position)
        desiredPosition = new FieldPosition(0, 1.5, 0);
        Robot.swerveAuto.setDesiredPosition(desiredPosition);
    }

    @Override
    public void execute() {
        if(!isMoveFinished) Robot.swerveAuto.drive();
        else Robot.swerveAuto.twist();
        System.out.println("auto command execute ran");
    }

    @Override
    public void end(boolean interrupted) {
        Robot.swerveAuto.kill();
        SmartDashboard.putBoolean("auto command finsihed", true);
    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean(" isAtPos ", Robot.swerveAuto.isAtDesiredPosition());
        isMoveFinished = Robot.swerveAuto.isAtDesiredPosition();
        isTwistFinished = Robot.swerveAuto.isAtDesiredAngle();
        return isTwistFinished;
    }


}