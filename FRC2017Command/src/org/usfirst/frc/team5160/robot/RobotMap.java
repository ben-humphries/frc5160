package org.usfirst.frc.team5160.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	//Declare motor port numbers
	
	//TODO fix the motor ports
	public static int FRONT_LEFT_CIM = 4;
	public static int BACK_LEFT_CIM = 2;
	public static int FRONT_RIGHT_CIM = 3;
	public static int BACK_RIGHT_CIM = 1;
	
	public static int SHOOTER_775_1 = 10;
	public static int SHOOTER_775_2 = 11;
	
	public static int INTAKE_BAG_1 = 0;
	public static int INTAKE_BAG_2 = 1;
	
	public static int GEAR_CIM = 9;
	
	public static int CLIMBER_775_1 = 5;
	public static int CLIMBER_775_2 = 6;
	public static int CLIMBER_CIM = 7;
	public static int CLIMBER_SWITCH = 13;
	
	public static int CAMERA_CLIMB = 1;
	
	//Declare joystick port number
	public static int JOYSTICK = 0;
	
	public static int TANK_JOYSTICK = 2;
}
