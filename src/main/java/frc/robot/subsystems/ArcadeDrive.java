package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArcadeDrive extends SubsystemBase {
    CANSparkMax frontRightMotor = new CANSparkMax(Constants.frontRightDriveMotor, MotorType.kBrushless);
    CANSparkMax frontLeftMotor = new CANSparkMax(Constants.frontLeftDriveMotor, MotorType.kBrushless);
    CANSparkMax backRightMotor = new CANSparkMax(Constants.backRightDriveMotor, MotorType.kBrushless);
    CANSparkMax backLeftMotor = new CANSparkMax(Constants.backLeftDriveMotor, MotorType.kBrushless);

    double rightSpeed = 0;
    double leftSpeed = 0;

    public ArcadeDrive() {
        
    }

    @Override
    public void periodic() {
        frontRightMotor.set(rightSpeed);
        frontLeftMotor.set(-leftSpeed);
        backRightMotor.set(rightSpeed);
        backLeftMotor.set(-leftSpeed);
    }

    public void setSpeed(double newLeftSpeed, double newRightSpeed) {
        leftSpeed = newLeftSpeed;
        rightSpeed = newRightSpeed;
    }
}
