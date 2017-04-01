package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team5160.robot.commands.CMDTeleOpArcadeDrive;
import org.usfirst.frc.team5160.robot.commands.CMDTeleOpMecanumDrive;
import org.usfirst.frc.team5160.robot.commands.CMDTeleOpTankDrive;

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
	private double targetDistance; 
	private double targetAngle;
	private double turnPower;
	private static final double INCH_TO_TICK = 1023d/(Math.PI*6d);///1023 ticks per pi*6 inches
	private static final double TICK_TO_INCH = 1d/INCH_TO_TICK;
	private static final double ROT_ERROR = 3;
	
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
    	
    	
    	//Cartesian mecanum drive, no value on gyro angle
    	driveBase.mecanumDrive_Cartesian(x, y, rotation, 0);
    }
    public void mecanumDriveField(double x, double y, double rotation){
    	//Makesure the motors are on teleop mode
    	
    	
    	//Cartesian mecanum drive, with respect to the gyro angle
    	driveBase.mecanumDrive_Cartesian(x, y, rotation, gyro.getAngle());
    }
    public void tankDrive(double leftValue, double rightValue){
    	
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
    	motor.setVoltageRampRate(36.0);
    	motor.configNominalOutputVoltage(-0f, 0f);
    	motor.configPeakOutputVoltage(-12, 12);
    	motor.enableBrakeMode(true);
    	motor.changeControlMode( TalonControlMode.PercentVbus);
    	motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	motor.setProfile(0);

    }
   private void ensurePositionTank(){
	   //Ensure it is ready for position based tank drive.
    	System.out.println("Ensured position");
    	frontLeft.changeControlMode( TalonControlMode.PercentVbus);
    	frontRight.changeControlMode( TalonControlMode.PercentVbus);
    	backLeft.changeControlMode( TalonControlMode.PercentVbus); 
    	backRight.changeControlMode( TalonControlMode.PercentVbus);
    }
    private void ensureMechanumTeleOp(){
    	//Ensure it is ready for driver control
    	frontLeft.changeControlMode(TalonControlMode.PercentVbus);
    	frontRight.changeControlMode(TalonControlMode.PercentVbus);
    	backLeft.changeControlMode( TalonControlMode.PercentVbus);
    	backRight.changeControlMode( TalonControlMode.PercentVbus);
    }
    
    public void positionTankDriveSet(double dist, double angle, double rotationPower){
    	ensurePositionTank();
    	resetEncoders();
    	gyro.reset();
    	targetDistance = dist;
    	targetAngle = angle;
    	turnPower = rotationPower;
    }
    public void positionTankDriveExecute(){
    	driveBase.mecanumDrive_Cartesian(0, RMath.sign(targetDistance)*0.7, turnPower*RMath.sign(targetAngle-gyro.getAngle()), 0);
    }
    public boolean positionTankDriveFinished(){
    	return false;//(finishedMoving()&&finishedRotation());
    }
    
    private boolean finishedMoving(){
    	return false;//(-Math.abs(getAverageEncoder())+Math.abs(targetDistance)<400);
    }
    public static double inchToEncoderTick(double inches){
    	return INCH_TO_TICK*inches;
    }
    public static double feetToEncoderTick(double feet){
    	return inchToEncoderTick(12*feet);
    }

	public void printEncoders() {
		System.out.println(frontLeft.getEncPosition()+" , "+ frontRight.getEncPosition()+"+"+targetDistance);
	}
	public void resetEncoders(){
		frontLeft.setEncPosition(0);
    	frontRight.setEncPosition(0);
    	backRight.setEncPosition(0);
    	backLeft.setEncPosition(0);
	}
	public double getAverageEncoder(){
		return (Math.abs(backLeft.getEncPosition())+Math.abs(backRight.getEncPosition())+Math.abs(frontLeft.getEncPosition())+Math.abs(frontRight.getEncPosition()))/4d;
	}
	private boolean finishedRotation() {
		return(Math.abs(targetAngle-gyro.getAngle())<ROT_ERROR);
}
	double mQuickStopAccumulator;
	public void cheesyDrive(double throttle, double wheel, boolean isQuickTurn) {
		frontRight.setInverted(true);
		backRight.setInverted(true);
    	frontLeft.setInverted(true);
    	backLeft.setInverted(true);
        wheel = -handleDeadband(wheel, 0.02);
        throttle = handleDeadband(throttle, 0.02);
        
        double overPower;

        double angularPower;

        if(Robot.oi.isReversed()){
        	wheel = wheel/2;
        	throttle = throttle/1.5;
        }
        if (isQuickTurn) {
            driveBase.arcadeDrive(throttle, -wheel * Robot.Turn_Sensitivity);
        }
        else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * wheel * Robot.Turn_Sensitivity/2 - mQuickStopAccumulator;
            if (mQuickStopAccumulator > 1) {
                mQuickStopAccumulator -= 1;
            } else if (mQuickStopAccumulator < -1) {
                mQuickStopAccumulator += 1;
            } else {
                mQuickStopAccumulator = 0.0;
            }
            
            double rightPwm = throttle - angularPower;
            double leftPwm = throttle + angularPower;
            if (leftPwm > 1.0) {
                rightPwm -= overPower * (leftPwm - 1.0);
                leftPwm = 1.0;
            } else if (rightPwm > 1.0) {
                leftPwm -= overPower * (rightPwm - 1.0);
                rightPwm = 1.0;
            } else if (leftPwm < -1.0) {
                rightPwm += overPower * (-1.0 - leftPwm);
                leftPwm = -1.0;
            } else if (rightPwm < -1.0) {
                leftPwm += overPower * (-1.0 - rightPwm);
                rightPwm = -1.0;
            }
            driveBase.tankDrive(leftPwm, rightPwm);
        }

        
    }
	 public double handleDeadband(double val, double deadband) {
	        return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
	    }
}