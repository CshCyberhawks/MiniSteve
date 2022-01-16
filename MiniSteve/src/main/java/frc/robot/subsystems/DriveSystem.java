package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.opencv.core.Mat;

public class DriveSystem extends SubsystemBase {
    VictorSPX motor1 = new VictorSPX(0);
    VictorSPX motor2 = new VictorSPX(1);
    VictorSPX motor3 = new VictorSPX(6);
    VictorSPX motor4 = new VictorSPX(8);

    public DriveSystem() {

    }


    public void setSpeed(double leftSpeed, double rightSpeed) {
        motor1.set(ControlMode.PercentOutput, rightSpeed);
        motor2.set(ControlMode.PercentOutput, rightSpeed);
        motor3.set(ControlMode.PercentOutput, leftSpeed);
        motor4.set(ControlMode.PercentOutput, leftSpeed);
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
