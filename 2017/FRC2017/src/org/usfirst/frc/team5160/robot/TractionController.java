package org.usfirst.frc.team5160.robot;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;

public class TractionController {
	EncoderTalon frontRight, frontLeft, backRight, backLeft;
	Gyro gyro;
	boolean tankMode = true; 
	public TractionController(EncoderTalon frontRight, EncoderTalon frontLeft, EncoderTalon backRight, EncoderTalon backLeft, Gyro gyro){
		this.frontRight = frontRight;
		this.frontLeft = frontLeft;
		this.backRight = backRight;
		this.backLeft = frontLeft;
		this.gyro = gyro;
	}
	
	public void tractionTank(double left, double right){
		if(left > 0.95 && right > 0.95){
			double avgVelocity = 1+(frontLeft.getVelocity() + frontRight.getVelocity() + backLeft.getVelocity() + backRight.getVelocity())/3.9;
			frontLeft.setVelocity(avgVelocity);
			frontRight.setVelocity(avgVelocity);
			backLeft.setVelocity(avgVelocity);
			backRight.setVelocity(avgVelocity);
		}
		else{
		frontLeft.setPercent(left);
		frontRight.setPercent(right);
		backLeft.setVelocity(frontLeft.getVelocity());
		backRight.setVelocity(frontRight.getVelocity());
		}
	}
	
}
