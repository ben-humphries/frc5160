package org.usfirst.frc.team5160.robot;

import com.ctre.CANTalon;

public class ChargerTalon extends CANTalon{
	private double clicksPerUnit = 1.0;
	public ChargerTalon(int deviceNumber) {
		super(deviceNumber);
	}
	public void setPIDValues(double p, double i, double d){
		
	}
	private void ensureControlMode(TalonControlMode mode){
		if(getControlMode() != mode){
			changeControlMode(mode);
		}
	}
}