package frc.robot.util;

public class FieldPosition {
    public double angle = 0;
    public Vector2 positionCoord;

    public FieldPosition(double startX, double startY, double startAngle) {
        positionCoord = new Vector2(startX, startY);
        angle = startAngle;
    }

    public void reset() {
        positionCoord = new Vector2(0, 0);
        angle = 0;
    }

}
