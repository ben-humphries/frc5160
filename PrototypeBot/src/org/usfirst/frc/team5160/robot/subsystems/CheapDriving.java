package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.EncoderTalon;
import org.usfirst.frc.team5160.robot.activities.Activity;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class CheapDriving extends Activity{
	private Joystick leftJoystick, rightJoystick;
	Talon left,right;
	private static final double clicksPerUnit = 3305.0 / 23.25;
	public CheapDriving(Joystick leftJoystick, Joystick rightJoystick){
		this.leftJoystick = leftJoystick;
		this.rightJoystick = rightJoystick;
		left = new Talon(0);
		right = new Talon(1);
		
	}

	@Override
	public void init() {
	}

	@Override
	public void loopAuto(double deltaTime) {
		
	}

	@Override
	public void loopTele(double deltaTime) {
		left.set(leftJoystick.getY());
		right.set(rightJoystick.getY());
	}

	@Override
	public void stop() {
		
	}
}
