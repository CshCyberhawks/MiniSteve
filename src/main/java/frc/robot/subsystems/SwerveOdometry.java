package frc.robot.subsystems;

import frc.robot.util.MathClass;
import frc.robot.Robot;
import frc.robot.util.FieldPosition;
import frc.robot.util.Gyro;
import frc.robot.util.IO;
import frc.robot.util.Vector2;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Vector;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.WPIUtilJNI;


public class SwerveOdometry extends SubsystemBase{
    private FieldPosition fieldPosition;

    private double lastUpdateTime = 1;



    public SwerveOdometry(FieldPosition _fieldPosition) {
        fieldPosition = _fieldPosition;
    }

    public FieldPosition getPosition() {
        return fieldPosition;
    }

    public void resetPos() {
        fieldPosition.reset();
    }

    public void updatePosition() {
        double timeNow = WPIUtilJNI.now() * 1.0e-6;
        double period = lastUpdateTime >= 0 ? timeNow - lastUpdateTime : 0.0;

        fieldPosition.positionCoord.x += Gyro.getVelocityX() * period;
        fieldPosition.positionCoord.y += Gyro.getVelocityY() * period;
        
        fieldPosition.angle = Gyro.getAngle();
        
        lastUpdateTime = timeNow;
        

        SmartDashboard.putNumber("fieldPosX ", fieldPosition.positionCoord.x);
        SmartDashboard.putNumber("fieldPosY ", fieldPosition.positionCoord.y);
        SmartDashboard.putNumber(" auto angle ", fieldPosition.angle);
    }

}