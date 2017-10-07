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
 
	
	Joystick leftJoystick = new Joystick(RobotMap.LEFT_JOYSTICK);
	Joystick rightJoystick = new Joystick(RobotMap.RIGHT_JOYSTICK);
	
	Joystick operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
	
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
	
	public double getJoystickRotation(){		
			if(Math.abs(rightJoystick.getTwist()) > 0.05){		
				return rightJoystick.getTwist()*rightJoystick.getTwist() * Math.signum(rightJoystick.getTwist());		
			}		
			return 0;		
	}
	
	public double getLeftJoystickY(){
		if(Math.abs(leftJoystick.getY()) > 0.05){
			return leftJoystick.getY()*leftJoystick.getY() * Math.signum(leftJoystick.getY());
		}
		return 0;
	}
	
	public double getRightJoystickY(){
		if(Math.abs(rightJoystick.getY()) > 0.05){
			return rightJoystick.getY()*rightJoystick.getY() * Math.signum(rightJoystick.getY());
		}
		return 0;
	}
	
	public double getRightJoystickX(){
		if(Math.abs(rightJoystick.getX()) > 0.05){
			return rightJoystick.getX()*rightJoystick.getX() * Math.signum(rightJoystick.getX());
		}
		return 0;
	}
	
	public double getOperatorJoystickZ(){
		return ((operatorJoystick.getZ() - 1.0) / 2.0);
	}
	
	public double getOperatorJoystickY(){
		if(Math.abs(operatorJoystick.getY()) > 0.05){
			return operatorJoystick.getY()*operatorJoystick.getY() * Math.signum(operatorJoystick.getY());
		}
		return 0;
	}
}