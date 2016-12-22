package org.usfirst.frc.team5160.robot.activities;
import org.usfirst.frc.team5160.robot.Robot;
public abstract class Activity {
	/**
	 * Called when the robot gets initialized
	 */
	public abstract void init(); 
	/**
	 * Called periodically 
	 * @param deltaTime The time since the last loop
	 */
	public abstract void loopAuto(double deltaTime);
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
