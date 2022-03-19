package frc.robot.util;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveEncoder {
    private TalonFX driveMotor;
    private double lastUpdateTime;

    public DriveEncoder(TalonFX driveMotor) {
        this.driveMotor = driveMotor;

        // lastUpdateTime = -1;
    }

    public double getVelocity() {
        // double timeNow = WPIUtilJNI.now() * 1.0e-6;
        // double period = lastUpdateTime >= 0 ? timeNow - lastUpdateTime : 0.0;

        // SmartDashboard.putNumber("Encoder Now", timeNow);
        // SmartDashboard.putNumber("Encoder Last", lastUpdateTime);
        // SmartDashboard.putNumber("deltaTime", period);
        // SmartDashboard.putNumber("Raw Drive Encoder", driveMotor.getSelectedSensorVelocity());

        double velocity = (driveMotor.getSelectedSensorVelocity() / 2048) * 10;
        // SmartDashboard.putNumber("Drive Encoder", velocity);
        // lastUpdateTime = timeNow;
        return velocity;
    }

    public double getPosition() {
        return driveMotor.getSelectedSensorPosition() / 2048;
    }
}
