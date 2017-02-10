package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearMechanism extends Subsystem {
    

	private CANTalon gearMotor;
	
	
	public GearMechanism(){
		
		gearMotor = new CANTalon(RobotMap.GEAR_CIM);
	}
	
	public void pushGear(double speed){
		gearMotor.set(speed);
	}
	public void pullGear(double speed){
		gearMotor.set(-1*speed);
	}
	public void stopMotor(){
		gearMotor.set(0);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
}

