package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.MathClass;
import edu.wpi.first.util.WPIUtilJNI;


public class Wait extends CommandBase {
    double startTime;
    private double length;
    //length is in MS
    public Wait(double _length) {
        length = _length;
    }

    @Override
    public void initialize() {
        startTime = WPIUtilJNI.now() * 1.0e-6;
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override public boolean isFinished() {
        double timeNow = WPIUtilJNI.now() * 1.0e-6;
        return MathClass.calculateDeadzone((timeNow - startTime) - length, .05) == 0;
    }

}

