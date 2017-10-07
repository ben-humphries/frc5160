package org.usfirst.frc.team5160.robot.subsystems;

import org.opencv.photo.CalibrateCRF;
import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.RobotMap;
import org.usfirst.frc.team5160.robot.commands.CMDGearMech;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearMechanism extends Subsystem {
    

	private CANTalon motor1;
	private CANTalon motor2;
	
	
	public GearMechanism(){
		
		motor1 = new CANTalon(RobotMap.GEAR_ROTATE);
		motor1.enableBrakeMode(true);
		motor1.setInverted(false);
		motor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor2 = new CANTalon(RobotMap.GEAR_INTAKE);
	}
	
	public void rotate(double speed){
		motor1.set(speed);
		if(motor1.isFwdLimitSwitchClosed()&&speed<0){
			motor1.set(0);
		}
	}
	public void intake(double speed){
		motor2.set(speed);
	}
	
	public void stopMotors(){
		motor1.set(0);
		motor2.set(0);
	}
	public boolean moveDown(){
		int error = Math.abs(motor1.getEncPosition())-Robot.Gear_Down_Value;
		motor1.set(RMath.clamp(-0.3, 0.3, 0.02*error));
		return error < 4;
		
	}
	public boolean moveUp(){
		motor1.set(-0.4);
		if(motor1.isFwdLimitSwitchClosed()){
			return true;
		}
		return false;
	}
	public boolean hold(int value){
		int error = Math.abs(motor1.getEncPosition())-value;
		motor1.set(RMath.clamp(-1, 1, 0.02*error));
		return Math.abs(error) < 4;
	}
	public boolean calibrate(){
		motor1.set(-0.4);
		if(motor1.isFwdLimitSwitchClosed()){
			motor1.setEncPosition(0);
			motor1.set(0);
			return true;
		}
		return false;
	}
	@Override
    public void initDefaultCommand() {
		setDefaultCommand(new CMDGearMech());
    }
	public boolean isTooFarDown(){
		return Math.abs(motor1.getEncPosition()) > Robot.Gear_Down_Value;
	}
}