package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team5160.robot.commands.CMDTeleOpMecanumDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
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
	private CANTalon frontLeft;
	private CANTalon backLeft; 
	private CANTalon frontRight;
	private CANTalon backRight;
	
	//Declare drive base
	private RobotDrive driveBase;
	
	//Declare gyro
	private ADXRS450_Gyro gyro;
	
	//Positions for auto drive
	private double targetLeftPos; 
	private double targetRightPos; 
	private double zeroLeftPos;
	private double zeroRightPos;
	private static final double INCH_TO_TICK = 1023d/(Math.PI*6d);///1023 ticks per pi*6 inches
	private static final double TICK_TO_INCH = 1d/INCH_TO_TICK;
	public Base(){
		
		//Init motors
		frontLeft = new CANTalon(RobotMap.FRONT_LEFT_CIM);
		backLeft = new CANTalon(RobotMap.BACK_LEFT_CIM);
		frontRight = new CANTalon(RobotMap.FRONT_RIGHT_CIM);
		backRight = new CANTalon(RobotMap.BACK_RIGHT_CIM);
		
		//Setup inversions for teleop mecanum drive
		frontRight.setInverted(true);
		backRight.setInverted(true);
    	frontLeft.setInverted(false);
    	backLeft.setInverted(false);
		
		//Call init on all motors
		initMotor(backLeft);
		initMotor(backRight);
		initMotor(frontLeft);
		initMotor(frontRight);
		//Init the wpi drive
		driveBase = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
		
		//Init gyro
		gyro = new ADXRS450_Gyro();
		
	}

    public void initDefaultCommand() {
    	//set default command as TeleOp Drive
    	setDefaultCommand(new CMDTeleOpMecanumDrive());
    	
    }
    public void mecanumDrive(double x, double y, double rotation){
    	//Makesure the motors are on teleop mode
    	ensureMechanumTeleOp();
    	frontRight.setInverted(true);
		backRight.setInverted(true);
    	frontLeft.setInverted(false);
    	backLeft.setInverted(false);
    	
    	//Cartesian mecanum drive, no value on gyro angle
    	driveBase.mecanumDrive_Cartesian(x, y, rotation, 0);
    }
    public void mecanumDriveField(double x, double y, double rotation){
    	//Makesure the motors are on teleop mode
    	ensureMechanumTeleOp();
    	frontRight.setInverted(true);
		backRight.setInverted(true);
    	frontLeft.setInverted(false);
    	backLeft.setInverted(false);
    	
    	//Cartesian mecanum drive, with respect to the gyro angle
    	driveBase.mecanumDrive_Cartesian(x, y, rotation, gyro.getAngle());
    }
    public void tankDrive(double leftValue, double rightValue){
    	//Tank drive is inverted
		frontRight.setInverted(false);
		backRight.setInverted(false);
		
		frontLeft.setInverted(true);
		backLeft.setInverted(true);
    	
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
    private void initMotor(CANTalon motor){
    	motor.setVoltageRampRate(24.0);
    	motor.configNominalOutputVoltage(-0f, 0f);
    	motor.configPeakOutputVoltage(-12, 12);
    	motor.changeControlMode( TalonControlMode.PercentVbus);
    	motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	motor.setAllowableClosedLoopErr(100);
    	motor.setProfile(0);

    }
   private void ensurePositionTank(){
	   //Ensure it is ready for position based tank drive.
    	System.out.println("Ensured position");
    	frontLeft.changeControlMode( TalonControlMode.PercentVbus);
    	frontRight.changeControlMode( TalonControlMode.PercentVbus);
    	backLeft.changeControlMode( TalonControlMode.PercentVbus); 
    	backRight.changeControlMode( TalonControlMode.PercentVbus);
    	setInvertAuto();
    }
    private void ensureMechanumTeleOp(){
    	//Ensure it is ready for driver control
    	frontLeft.changeControlMode(TalonControlMode.PercentVbus);
    	frontRight.changeControlMode(TalonControlMode.PercentVbus);
    	backLeft.changeControlMode( TalonControlMode.PercentVbus);
    	backRight.changeControlMode( TalonControlMode.PercentVbus);
    	setInvertTeleOp();
    }
    
    public void positionTankDriveSet(double dLeft, double dRight){
    	ensurePositionTank();
    	resetEncoders();
    	targetLeftPos = dLeft;
    	targetRightPos = dRight;
    }
    public void positionTankDriveExecute(){
    	//Sets the motors to their sides ramping power
    	frontLeft.set(rampingVelocity(frontLeft, targetLeftPos));
		frontRight.set(rampingVelocity(frontRight, targetRightPos));
		backLeft.set(rampingVelocity(frontLeft, targetLeftPos));
		backRight.set(rampingVelocity(frontRight, targetRightPos));
    }
    public void setInvertTeleOp(){
    	frontRight.setInverted(true);
		backRight.setInverted(true);
    	frontLeft.setInverted(false);
    	backLeft.setInverted(false);
    }
    public void setInvertAuto(){
    	//Auto is flipped from teleop since it is driving "backwards" (gear forwards)
    	frontRight.setInverted(false);
		backRight.setInverted(false);
    	frontLeft.setInverted(true);
    	backLeft.setInverted(true);
    }
    public double rampingVelocity(CANTalon motor, double target){
    	//Slow down the closer it is to its target.
    	if(Math.abs(Math.abs(frontLeft.getPosition())-Math.abs(target))<1200){
    		return RMath.sign(target)*0.2;
    	}
    	else if(Math.abs(Math.abs(frontLeft.getPosition())-Math.abs(target))<2400){
    		return RMath.sign(target)*0.4;
    	}
    	else if(Math.abs(Math.abs(frontLeft.getPosition())-Math.abs(target))<3600){
    		return RMath.sign(target)*0.6;
    	}
    	return 0.8;
    }
    public boolean positionTankDriveReached(){
    	if((Math.abs(frontLeft.getPosition()) - Math.abs(targetLeftPos)) >= 0 && (Math.abs(frontRight.getPosition()) - Math.abs(targetRightPos)) >= 0 ){
    		return true;
    	}
    	return false;
    }
    public static double inchToEncoderTick(double inches){
    	return INCH_TO_TICK*inches;
    }
    public static double feetToEncoderTick(double feet){
    	return inchToEncoderTick(12*feet);
    }

	public void printEncoders() {
		System.out.println(frontLeft.getEncPosition()+" , "+ frontRight.getEncPosition());
	}
	public void resetEncoders(){
		frontLeft.setEncPosition(0);
    	frontRight.setEncPosition(0);
    	backRight.setEncPosition(0);
    	backLeft.setEncPosition(0);
	}
	public double getAverageEncoder(){
		return (frontLeft.getEncPosition()+frontRight.getEncPosition())/2d;
	}
	
}