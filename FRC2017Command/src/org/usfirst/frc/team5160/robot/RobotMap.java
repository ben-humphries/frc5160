package org.usfirst.frc.team5160.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//drive motors
	public static int FRONT_LEFT_CIM = 1;
	public static int BACK_LEFT_CIM = 2;
	public static int FRONT_RIGHT_CIM = 3;
	public static int BACK_RIGHT_CIM = 4;
	
	//gear mech
	public static int GEAR_BAG_1 = 8;
	public static int GEAR_BAG_2 = 9;
	public static int GEAR_INTAKE = 0; //PWM
	
	//climber
	public static int CLIMBER_775_1 = 5;
	public static int CLIMBER_775_2 = 6;
	public static int CLIMBER_CIM = 7;
	public static int CLIMBER_SWITCH = 13; //not in use
	
	//cameras
	public static int CAMERA_CLIMB = 1; //gear camera
	public static int CAMERA_INTAKE = 2;
	public static int CAMERA_SHOOT = 3;
	
	//joysticks
	public static int JOYSTICK = 0;
	public static int TANK_JOYSTICK = 1;
	public static int OPERATOR_JOYSTICK = 2;
}