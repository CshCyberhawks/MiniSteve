package frc.robot.subsystems;

import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.links.SPILink;

public class CameraSystem {
    private Pixy2 pixyCam;
    private final SPILink link = new SPILink();
    public CameraSystem()
    {
        pixyCam = Pixy2.createInstance(link);
        pixyCam.init();
        pixyCam.setLamp((byte) 1, (byte) 1); 
    }
    public void setColor()
    {
        pixyCam.setLED(255, 255, 255);
    }
    public void setColorRed()
    {
        pixyCam.setLED(255, 0, 0);
    }
    public void setColorBlue()
    {
        pixyCam.setLED(0,0,255);
    }

}
