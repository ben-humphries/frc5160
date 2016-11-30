package org.usfirst.frc.team5160.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class RobotDrive {


	private double clicksPerUnit = 3305.0 / 23.25;
	public EncoderTalon leftMotor, rightMotor;
    private CANTalon leftSlaveMotor, rightSlaveMotor;
	private Joystick leftStick, rightStick, thirdStick;

	public RobotDrive(Joystick leftStick, Joystick rightStick, Joystick thirdStick) {
		this.leftStick = leftStick;
		this.rightStick = rightStick;
		this.thirdStick = thirdStick;
    	leftMotor = new EncoderTalon(1, -clicksPerUnit);
    	rightMotor = new EncoderTalon(3, clicksPerUnit);
    	leftSlaveMotor = new CANTalon(2);
    	rightSlaveMotor = new CANTalon(4);
	}
	
	public void init() {
		PID drivePID = new PID("Drive_PID", 0, 6, 10.0, 20);
		leftMotor.setPID(drivePID);
		rightMotor.setPID(drivePID);
		
		leftMotor.init();
		rightMotor.init();
		leftMotor.setSlave(leftSlaveMotor);
		rightMotor.setSlave(rightSlaveMotor);
		
		leftMotor.reverseOutput(true);
		rightMotor.reverseOutput(true);
		
		rightMotor.setInverted(true);
	}
	
	public void operatorControlTick() {
        //Robot.debugHash.put("leftMotor Position", leftMotor.getPosition());
        //Robot.debugHash.put("rightMotor Position", rightMotor.getPosition());
        Robot.debugHash.put("leftMotor Velocity", leftMotor.getVelocity());
        Robot.debugHash.put("rightMotor Velocity", rightMotor.getVelocity());
        
//        if (leftStick.getRawButton(2)) {
//    		double maxVel = Robot.configHash.get("MaxVel");
//    		leftMotor.setVelocity(-maxVel * leftStick.getY());
//    		rightMotor.setVelocity(maxVel * rightStick.getY());
//    		Robot.debugHash.put("Set to: ", maxVel * leftStick.getY());
//        } else {
        	if (!leftStick.getRawButton(1)) {
        		leftMotor.setPercent(-leftStick.getY());
        		rightMotor.setPercent(-rightStick.getY());
        	} else {
        		leftMotor.setPercent(rightStick.getY()*0.5);
        		rightMotor.setPercent(leftStick.getY()*0.5);
        	}
//        }
        	

        if (thirdStick.getRawButton(10)) {
        	rightMotor.zero();
        	leftMotor.zero();
        }
        //Robot.debugHash.put("LeftPos", leftMotor.getPosition());
        //Robot.debugHash.put("RightPos", rightMotor.getPosition());
	}
	
	public void rampartsInit() {
		double dist = 150;
		leftMotor.move(dist, 0.364);
		rightMotor.move(dist, 0.4025);
	}
	
	public void rampartsUpdate() {
		leftMotor.thereYet();
		if (rightMotor.thereYet()) {
			leftMotor.set(0);
		}
	}
	
	public void stop() {
		leftMotor.set(0);
		rightMotor.set(0);
	}
}
