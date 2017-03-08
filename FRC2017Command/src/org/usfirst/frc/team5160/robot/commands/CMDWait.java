package org.usfirst.frc.team5160.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Has the robot wait until the time has ellapsed.
 */

public class CMDWait extends Command{
	private double timeToWait;
	public CMDWait(double seconds){
		timeToWait = seconds;
	}
	@Override
	protected boolean isFinished() {
		return timeToWait <= timeSinceInitialized();
	}

}
