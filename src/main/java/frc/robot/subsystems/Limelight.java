//https://readthedocs.org/projects/limelight/downloads/pdf/latest/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry tv = table.getEntry("tv"); //Between 0 and 1 whether it has a valid target
    private static NetworkTableEntry tx = table.getEntry("tx"); //The horizontal offset between the crosshair and target in degrees
    private static NetworkTableEntry ty = table.getEntry("ty"); //The vertical offset between the crosshair and target in degrees
    private static NetworkTableEntry ta = table.getEntry("ta"); //Percentage of image (filled by target?)
    
    public Limelight() {}

    public static double getHorizontalOffset() {
        return tx.getDouble(0.0);
    }

    public static double getVerticalOffset() {
        return ty.getDouble(0.0);
    }

    public static double getArea() {
        return ta.getDouble(0.0);
    }

    public static double getTarget() {
        return tv.getDouble(0.0);
    }

    public static double getDistance() {
        return getArea() * 1;
    }

    @Override
    public void periodic() {
        //Values needed from final robot before implemented
        //double distanceFromTarget = (targetHeight - cameraHeight(10 inches)) / Math.tan(mountAngle + getVerticalOffset());

        SmartDashboard.putNumber("Limelight hasValidTarget", getTarget());
        SmartDashboard.putNumber("Limelight horrizontalOffset", getHorizontalOffset());
        SmartDashboard.putNumber("Limelight verticalOffset", getVerticalOffset());
        SmartDashboard.putNumber("LimelightArea", getArea());
    }
}
