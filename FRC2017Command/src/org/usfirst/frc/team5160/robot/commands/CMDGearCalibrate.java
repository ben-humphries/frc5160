package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CMDGearCalibrate extends Command{
	public CMDGearCalibrate() {
		requires(Robot.GEAR_MECHANISM);
	}
	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
