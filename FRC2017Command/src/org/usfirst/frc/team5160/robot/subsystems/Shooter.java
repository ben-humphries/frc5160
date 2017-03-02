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
    	
    	motor1 = new CANTalon(RobotMap.SHOOTER_775_2);
    	motor2 = new CANTalon(RobotMap.SHOOTER_775_1);
    	motor1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	motor1.configEncoderCodesPerRev(1024);
    	motor1.changeControlMode(TalonControlMode.PercentVbus);
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
    	motor.setProfile(0);
    	motor.enableBrakeMode(false);
    	motor.setInverted(true);
    	motor.setPID(Robot.shootP, Robot.shootI, Robot.shootD ,Robot.shootF, (int) (1023/Robot.shootP), 0, 0);
    }
    public void shootBangBang(double speed){
    	motor1.changeControlMode(TalonControlMode.PercentVbus);
    	if(motor1.getSpeed() < speed){
    		motor1.set(rampVelocity());
    	}
    }
    public double rampVelocity(){
    	if(motor1.getSpeed()<500){
    		return 0.25;
    	}
    	else if(motor1.getSpeed() < 1000){
    		return 0.5;
    	}
    	return 1;
    }
    public void shoot(double speed){
    	motor1.changeControlMode(TalonControlMode.Speed);
    	System.out.println("Shoot "+speed+" , "+motor1.getSpeed());
    	motor1.set(speed);
    }
    
    public void stopMotors(){
    	motor1.set(0);
    	motor2.set(0);
    }
}