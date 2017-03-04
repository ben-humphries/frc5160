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
    	motor1.changeControlMode(TalonControlMode.PercentVbus);
    	motor2.changeControlMode(TalonControlMode.Follower);
    	motor2.set(RobotMap.SHOOTER_775_2);
    	motor1.setPID(Robot.pShoot, Robot.iShoot, Robot.dShoot, Robot.fShoot, (int) (1023.0 / Robot.pShoot),36,0);
    	motor1.setProfile(0);
    	motor1.setInverted(true);
    	motor2.setInverted(true);
    	motor1.setVoltageRampRate(36.0);
    	motor2.setVoltageRampRate(36);
    	motor1.enableBrakeMode(false);
    	motor2.enableBrakeMode(false);
    	motor1.clearStickyFaults();
    	motor2.clearStickyFaults();
    	
    }

    public void initDefaultCommand() {
        
    }
    
    public void shootBangBang(double speed){
    	motor1.changeControlMode(TalonControlMode.PercentVbus);
    	motor2.changeControlMode(TalonControlMode.PercentVbus);
    	if(motor1.getSpeed() < speed){
    		motor1.set(rampVelocity());
    		motor2.set(rampVelocity());
    	}
    	else{
    		motor1.set(0.25);
    		motor2.set(0.25);
    	}
    }
    public double rampVelocity(){
    	if(motor1.getSpeed()<500){
    		return 0.5;
    	}
    	else if(motor1.getSpeed() < 1000){
    		return 0.75;
    	}
    	return 1;
    }
 
    
    public void stopMotors(){
    	motor1.set(0);
    	motor2.set(0);
    }
    public double getSpeed(){
    	return motor1.getSpeed();
    }

	public void shootPID(double targetSpeed) {
		motor1.changeControlMode(TalonControlMode.Speed);
		motor1.set(targetSpeed);
	}

	public void shoot(double targetSpeed) {
		motor1.changeControlMode(TalonControlMode.PercentVbus);
		motor1.set(targetSpeed);
		
	}
}