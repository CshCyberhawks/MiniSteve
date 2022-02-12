package frc.robot.subsystems;

import frc.robot.util.MathClass;
import frc.robot.Robot;
import frc.robot.util.FieldPosition;
import frc.robot.util.Gyro;
import frc.robot.util.IO;
import frc.robot.util.Vector2;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Vector;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.WPIUtilJNI;
import java.lang.Math;

import org.ejml.simple.SimpleMatrix;

public class SwerveOdometry extends SubsystemBase {
    private FieldPosition fieldPosition;

    private double lastUpdateTime = 1;

    public SwerveOdometry(FieldPosition _fieldPosition) {
        fieldPosition = _fieldPosition;
    }

    public FieldPosition getPosition() {
        return fieldPosition;
    }

    public void resetPos() {
        fieldPosition.reset();
    }

    public double[] calculateVelocities() {
        // 4 is num_modules/number of wheels
        SimpleMatrix moduleStatesMatrix = new SimpleMatrix(4 * 2, 1);

        for (int i = 0; i < 4; i++) {
            double wheelSpeed = Robot.swerveSystem.wheelArr[i].currentDriveSpeed;
            double wheelAngle = Robot.swerveSystem.wheelArr[i].rawTurnValue;

            moduleStatesMatrix.set(i * 2, 0, wheelSpeed * Math.cos(wheelAngle));
            moduleStatesMatrix.set(i * 2 + 1, wheelSpeed * Math.sin(wheelAngle));
        }

        SimpleMatrix chassisSpeedVector = Robot.swerveSystem.forwardKinematics.mult(moduleStatesMatrix);

        return new double[] { chassisSpeedVector.get(0, 0), chassisSpeedVector.get(1, 0) };
    }

    public void updatePosition() {
        double timeNow = WPIUtilJNI.now() * 1.0e-6;
        double period = lastUpdateTime >= 0 ? timeNow - lastUpdateTime : 0.0;

        double[] velocities = calculateVelocities();

        SmartDashboard.putNumber(" velocitiyx ", velocities[0]);
        SmartDashboard.putNumber(" velocitiyy ", velocities[1]);

        fieldPosition.positionCoord.x += velocities[0] * period;
        fieldPosition.positionCoord.y += velocities[1] * period;
        fieldPosition.angle = Gyro.getAngle();

        lastUpdateTime = timeNow;

        SmartDashboard.putNumber("fieldPosX ", fieldPosition.positionCoord.x);
        SmartDashboard.putNumber("fieldPosY ", fieldPosition.positionCoord.y);
        SmartDashboard.putNumber(" auto angle ", fieldPosition.angle);
    }

}