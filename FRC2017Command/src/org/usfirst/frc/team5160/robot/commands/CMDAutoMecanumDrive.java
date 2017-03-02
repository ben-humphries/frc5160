package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This allows for mecanum movement while rotating precisely in auto. 
 */

public class CMDAutoMecanumDrive extends Command{
	private double x, y, desiredAngle, rotationPower, startAngle;
	private boolean absolute;
	private ADXRS450_Gyro gyro;
	public CMDAutoMecanumDrive(double x, double y, double desiredAngle, double rotationPower, boolean absoluteAngle){
		requires(Robot.BASE);
		this.x = x;
		this.y = y;
		this.desiredAngle = desiredAngle;
		this.rotationPower = rotationPower;
		this.gyro = Robot.BASE.getGyro();
		this.absolute = absoluteAngle;
	}
	@Override
	public void initialize(){
		Robot.BASE.setInvertAuto();
		this.startAngle = gyro.getAngle();
	}
	@Override
	public void execute(){
		double dir = 0; 
		if(!isRotationFinished()){
			if(absolute){
				dir = RMath.sign(desiredAngle-startAngle);
			}
			else {
				dir = RMath.sign(desiredAngle);
			}
		}
		//Rotate Robot based on rotationMag
		Robot.BASE.mecanumDrive(x, y, dir*rotationPower);
		Timer.delay(0.03);
		
	}
	private boolean isRotationFinished(){
		if(absolute){
			double dir = RMath.sign(desiredAngle-startAngle);
			return dir*(desiredAngle-gyro.getAngle()) > 0;
		}
		else{
			double dir = RMath.sign(desiredAngle);
			return dir*(desiredAngle-desiredAngle+startAngle)>0;
		}
	}
	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
