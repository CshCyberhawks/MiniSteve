//https://readthedocs.org/projects/limelight/downloads/pdf/latest/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tv = table.getEntry("tv"); //Between 0 and 1 whether it has a valid target
    NetworkTableEntry tx = table.getEntry("tx"); //The horizontal offset between the crosshair and target in degrees
    NetworkTableEntry ty = table.getEntry("ty"); //The vertical offset between the crosshair and target in degrees
    NetworkTableEntry ta = table.getEntry("ta"); //Percentage of image (filled by target?)
    NetworkTableEntry tcornx = table.getEntry("tcornx");
    NetworkTableEntry tcorny = table.getEntry("tcorny");
    NetworkTableEntry tcornxy = table.getEntry("tcornxy");
    
    public Limelight() {
        
    }

    @Override
    public void periodic() {
        double hasValidTarget = tv.getDouble(0.0);
        double horrizontalOffset = tx.getDouble(0.0);
        double verticalOffset = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);
        double[] xCorners = tcornx.getDoubleArray(new double[2]);
        double[] yCorners = tcorny.getDoubleArray(new double[2]);
        double[] xyCorners = tcornxy.getDoubleArray(new double[4]);
        
        //Values needed from final robot before implemented
        //double distanceFromTarget = (targetHeight - cameraHeight) / Math.tan(mountAngle + verticalOffset);

        SmartDashboard.putNumber("Limelight hasValidTarget", hasValidTarget);
        SmartDashboard.putNumber("Limelight horrizontalOffset", horrizontalOffset);
        SmartDashboard.putNumber("Limelight verticalOffset", verticalOffset);
        SmartDashboard.putNumber("LimelightArea", area);
        SmartDashboard.putNumberArray("Limelight xCorners", xCorners);
        SmartDashboard.putNumberArray("Limelight yCorners", yCorners);
        SmartDashboard.putNumberArray("Limelight xyCorners", xyCorners);
    }
}