package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Rotates robot based on a certain number of degrees entered and a magnitude of rotation.
 */
public class RotateBase extends Command {
	
	double degrees;
	boolean angleReached;
	double currentAngle;
	
	double rotationMag;
	
	ADXRS450_Gyro gyro;

    public RotateBase(double degrees, double rotationMag) {

    	requires(Robot.BASE);
    	
    	this.degrees = degrees;
    	angleReached = false;
    	currentAngle = 0;
    	
    	this.rotationMag = rotationMag;
    	
    	this.gyro = Robot.BASE.getGyro();
    }

    protected void execute() {
    		
		double startAngle = gyro.getAngle();
		
		//Rotate Robot based on rotationMag
		Robot.BASE.mecanumDrive(0, 0, rotationMag);
		Timer.delay(0.05);
		
		//track current angle
		double deltaAngle = gyro.getAngle() - startAngle;
		currentAngle += deltaAngle;
		
		//if current angle is equal to or has exceeded the number of degrees to be rotated, stop
		if(Math.abs(currentAngle) >= Math.abs(degrees)){
			angleReached = true;
		}
    		
    	
    }
    protected boolean isFinished() {
    	
        if(angleReached){
        	return true;
        }
        return false;
    }

}
