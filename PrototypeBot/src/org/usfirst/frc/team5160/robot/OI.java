package org.usfirst.frc.team5160.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team5160.robot.commands.CMDTeleOpMecanumDrive;
import org.usfirst.frc.team5160.robot.commands.CMDToggleCamera;

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
	
	//current drive mode: true is mecanum, false is tank
	boolean currentTeleOpDriveMode = true;
	
	Joystick joystick = new Joystick(RobotMap.JOYSTICK);
	Joystick operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
	Joystick tankJoystick = new Joystick(RobotMap.TANK_JOYSTICK);
	
	//Drive joystick
	Button cameraButton = new JoystickButton(joystick, 3);
	
	//Tank joystick
	//--
	
	//Operator joystick
	
	public OI(){
		
		
		
		cameraButton.whenPressed(new CMDToggleCamera());
	}
	
	//getter methods for the squared movement
	public double getJoystickX(){
		if(Math.abs(joystick.getX()) > 0.05){
			return joystick.getX()*joystick.getX() * Math.signum(joystick.getX());
		}
		return 0;
	}
	public double getJoystickY(){
		if(Math.abs(joystick.getY()) > 0.05){
			return joystick.getY()*joystick.getY() * Math.signum(joystick.getY());
		}
		return 0;
	}
	public double getJoystickRotation(){
		//if(Math.abs(joystick.getTwist()) > 0.05){
		//	return //joystick.getTwist()*joystick.getTwist() * Math.signum(joystick.getTwist()) / 2;
		//}
		return -joystick.getRawAxis(2) +  joystick.getRawAxis(3) + joystick.getRawAxis(4);
	}
	
	public double getTankJoystickY(){
		return tankJoystick.getY()*tankJoystick.getY() * Math.signum(tankJoystick.getY());
	}
	public double getOperatorJoystickZ(){
		return ((operatorJoystick.getZ() - 1.0) / 2.0);
	}
}