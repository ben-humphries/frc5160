package org.usfirst.frc.team5160.robot;

import org.usfirst.frc.team5160.robot.commands.CMDClimb;
import org.usfirst.frc.team5160.robot.commands.CMDClimbTilt;
import org.usfirst.frc.team5160.robot.commands.CMDToggleCamera;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	Joystick leftJoystick = new Joystick(RobotMap.DRIVE_JOYSTICK_LEFT);
	Joystick rightJoystick = new Joystick(RobotMap.DRIVE_JOYSTICK_RIGHT);
	Joystick operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
	
	//Drive joystick
	Button slowButton = new JoystickButton(leftJoystick, 1),
				reverseButton = new JoystickButton(rightJoystick, 1),
	
	//Operator joystick
	Button climbUpButton = new JoystickButton(operatorJoystick, 3),
				climbForwardButton = new JoystickButton(operatorJoystick, 4),
				climbBackwardButton = new JoystickButton(operatorJoystick, 5),
				gearIntake = new JoystickButton(operatorJoystick, 1),
				gearOuttake = new JoystickButton(operatorJoystick, 2); //maybe use different button?
	
	public OI(){
		
		climbUpButton.whileHeld(new CMDClimb(1.0));
		climbForwardButton.whileHeld(new CMDClimbTilt(0.5));
		climbBackwardButton.whileHeld(new CMDClimbTilt(-0.5));
		
		gearIntake.whileHeld();
		gearOuttake.whileHeld();
		
	}
	
	public boolean isSlowed(){
		return slowButton.get();
	}
	public boolean isReversed(){
		return reverseButton.get();
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