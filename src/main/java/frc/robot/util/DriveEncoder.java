package frc.robot.util;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveEncoder {
    private TalonFX driveMotor;
    private double lastUpdateTime = -1;

    public DriveEncoder(TalonFX driveMotor) {
        this.driveMotor = driveMotor;

    }

    public double getVelocity() {
        double timeNow = WPIUtilJNI.now() * 1.0e-6;
        double period = lastUpdateTime >= 0 ? timeNow - lastUpdateTime : 0.0;
        SmartDashboard.putNumber("deltaTime", period);
        SmartDashboard.putNumber("Raw Drive Encoder", driveMotor.getSelectedSensorVelocity());
        double velocity = (driveMotor.getSelectedSensorVelocity() / 2048) * (period / .1);
        lastUpdateTime = timeNow;
        return 0;// velocity;
    }

    public double getPosition() {
        return driveMotor.getSelectedSensorPosition() / 2048;
    }
}
