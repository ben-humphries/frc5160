package org.usfirst.frc.team5160.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDDrivePID;
import org.usfirst.frc.team5160.robot.commands.CMDTurnPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleAuto extends CommandGroup{
	public MiddleAuto(){
			addSequential(new CMDDrivePID(90),10);
			}
}
