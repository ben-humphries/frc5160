package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearMechanism extends Subsystem {
    

	private Spark hoodMotor;
	
	
	public GearMechanism(){
	}
	
	public void pushHood(double speed){
	}
	
	public void pullHood(double speed){
	}
	
	public void stopMotor(){
	}
	
	@Override
    public void initDefaultCommand() {
    }
}

