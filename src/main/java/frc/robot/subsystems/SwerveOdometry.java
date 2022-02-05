package frc.robot.subsystems;

import frc.robot.util.MathClass;
import frc.robot.util.FieldPosition;
import frc.robot.util.Gyro;
import frc.robot.util.IO;
import frc.robot.util.Vector2;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Vector;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class SwerveOdometry extends SubsystemBase{
    private FieldPosition fieldPosition;
    private SwerveDriveTrain swerveDriveTrain;
    private FieldPosition desiredPosition;

    private double[] inputs;

    public SwerveOdometry(FieldPosition _fieldPosition, SwerveDriveTrain _driveTrain) {
        swerveDriveTrain = _driveTrain;
        fieldPosition = _fieldPosition;
    }

    public FieldPosition getPosition() {
        return fieldPosition;
    }
    
    //time to desired position is in ms

    public void setDesiredPosition(FieldPosition _desiredPosition) {
        desiredPosition = _desiredPosition;

        inputs = calculateInputs();
    }

    public void resetPos() {
        fieldPosition.reset();
        Gyro.resetDispacement();
    }

    public void updatePosition() {
        fieldPosition.positionCoord.x = Gyro.getDisplacementX();
        fieldPosition.positionCoord.y = Gyro.getDisplacementY();

        fieldPosition.angle = Gyro.getAngle();


    }

    //swo is the main loop
    //subtracting 20 from time cuz thats the periodic loop interval
    public void swo() {
        
        updatePosition();

        
        SmartDashboard.putNumber(" fieldPosX ", fieldPosition.positionCoord.x);
        SmartDashboard.putNumber(" fieldPosY ", fieldPosition.positionCoord.y);

        SmartDashboard.putNumber(" gyroXDis ", Gyro.getDisplacementX());


        //need to figure out how to make those inputs AHHHHH
        swerveDriveTrain.backRight.drive(inputs[1], inputs[0]);
        swerveDriveTrain.backLeft.drive(-inputs[1], inputs[0]);
        swerveDriveTrain.frontRight.drive(inputs[1], inputs[0]);
        swerveDriveTrain.frontLeft.drive(-inputs[1], inputs[0]);

    }
    
    private double[] calculateInputs() {

        TrapezoidProfile trapProfileX = new TrapezoidProfile(new TrapezoidProfile.Constraints(1, 1),
        new TrapezoidProfile.State(desiredPosition.positionCoord.x, 0), 
        new TrapezoidProfile.State(fieldPosition.positionCoord.x, 0));


        TrapezoidProfile trapProfileY = new TrapezoidProfile(new TrapezoidProfile.Constraints(1, 1), 
        new TrapezoidProfile.State(desiredPosition.positionCoord.y, 0), 
        new TrapezoidProfile.State(fieldPosition.positionCoord.y, 0));

        double[] polarCurrent = swerveDriveTrain.cartesianToPolar(fieldPosition.positionCoord.x, fieldPosition.positionCoord.y);

        double[] polarDesired = swerveDriveTrain.cartesianToPolar(desiredPosition.positionCoord.x, desiredPosition.positionCoord.y);

        double thetaDifference = polarDesired[0] - polarCurrent[0];



        SmartDashboard.putNumber(" auto theta ", thetaDifference);

        double[] ret = {thetaDifference, 1};
        return ret;
    }

    public boolean isAtPosition() {
        if (MathClass.calculateDeadzone(desiredPosition.positionCoord.x - fieldPosition.positionCoord.x, 2) == 0) {
            if (MathClass.calculateDeadzone(desiredPosition.positionCoord.y - fieldPosition.positionCoord.y, 2) == 0) {
                return true;
            }
        }
        return false;
    }

}