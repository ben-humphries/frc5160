package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CMDAutoRotate extends Command{
	private double desiredAngle, rotationPower, startAngle;
	private boolean absolute;
	private ADXRS450_Gyro gyro;
	public CMDAutoRotate( double desiredAngle, double rotationPower, boolean absoluteAngle){
		requires(Robot.BASE);
		this.desiredAngle = desiredAngle;
		this.rotationPower = rotationPower;
		this.gyro = Robot.BASE.getGyro();
		this.absolute = absoluteAngle;
	}
	@Override
	protected void initialize(){
		Robot.BASE.setInvertAuto();
		this.startAngle = gyro.getAngle();
	}
	@Override
	public void execute(){
		double dir; 
		if(absolute){
			dir = RMath.sign(desiredAngle-startAngle);
		}
		else{
			dir = RMath.sign(desiredAngle);
		}
		//Rotate Robot based on rotationMag
		Robot.BASE.mecanumDrive(0, 0, dir*rotationPower);
		Timer.delay(0.03);
		
	}
	@Override
	protected boolean isFinished() {
		if(absolute){
			double dir = RMath.sign(desiredAngle-startAngle);
			return dir*(desiredAngle-gyro.getAngle()) > 0;
		}
		else{
			double dir = RMath.sign(desiredAngle);
			return dir*(desiredAngle-desiredAngle+startAngle)>0;
		}
	}
}
