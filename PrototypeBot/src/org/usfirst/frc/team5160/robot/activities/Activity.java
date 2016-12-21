package org.usfirst.frc.team5160.robot.activities;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.Robot.AutoMode;
public abstract class Activity {
	enum OPMode{AUTO_ONLY, TELE_ONLY, AUTO_TELE}
	public OPMode mode; 
	/**
	 * Called when the robot gets initialized
	 */
	public abstract void init(); 
	
	public abstract void loopAuto(double deltaTime, AutoMode mode);
	/**
	 * Called periodically 
	 * @param deltaTime The time since the last loop
	 */
	public abstract void loopTele(double deltaTime);
	/**
	 * Called when the robot is to stop
	 */
	public abstract void stop(); 
}
