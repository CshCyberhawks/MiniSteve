package frc.robot.util;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class DriveEncoder {
    private TalonFX driveMotor;
    private double lastUpdateTime;

    public DriveEncoder(TalonFX driveMotor) {
        this.driveMotor = driveMotor;

        lastUpdateTime = MathClass.getCurrentTime();
    }

    public double getVelocity() {
        double currentTime = MathClass.getCurrentTime();
        double velocity = driveMotor.getSelectedSensorVelocity() / 100 * (currentTime - lastUpdateTime);
        lastUpdateTime = currentTime;
        return velocity;
    }

    public double getPosition() {
        return driveMotor.getSelectedSensorPosition() / 2048;
    }
}