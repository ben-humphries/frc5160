package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.RobotMap;
import com.ctre.CANTalon;
import org.usfirst.frc.team5160.robot.commands.CMDTeleOpMecanumDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Base extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	//TODO: Install libraries for CANTalon
	
	//Declare motors
	private CANTalon frontLeft;
	private CANTalon backLeft; 
	private CANTalon frontRight;
	private CANTalon backRight;
	
	//Declare drive base
	private RobotDrive driveBase;
	
	//Declare gyro
	private ADXRS450_Gyro gyro;
	
	public Base(){
		
		//Init motors
		frontLeft = new CANTalon(RobotMap.FRONT_LEFT_CIM);
		backLeft = new CANTalon(RobotMap.BACK_LEFT_CIM);
		frontRight = new CANTalon(RobotMap.FRONT_RIGHT_CIM);
		backRight = new CANTalon(RobotMap.BACK_RIGHT_CIM);
		
		frontRight.setInverted(true);
		backRight.setInverted(true);
		
		//Sets ramp rate for each motor. Gradually changes speed resulting in less jerkiness.
		frontLeft.setVoltageRampRate(24.0);
		backLeft.setVoltageRampRate(24.0);
		frontRight.setVoltageRampRate(24.0);
		backRight.setVoltageRampRate(24.0);
		
		//Init base
		driveBase = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
		
		//Init gyro
		gyro = new ADXRS450_Gyro(/*port*/);
		
	}

    public void initDefaultCommand() {
    	//set default command as TeleOp Drive
    	setDefaultCommand(new CMDTeleOpMecanumDrive());
    	
    }
    public void mecanumDrive(double x, double y, double rotation){
    	
    	//Cartesian mecanum drive, with respect to the gyro angle
    	driveBase.mecanumDrive_Cartesian(x, y, rotation, gyro.getAngle());
    }
    public void tankDrive(double leftValue, double rightValue){
    	
		frontRight.setInverted(false);
		backRight.setInverted(false);
    	
    	//Tank drive
    	driveBase.tankDrive(leftValue, rightValue);
    }
    public void stopMotors(){
    	frontLeft.set(0);
    	frontRight.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    }
    public ADXRS450_Gyro getGyro(){
    	return gyro;
    }
}

