package frc.robot.subsystems;

import frc.robot.util.FieldPosition;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class SwerveOdometry {
    private FieldPosition fieldPosition;
    private SwerveDriveTrain swerveDriveTrain;
    private double movementTime;


    public void SwerveOdometry(double startX, double startY, double startAngle, SwerveDriveTrain _driveTrain) {
        swerveDriveTrain = _driveTrain;
        fieldPosition.spawn(startX, startY, startAngle);
    }

    public FieldPosition getPosition() {
        return fieldPosition;
    }

    //swo is the main loop
    public void swo(FieldPosition desiredPosition, double timeToDesiredPosition) {
        fieldPosition.update();


        
        TrapezoidProfile trapProfileAngle = new TrapezoidProfile(new TrapezoidProfile.Constraints(10, 10), new TrapezoidProfile.State(0, 10));



        //need to figure out how to make those inputs AHHHHH
        swerveDriveTrain.drive(inputX, inputY, inputTwist);

    }


}