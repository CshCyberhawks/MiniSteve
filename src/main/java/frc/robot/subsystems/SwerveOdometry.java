package frc.robot.subsystems;

import frc.robot.util.MathClass;
import frc.robot.util.FieldPosition;
import frc.robot.util.Gyro;
import frc.robot.util.IO;
import frc.robot.util.Vector2;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;

public class SwerveOdometry extends SubsystemBase{
    private FieldPosition fieldPosition;
    private SwerveDriveTrain swerveDriveTrain;
    private double movementTime;
    private FieldPosition desiredPosition;
    private double timeToDesiredPosition;

    public SwerveOdometry(FieldPosition _fieldPosition, SwerveDriveTrain _driveTrain) {
        swerveDriveTrain = _driveTrain;
        fieldPosition = _fieldPosition;
    }

    public FieldPosition getPosition() {
        return fieldPosition;
    }
    
    //time to desired position is in ms

    public void setDesiredPosition(FieldPosition _desiredPosition, double _timeToDesiredPosition) {
        desiredPosition = _desiredPosition;
        timeToDesiredPosition = _timeToDesiredPosition;

    }

    public void updatePosition() {
        fieldPosition.positionCoord.x += Gyro.getVelX();
        fieldPosition.positionCoord.y += Gyro.getVelY();

        fieldPosition.angle = Gyro.getAngle();


    }

    //swo is the main loop
    //subtracting 20 from time cuz thats the periodic loop interval
    public void swo() {
        
        updatePosition();

        double inputTwist = 0;//(desiredAngle - fieldPosition.angle) / timeToDesiredPosition;

        

        double[] inputs = calculateInputs();



        //need to figure out how to make those inputs AHHHHH
        swerveDriveTrain.drive(inputs[0], inputs[1], inputTwist);

        timeToDesiredPosition -= 20;
    }
    
    private double[] calculateInputs() {

        TrapezoidProfile trapProfileX = new TrapezoidProfile(new TrapezoidProfile.Constraints(1, 1),
        new TrapezoidProfile.State(desiredPosition.positionCoord.x, 0), 
        new TrapezoidProfile.State(fieldPosition.positionCoord.x, 0));


        TrapezoidProfile trapProfileY = new TrapezoidProfile(new TrapezoidProfile.Constraints(1, 1), 
        new TrapezoidProfile.State(desiredPosition.positionCoord.y, 0), 
        new TrapezoidProfile.State(fieldPosition.positionCoord.y, 0));



        double inputX = trapProfileX.calculate(timeToDesiredPosition).velocity * MathClass.calculateDeadzone(MathUtil.clamp(desiredPosition.positionCoord.x - fieldPosition.positionCoord.x, -1, 1), .3);
        double inputY = trapProfileY.calculate(timeToDesiredPosition).velocity * MathClass.calculateDeadzone(MathUtil.clamp(desiredPosition.positionCoord.y - fieldPosition.positionCoord.y, -1, 1), .3);
        


        double[] ret = {inputX, inputY};
        return ret;
    }

}