package org.usfirst.frc.team5160.robot;

import org.usfirst.frc.team5160.robot.commands.CMDClimb;
import org.usfirst.frc.team5160.robot.commands.CMDIntakeGear;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
 
	
	public Joystick leftJoystick = new Joystick(RobotMap.LEFT_JOYSTICK);
	public Joystick rightJoystick = new Joystick(RobotMap.RIGHT_JOYSTICK);
	
	public Joystick operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
	
	//Drive joystick
	Button slowButton = new JoystickButton(leftJoystick, 1),
				reverseButton = new JoystickButton(rightJoystick, 1);
	
	//Operator joystick
	Button climbUpButton = new JoystickButton(operatorJoystick, 3),
				gearIntake = new JoystickButton(operatorJoystick, 1),
				gearOuttake = new JoystickButton(operatorJoystick, 2); //maybe use different button?
	
	public OI(){
		
		climbUpButton.whileHeld(new CMDClimb(1.0));
		
		gearIntake.whileHeld(new CMDIntakeGear(1.0));
		gearOuttake.whileHeld(new CMDIntakeGear(-0.5));
		
	}
	
	public double gearIntakePower(){
		if(gearIntake.get()){
			return 1;
		}
		else if(gearOuttake.get()){
			return -0.5;
		}
		return 0;
	}
	
	public boolean isSlowed(){
		return slowButton.get();
	}
	
	public boolean isReversed(){
		return reverseButton.get();
	}

	
	public double getJoystickX(Joystick j){
		
		if(Math.abs(j.getX()) > 0.05){
			return j.getX()*j.getX() * Math.signum(j.getX());
		}
		
		return 0;
	}
	public double getJoystickY(Joystick j){
		
		if(Math.abs(j.getY()) > 0.05){
			return j.getY()*j.getY() * Math.signum(j.getY());
		}
		
		return 0;
	}
	public double getJoystickZ(Joystick j){
		
		return j.getZ();
	}
	public double getJoystickTwist(Joystick j){
		
		if(Math.abs(j.getTwist()) > 0.05){		
			return j.getTwist()*j.getTwist() * Math.signum(j.getTwist());		
		}
		
		return 0;
	}
}