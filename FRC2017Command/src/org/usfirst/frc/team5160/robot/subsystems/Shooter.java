package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
    
    private CANTalon motor1;
    private CANTalon motor2;
    
    public Shooter(){
    	
    	motor1 = new CANTalon(RobotMap.SHOOTER_775_1);
    	motor2 = new CANTalon(RobotMap.SHOOTER_775_2);
    	motor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	motor1.configEncoderCodesPerRev(1024);
    	motor1.changeControlMode(TalonControlMode.Speed);
    	motor2.changeControlMode(TalonControlMode.Follower);
    	initMotor(motor1);
    	initMotor(motor2);
    	motor2.set(RobotMap.SHOOTER_775_2);
    }

    public void initDefaultCommand() {
        
    }
    
    private void initMotor(CANTalon motor){
    	motor.setVoltageRampRate(24.0);
    	motor.configNominalOutputVoltage(-0f, 0f);
    	motor.configPeakOutputVoltage(-12, 12);
    	motor.setAllowableClosedLoopErr(100);
    	motor.setProfile(0);
    	motor.setPID(Robot.shootD, Robot.shootI, Robot.shootD );
    	motor.setF(Robot.shootF);
    }
    
    public void shoot(double speed){
    	System.out.println("Shoot "+speed);
    	motor1.set(-speed);
    }
    
    public void stopMotors(){
    	motor1.set(0);
    	motor2.set(0);
    }
}