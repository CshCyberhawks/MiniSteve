
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ColorSystem {
    private I2C.Port port = I2C.Port.kMXP;
    private ColorSensorV3 colorSensor = new ColorSensorV3(port);
    private ColorMatch colorMatch = new ColorMatch();
    public ColorSystem()
    {
        port = I2C.Port.kMXP;
        colorSensor = new ColorSensorV3(port);
        colorMatch = new ColorMatch();
        setColorMatch();
    }
    private void setColorMatch()
    {
        colorMatch.addColorMatch(Color.kRed);
        colorMatch.addColorMatch(Color.kBlue);
    }
    public String returnColor()
    {
        Color foundColor = colorSensor.getColor();
        String colorString;
        ColorMatchResult result = colorMatch.matchClosestColor(foundColor);
        
        if (result.color == Color.kBlue)
            colorString = "Blue";
        else if (result.color == Color.kRed)
            colorString = "Red";
        else 
        colorString = "Unknown";
        SmartDashboard.putNumber("Red", foundColor.red); //check with 
        SmartDashboard.putNumber("Green", foundColor.green);
        SmartDashboard.putNumber("Blue", foundColor.blue);
        SmartDashboard.putNumber("Confidence", result.confidence);
        SmartDashboard.putString("Detected Color", colorString);
        return colorString;
    }
}
