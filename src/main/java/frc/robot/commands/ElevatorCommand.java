package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ElevatorCommand extends CommandBase {
    private int ball;

    public ElevatorCommand(int ball) {
        this.ball = ball;
    }

    @Override
    public void execute() {
        //run elevator
        Robot.getShootSystem().traverse(.5);
    }

    @Override 
    public void end(boolean interrupted) {
        //sets autoClassWhatever ball number = opposite of one given
        //kills traverse
        if (ball == 1)
            ball = 2;
        else
            ball = 1;
        Robot.getShootSystem().traverse(0);
    }

    @Override
    public boolean isFinished() {
        //return true on 2nd breakbeam if ball == 1, return true on 3rd breakbeam if ball == 2
        if (ball == 1) 
            return !Robot.getBackBreakBeam().get();
        return !Robot.getTopBreakBeam().get();
    }

}

