package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CMDAutoRotate extends Command{
	private double desiredAngle, rotationPower;
	private ADXRS450_Gyro gyro;
	private static final double ROT_ERROR = 3;
	public CMDAutoRotate( double desiredAngle, double rotationPower){
		requires(Robot.BASE);
		this.desiredAngle = desiredAngle;
		this.rotationPower = Math.abs(rotationPower);
		this.gyro = Robot.BASE.getGyro();
	}
	@Override
	protected void initialize(){
		gyro.reset();
	}
	@Override
	public void execute(){
		double dir;
		dir = RMath.sign(desiredAngle-gyro.getAngle());
		//Rotate Robot based on rotationMag
		Robot.BASE.mecanumDrive(0, 0, dir*rotationPower);
		Timer.delay(0.03);
		
	}
	@Override
	protected boolean isFinished() {
			return(Math.abs(desiredAngle-gyro.getAngle())<ROT_ERROR);
	}
}
