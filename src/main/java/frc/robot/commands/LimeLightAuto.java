package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.SwerveAuto;

public class LimeLightAuto extends CommandBase {

    SwerveAuto swerveAuto;
    boolean isAtAngle = false;
    boolean isAtPosition = false;
    boolean pickedUpBall = false;
    boolean firstTimeAtAngle = false;
    // IntakeSequence intakeSequence;

    public LimeLightAuto() {
        // Use addRequirements() here to declare subsystem dependencies.
        swerveAuto = Robot.swerveAuto;
        swerveAuto.setDesiredAngle(LimeLight.getHorizontalOffset(), true);

    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("limeLightDistance", LimeLight.getDistance());

        if (!isAtAngle) {
            swerveAuto.twist();
            return;
        }

        else if (isAtAngle && !isAtPosition && firstTimeAtAngle) {
            // intakeSequence = new IntakeSequence();
            swerveAuto.setDesiredPosition(LimeLight.getDistance());
        }

        else if (!isAtPosition && isAtAngle) {
            swerveAuto.translate();
        }
    }

    @Override
    public void end(boolean interrupted) {
        Robot.swerveAuto.kill();
    }

    @Override
    public boolean isFinished() {
        if (!isAtAngle) {
            isAtAngle = swerveAuto.isAtDesiredAngle();
            firstTimeAtAngle = isAtAngle ? true : false;
        } else if (isAtAngle && !isAtPosition) {
            isAtPosition = swerveAuto.isAtDesiredPosition();
        }

        // pickedUpBall = IntakeSequence.isFinished();

        return pickedUpBall;
    }
}
