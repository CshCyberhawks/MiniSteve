package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
<<<<<<< Updated upstream
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.DriverStation;
=======
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
>>>>>>> Stashed changes
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
<<<<<<< Updated upstream
    private static NetworkTableEntry tv = table.getEntry("tv");
    private static NetworkTableEntry tx = table.getEntry("tx"); //The horizontal offset between the crosshair and target in degrees
    private static NetworkTableEntry ty = table.getEntry("ty"); //The vertical offset between the crosshair and target in degrees
    private static NetworkTableEntry tc = table.getEntry("tc");
    private static NetworkTableEntry pipeline = table.getEntry("pipeline");
    private static LinearFilter filter = LinearFilter.highPass(0.1, 0.02);
    private static Alliance teamColor = DriverStation.getAlliance();
    public Limelight() {}
    public static void setTeam()
    {
        if (teamColor == Alliance.Blue)
        {
            pipeline.setString("");
        }
        else if (teamColor == Alliance.Red)
        {
            pipeline.setString("FRCPipe");
=======
    private static NetworkTableEntry tv = table.getEntry("tv"); //0 or 1 whether it has a valid target
    private static NetworkTableEntry tx = table.getEntry("tx"); //The horizontal offset between the crosshair and target in degrees
    private static NetworkTableEntry ty = table.getEntry("ty"); //The vertical offset between the crosshair and target in degrees
    private static NetworkTableEntry ta = table.getEntry("ta"); //Percentage of image
    private static NetworkTableEntry tc = table.getEntry("tc"); //HSV color underneath the crosshair region as a NumberArray 
    private static NetworkTableEntry pipeline = table.getEntry("pipeline");
    private static Alliance team = DriverStation.getAlliance();
    public Limelight() { }
    public static void setTeam()
    {
        if (team == Alliance.Red)
            pipeline.setString("FRCPipe");
        else if (team == Alliance.Blue)
        {
            pipeline.setString("FRCBPipe");
>>>>>>> Stashed changes
        }
    }
    public static double getHorizontalOffset() {
        return filter.calculate(tx.getDouble(0.0));
    }

    public static double getVerticalOffset() {
        return ty.getDouble(0.0);
    }
<<<<<<< Updated upstream
    public static Number[] getHSVColor()
    {
        return tc.getNumberArray(new Number[] {-1});
    }
    public static boolean hasTarget()
    {
        return (int)tv.getDouble(0) == 1;
    }
    //(target height - limelight height) / tanget(limelight angle + target vertical offset)
    public static double getDistance() {
        if (hasTarget())
            return ((18.5 - 9.875) / (Math.tan(Math.toRadians(0 + getVerticalOffset()))));   
        return 0; 
    }
   
    @Override
    public void periodic() {   
=======

    public static double getArea() {
        return ta.getDouble(0.0);
    }

    public static boolean hasTarget() {
        return tv.getDouble(0.0) == 1;
    }
    public static Number[] getColor()
    {
        return tc.getNumberArray(new Number[] {-1});
    }
    
    public static double getDistance() {
       return getArea();
    }

    @Override
    public void periodic() {
        //Values needed from final robot before implemented
        //double distanceFromTarget = (10 - 10) / Math.tan(0 + getVerticalOffset());
        
        SmartDashboard.putBoolean("Limelight hasValidTarget", hasTarget());
>>>>>>> Stashed changes
        SmartDashboard.putNumber("Limelight horrizontalOffset", getHorizontalOffset());
        SmartDashboard.putNumber("Limelight verticalOffset", getVerticalOffset());
        SmartDashboard.putNumber("Limelight distance", getDistance());
    }
}