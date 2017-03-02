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
		
		frontRight.setInverted(true);
		backRight.setInverted(true);
		
		//Call init on all motors
		initMotor(backLeft);
		initMotor(backRight);
		initMotor(frontLeft);
		initMotor(frontRight);
		//Init base
		driveBase = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
		
		//Init gyro
		gyro = new ADXRS450_Gyro();
		
	}

    public void initDefaultCommand() {
    	//set default command as TeleOp Drive
    	setDefaultCommand(new CMDTeleOpMecanumDrive());
    	
    }
    public void mecanumDrive(double x, double y, double rotation){
    	ensureMechanumTeleOp();
    	frontRight.setInverted(true);
		backRight.setInverted(true);
    	frontLeft.setInverted(false);
    	backLeft.setInverted(false);
    	
    	//Cartesian mecanum drive, with respect to the gyro angle
    	driveBase.mecanumDrive_Cartesian(x, y, rotation, 0);
    }
    public void mecanumDriveField(double x, double y, double rotation){
    	ensureMechanumTeleOp();
    	frontRight.setInverted(true);
		backRight.setInverted(true);
    	frontLeft.setInverted(false);
    	backLeft.setInverted(false);
    	
    	//Cartesian mecanum drive, with respect to the gyro angle
    	driveBase.mecanumDrive_Cartesian(x, y, rotation, gyro.getAngle());
    }
    public void tankDrive(double leftValue, double rightValue){
    	
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
//    	motor.setPID(Robot.driveD, Robot.driveI, Robot.driveD );
//    	motor.setF(Robot.driveF);
    }
   private void ensurePositionTank(){
    	System.out.println("Ensured position");
  /*  	frontLeft.changeControlMode( TalonControlMode.Position);
    	frontRight.changeControlMode( TalonControlMode.Position);
    	backLeft.changeControlMode( TalonControlMode.Follower); 
    	backRight.changeControlMode( TalonControlMode.Follower);
    	backLeft.set(RobotMap.FRONT_LEFT_CIM);
    	backRight.set(RobotMap.FRONT_RIGHT_CIM);*/
    	frontLeft.changeControlMode( TalonControlMode.PercentVbus);
    	frontRight.changeControlMode( TalonControlMode.PercentVbus);
    	backLeft.changeControlMode( TalonControlMode.PercentVbus); 
    	backRight.changeControlMode( TalonControlMode.PercentVbus);
    	setInvertAuto();
    }
    private void ensureMechanumTeleOp(){
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
    /*		frontLeft.set(targetLeftPos);
    		frontRight.set(targetRightPos);
    		backLeft.set(frontLeft.getDeviceID());
    		backRight.set(frontRight.getDeviceID());*/
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
    	frontRight.setInverted(false);
		backRight.setInverted(false);
    	frontLeft.setInverted(true);
    	backLeft.setInverted(true);
    }
    public double rampingVelocity(CANTalon motor, double target){
    	if(TICK_TO_INCH*Math.abs(Math.abs(frontLeft.getPosition())-Math.abs(target))<6){
    		return RMath.sign(target)*0.2;
    	}
    	else if(TICK_TO_INCH*Math.abs(Math.abs(frontLeft.getPosition())-Math.abs(target))<12){
    		return RMath.sign(target)*0.4;
    	}
    	else if(TICK_TO_INCH*Math.abs(Math.abs(frontLeft.getPosition())-Math.abs(target))<24){
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