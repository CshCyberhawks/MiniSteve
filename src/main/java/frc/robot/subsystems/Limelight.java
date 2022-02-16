package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry tx = table.getEntry("tx"); //The horizontal offset between the crosshair and target in degrees
    private static NetworkTableEntry ty = table.getEntry("ty"); //The vertical offset between the crosshair and target in degrees
    private static LinearFilter filter = LinearFilter.highPass(0.1, 0.02);
    
    public Limelight() {}

    public static double getHorizontalOffset() {
        return filter.calculate(tx.getDouble(0.0));
    }

    public static double getVerticalOffset() {
        return ty.getDouble(0.0);
    }

    //(target height - limelight height) / tanget(limelight angle + target vertical offset)
    public static double getDistance() {
        return ((18.5 - 9.875) / (Math.tan(Math.toRadians(0 + getVerticalOffset()))));    
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Limelight horrizontalOffset", getHorizontalOffset());
        SmartDashboard.putNumber("Limelight verticalOffset", getVerticalOffset());
        SmartDashboard.putNumber("Limelight distance", getDistance());
    }
}