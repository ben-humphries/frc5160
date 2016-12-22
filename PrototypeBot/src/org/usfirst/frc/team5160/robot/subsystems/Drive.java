package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.EncoderTalon;
import org.usfirst.frc.team5160.robot.activities.Activity;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class Drive extends Activity{
	private EncoderTalon leftMasterMotor, rightMasterMotor;
	private CANTalon leftSlaveMotor, rightSlaveMotor; 
	private Joystick leftJoystick, rightJoystick;
	private static final double clicksPerUnit = 3305.0 / 23.25;
	public Drive(Joystick leftJoystick, Joystick rightJoystick){
		this.leftJoystick = leftJoystick;
		this.rightJoystick = rightJoystick;
		leftMasterMotor = new EncoderTalon(1, -clicksPerUnit);
    	rightMasterMotor = new EncoderTalon(3, clicksPerUnit);
    	leftSlaveMotor = new CANTalon(2);
    	rightSlaveMotor = new CANTalon(4);
    	
	}

	@Override
	public void init() {
		leftMasterMotor.init();
		rightMasterMotor.init();
		leftMasterMotor.setSlave(leftSlaveMotor);
		rightMasterMotor.setSlave(rightSlaveMotor);
		
	}

	@Override
	public void loopAuto(double deltaTime) {
		
	}

	@Override
	public void loopTele(double deltaTime) {
		leftMasterMotor.setPercent(leftJoystick.getY());
		rightMasterMotor.setPercent(rightJoystick.getY());
	}

	@Override
	public void stop() {
		leftMasterMotor.set(0);
		rightMasterMotor.set(0);
	}
}
