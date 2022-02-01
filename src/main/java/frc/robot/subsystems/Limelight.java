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
    NetworkTableEntry tx = table.getEntry("tx"); //The horizontal offset between the crosshair and target
    NetworkTableEntry ty = table.getEntry("ty"); //The vertical offset between the crosshair and target
    NetworkTableEntry ta = table.getEntry("ta"); //Percentage of image (filled by target?)
    NetworkTableEntry tcornx = table.getEntry("tcornx");
    NetworkTableEntry tcorny = table.getEntry("tcorny");
    
    public Limelight() {
        
    }

    @Override
    public void periodic() {
        double hasValidTarget = tv.getDouble(0.0);
        double horrizontalOffset = tx.getDouble(0.0);
        double verticalOffset = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);
        double[] xCorners = tcornx.getDoubleArray(new double[4]);
        double[] yCorners = tcorny.getDoubleArray(new double[4]);
        SmartDashboard.putNumber("hasValidTarget", hasValidTarget);
        SmartDashboard.putNumber("limelight horrizontalOffset", horrizontalOffset);
        SmartDashboard.putNumber("limelight verticalOffset", verticalOffset);
        SmartDashboard.putNumber("LimelightArea", area);
        SmartDashboard.putNumberArray("xCorners", xCorners);
        SmartDashboard.putNumberArray("yCorners", yCorners);
    }
}