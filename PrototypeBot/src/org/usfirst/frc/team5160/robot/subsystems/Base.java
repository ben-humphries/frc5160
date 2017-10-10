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
import edu.wpi.first.wpilibj.Spark;
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
	private static final double INCH_TO_TICK = 1023d/(Math.PI*6d);///1023 ticks per pi*6 inches
	private static final double TICK_TO_INCH = 1d/INCH_TO_TICK;
	public Base(){
		
		//Init motors
		frontLeft = new Talon(RobotMap.FRONT_LEFT_CIM);
		backLeft = new Talon(RobotMap.BACK_LEFT_CIM);
		frontRight = new Talon(RobotMap.FRONT_RIGHT_CIM);
		backRight = new Talon(RobotMap.BACK_RIGHT_CIM);
		
		
		//Call init on all motors
		initMotor(backLeft);
		initMotor(backRight);
		initMotor(frontLeft);
		initMotor(frontRight);
		//Init the wpi drive
		driveBase = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
		
		//Init gyro
		gyro = new ADXRS450_Gyro();
		
		
		frontRight.setInverted(false);
		backRight.setInverted(false);
		
		frontLeft.setInverted(true);
		backLeft.setInverted(true);
		
	}

    public void initDefaultCommand() {
    	//set default command as TeleOp Drive
    	setDefaultCommand(new CMDTeleOpMecanumDrive());
    	
    }
    public void mecanumDrive(double x, double y, double rotation){
    	//Makesure the motors are on teleop mode
    	frontRight.setInverted(true);
		backRight.setInverted(true);
    	frontLeft.setInverted(false);
    	backLeft.setInverted(false);
    	
    	//Cartesian mecanum drive, no value on gyro angle
    	driveBase.mecanumDrive_Cartesian(x, y, rotation, 0);
    }
    public void mecanumDriveField(double x, double y, double rotation){
    	//Makesure the motors are on teleop mode
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
    public void arcadeDrive(double power, double rotation){
    	//Tank drive is inverted
    	frontRight.setInverted(false);
		backRight.setInverted(false);
		frontLeft.setInverted(true);
		backLeft.setInverted(true);
    	
    	//Tank drive
    	driveBase.arcadeDrive(power, rotation);
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
	
	double mQuickStopAccumulator;
    public static final double kThrottleDeadband = 0.02;
    private static final double kWheelDeadband = 0.02;
    private static final double kTurnSensitivity = 1.0;

    public void cheesyDrive(double throttle, double wheel, boolean isQuickTurn) {

        wheel = handleDeadband(wheel, kWheelDeadband);
        throttle = handleDeadband(throttle, kThrottleDeadband);

        double overPower;

        double angularPower;

        if (isQuickTurn) {
            if (Math.abs(throttle) < 0.2) {
                double alpha = 0.1;
                mQuickStopAccumulator = (1 - alpha) * mQuickStopAccumulator + alpha * wheel * 2;
            }
            overPower = 1.0;
            angularPower = wheel;
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * wheel * kTurnSensitivity - mQuickStopAccumulator;
            if (mQuickStopAccumulator > 1) {
                mQuickStopAccumulator -= 1;
            } else if (mQuickStopAccumulator < -1) {
                mQuickStopAccumulator += 1;
            } else {
                mQuickStopAccumulator = 0.0;
            }
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

    public double handleDeadband(double val, double deadband) {
        return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
    }
}