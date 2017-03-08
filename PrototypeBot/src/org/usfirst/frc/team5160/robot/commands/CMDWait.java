package org.usfirst.frc.team5160.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

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
