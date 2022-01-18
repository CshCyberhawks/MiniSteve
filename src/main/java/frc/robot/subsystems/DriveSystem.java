package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;


public class DriveSystem extends SubsystemBase {
    VictorSPX leftBackMotor = new VictorSPX(Constants.leftBackMotor);
    VictorSPX leftFrontMotor = new VictorSPX(Constants.leftFrontMotor);
    VictorSPX rightBackMotor = new VictorSPX(Constants.rightBackMotor);
    VictorSPX rightFrontMotor = new VictorSPX(Constants.rightFrontMotor);

    public DriveSystem() {}


    public void setSpeed(double leftSpeed, double rightSpeed) {
        leftBackMotor.set(ControlMode.PercentOutput, -leftSpeed);
        leftFrontMotor.set(ControlMode.PercentOutput, -leftSpeed);
        rightBackMotor.set(ControlMode.PercentOutput, rightSpeed);
        rightFrontMotor.set(ControlMode.PercentOutput, rightSpeed);
        SmartDashboard.putNumber("Left Motor", -leftSpeed);
        SmartDashboard.putNumber("Right Motor", rightSpeed);
    }


    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
}
