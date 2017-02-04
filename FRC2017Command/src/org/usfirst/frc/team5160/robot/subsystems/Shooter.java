package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
    
    private CANTalon motor1;
    private CANTalon motor2;
    
    public Shooter(){
    	
    	motor1 = new CANTalon(RobotMap.SHOOTER_CIM_1);
    	motor2 = new CANTalon(RobotMap.SHOOTER_CIM_2);
    	
    }

    public void initDefaultCommand() {
        
    }
    
    public void shoot(double speed){
    	motor1.set(speed);
    	motor2.set(speed);
    }
    public void shoot(double mag1, double mag2){
    	motor1.set(mag1);
    	motor2.set(mag2);
    }
    public void stopMotors(){
    	motor1.set(0);
    	motor2.set(0);
    }
}

