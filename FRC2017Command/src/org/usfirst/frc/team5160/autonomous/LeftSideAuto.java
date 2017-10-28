package org.usfirst.frc.team5160.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDDrivePID;
import org.usfirst.frc.team5160.robot.commands.CMDTurnPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftSideAuto extends CommandGroup{
	public LeftSideAuto(){
			addSequential(new CMDDrivePID(98.5-20),4);
			addSequential(new CMDTurnPID(60),3);
			addSequential(new CMDDrivePID(98.5-20),5);
	}
}
