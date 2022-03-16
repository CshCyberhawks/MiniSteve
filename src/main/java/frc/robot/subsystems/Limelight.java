package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

public class Limelight extends SubsystemBase {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry tv = table.getEntry("tv"); // 0 or 1 whether it has a valid target
    private static NetworkTableEntry tx = table.getEntry("tx"); // The horizontal offset between the crosshair and
                                                                // target in degrees
    private static NetworkTableEntry ty = table.getEntry("ty"); // The vertical offset between the crosshair and target
                                                                // in degrees
    private static NetworkTableEntry ta = table.getEntry("ta"); // Percentage of image
    private static NetworkTableEntry tc = table.getEntry("tc"); // HSV color underneath the crosshair region as a
                                                                // NumberArray
    private static NetworkTableEntry pipeline = table.getEntry("pipeline");
    private static Alliance team = DriverStation.getAlliance();

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

    public static boolean hasTarget() {
        return tv.getDouble(0.0) == 1;
    }

    public static Number[] getColor() {
        return tc.getNumberArray(new Number[] { -1 });
    }

    public static double getDistance() {
        return getArea();
    }
    
    public static void setTeam() {
        if (team == Alliance.Red)
            pipeline.setString("FRCPipe");
        else if (team == Alliance.Blue)
            pipeline.setString("FRCBPipe");
    }

    @Override
    public void periodic() {
        // Values needed from final robot before implemented
        // double distanceFromTarget = (10 - 10) / Math.tan(0 + getVerticalOffset());

        SmartDashboard.putBoolean("Limelight hasValidTarget", hasTarget());
        SmartDashboard.putNumber("Limelight horrizontalOffset", getHorizontalOffset());
        SmartDashboard.putNumber("Limelight verticalOffset", getVerticalOffset());
        SmartDashboard.putNumber("Limelight distance", getDistance());
    }
}
