package frc.robot.subsystems;

import frc.robot.util.FieldPosition;
import frc.robot.util.Vector2;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;

public class SwerveOdometry extends SubsystemBase{
    private FieldPosition fieldPosition;
    private SwerveDriveTrain swerveDriveTrain;
    private double movementTime;
    private Vector2 desiredPosition;
    private double timeToDesiredPosition;



    public SwerveOdometry(double startX, double startY, double startAngle, SwerveDriveTrain _driveTrain) {
        swerveDriveTrain = _driveTrain;
        fieldPosition.spawn(startX, startY, startAngle);
    }

    public FieldPosition getPosition() {
        return fieldPosition;
    }
    
    //time to desired position is in ms

    public void setDesiredPosition(Vector2 _desiredPosition, double _timeToDesiredPosition) {
        desiredPosition = _desiredPosition;
        timeToDesiredPosition = _timeToDesiredPosition;


    }


    //swo is the main loop
    //subtracting 20 from time cuz thats the periodic loop interval
    public void swo() {
        fieldPosition.update();

        double inputTwist = 0;//(desiredPosition.angle - fieldPosition.angle) / timeToDesiredPosition;

        
        TrapezoidProfile trapProfileAngle = new TrapezoidProfile(new TrapezoidProfile.Constraints(10, 10), new TrapezoidProfile.State(0, 10));

        double[] inputs = calculateInputs();



        //need to figure out how to make those inputs AHHHHH
        swerveDriveTrain.drive(inputs[0], inputs[1], inputTwist);

        timeToDesiredPosition -= 20;
    }
    
    private double[] calculateInputs() {
        double[] ret = {MathUtil.clamp(desiredPosition.x - fieldPosition.positionCoord.x, -1, 1), MathUtil.clamp(desiredPosition.y - fieldPosition.positionCoord.y, -1, 1)};
        return ret;
    }

}