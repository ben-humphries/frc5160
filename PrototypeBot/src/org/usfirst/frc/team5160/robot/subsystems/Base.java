package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.RobotMap;

import org.usfirst.frc.team5160.robot.commands.CMDTeleOpMecanumDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CANSpeedController.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Base extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	//TODO: Install libraries for CANTalon
	
	//Declare motors
	private Talon frontLeft;
	private Talon backLeft; 
	private Talon frontRight;
	private Talon backRight;
	
	//Declare drive base
	private RobotDrive driveBase;
	
	//Declare gyro
	private ADXRS450_Gyro gyro;
	
	//Positions for auto drive
	private double targetLeftPos; 
	private double targetRightPos; 
	private double zeroLeftPos;
	private double zeroRightPos;
	
	public Base(){
		
		//Init motors
		frontLeft = new Talon(RobotMap.FRONT_LEFT_CIM);
		backLeft = new Talon(RobotMap.BACK_LEFT_CIM);
		frontRight = new Talon(RobotMap.FRONT_RIGHT_CIM);
		backRight = new Talon(RobotMap.BACK_RIGHT_CIM);
		
	//	frontRight.setInverted(true);
	//	backRight.setInverted(true);
		
		//Call init on all motors
		initMotor(backLeft);
		initMotor(backRight);
		initMotor(frontLeft);
		initMotor(frontRight);
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
//    	frontLeft.setInverted(false);
  //  	backLeft.setInverted(false);
    	
    	//Cartesian mecanum drive, with respect to the gyro angle
    	driveBase.mecanumDrive_Cartesian(x, y, rotation, 0);
    }
    public void mecanumDriveField(double x, double y, double rotation){
    //	frontLeft.setInverted(false);
    //	backLeft.setInverted(false);
    	
    	//Cartesian mecanum drive, with respect to the gyro angle
    	driveBase.mecanumDrive_Cartesian(x, y, rotation, gyro.getAngle());
    }
    public void tankDrive(double leftValue, double rightValue){
    	
	//	frontRight.setInverted(false);
	//	backRight.setInverted(false);
		
	//	frontLeft.setInverted(true);
	//	backLeft.setInverted(true);
    	
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
    //Code for initializing the motors at init
    private void initMotor(Talon motor){

    }
  
   
   
    public void positionTankDriveSet(double dLeft, double dRight){
    	targetLeftPos = dLeft;
    	targetRightPos = dRight;
    }
    public void positionTankDriveExecute(){
    		frontLeft.set(targetLeftPos);
    		frontRight.set(targetRightPos);

    }
    public boolean positionTankDriveReached(){
    	if((frontLeft.getPosition() - targetLeftPos) >= 0 && (frontRight.getPosition() - targetRightPos) >= 0 ){
    		return true;
    	}
    	return false;
    }
    public static double inchToEncoderTick(double inches){
    	return 1*inches;
    }
    public static double feetToEncoderTick(double feet){
    	return inchToEncoderTick(12*feet);
    }

	
	
}