package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.RobotMap;
import org.usfirst.frc.team5160.robot.commands.CMDRotateGearMech;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearMechanism extends Subsystem {
    

	private CANTalon motor1;
	private CANTalon motor2;
	
	
	
	public GearMechanism(){
		
		motor1 = new CANTalon(RobotMap.GEAR_ROTATE);
		motor2 = new CANTalon(RobotMap.GEAR_INTAKE);
	}
	
	public void rotate(double speed){
		motor1.set(speed);
	}
	public void intake(double speed){
		motor2.set(speed);
	}
	
	public void stopMotors(){
		motor1.set(0);
		motor2.set(0);
	}
	
	@Override
    public void initDefaultCommand() {
		setDefaultCommand(new CMDRotateGearMech());
    }
}

