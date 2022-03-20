package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.util.FieldPosition;
import frc.robot.util.Gyro;
import frc.robot.util.Vector2;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.WPIUtilJNI;

public class SwerveOdometry extends SubsystemBase {
    private FieldPosition fieldPosition = new FieldPosition(0, 0, 0);

    private double lastUpdateTime = 1;
    private double[] robotVelocities = new double[] { 0, 0 };

    public SwerveOdometry(FieldPosition fieldPosition) {
        this.fieldPosition = fieldPosition;
    }

    public FieldPosition getPosition() {
        return fieldPosition;
    }

    public double[] getVelocities() {
        return robotVelocities;
    }

    public void resetPos() {
        fieldPosition.reset();
    }

    public double[] calculateVelocities() {
        Vector2[] wheelCoords = new Vector2[4];
        double totalX = 0;
        double totalY = 0;

        for (int i = 0; i < 4; i++) {
            double wheelAngle = Robot.swerveSystem.wheelArr[i].turnValue;
            double wheelSpeed = Robot.swerveSystem.wheelArr[i].currentDriveSpeed;

            // if (i == 0 || i == 2) {
            // wheelSpeed = -wheelSpeed;
            // }

            // Undoes the wheel optimization
            // commented out to see if it will work without optimization
            if (wheelSpeed < 0) {
                wheelSpeed = -wheelSpeed;
                wheelAngle = (wheelAngle + 180) % 360;
            }

            double[] cartCoords = Robot.swerveSystem.polarToCartesian(wheelAngle, wheelSpeed);

            wheelCoords[i] = new Vector2(cartCoords[0], cartCoords[1]);

            totalX += cartCoords[0];
            totalY += cartCoords[1];

        }

        double[] robotPolar = Robot.swerveSystem.cartesianToPolar(totalX, totalY);
        // maybe below is done incorrectly / is unnecessary? also possible that it
        // should be subtracting gyro not adding
        robotPolar[0] -= Gyro.getAngle();

        robotVelocities = Robot.swerveSystem.polarToCartesian(robotPolar[0], robotPolar[1]);

        // return new double[] { totalX, totalY };
        return new double[] { robotVelocities[0], robotVelocities[1] };
    }

    public void updatePosition() {
        double timeNow = WPIUtilJNI.now() * 1.0e-6;
        double period = lastUpdateTime >= 0 ? timeNow - lastUpdateTime : 0.0;

        double[] velocities = calculateVelocities();

        fieldPosition.positionCoord.x += velocities[0] * period;
        fieldPosition.positionCoord.y += velocities[1] * period;
        fieldPosition.angle = Gyro.getAngle();

        SmartDashboard.putNumber("fieldPosX ", fieldPosition.positionCoord.x);
        SmartDashboard.putNumber("fieldPosY ", fieldPosition.positionCoord.y);
        SmartDashboard.putNumber("fieldPosAngle ", fieldPosition.angle);

        lastUpdateTime = timeNow;
    }
}