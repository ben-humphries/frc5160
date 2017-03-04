package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.RobotMap;
import org.usfirst.frc.team5160.robot.commands.CMDPushHood;

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
		hoodMotor = new Spark(RobotMap.HOOD_NEVEREST);
		//gearMotor = new CANTalon(RobotMap.GEAR_CIM);
	}
	
	public void pushHood(double speed){
		hoodMotor.set(speed);
	}
	public void pullHood(double speed){
		hoodMotor.set(-1*speed);
	}
	public void stopMotor(){
		hoodMotor.set(0);
	}
	@Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new CMDPushHood());
    }
}

