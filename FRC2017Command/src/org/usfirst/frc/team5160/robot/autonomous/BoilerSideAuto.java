package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
import org.usfirst.frc.team5160.robot.commands.CMDWait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BoilerSideAuto extends CommandGroup{
	//Drive forward 7.5 ft, turn 45 ish, 2ft forward, stop 2 seconds, turn, shoot. 
	public BoilerSideAuto(){
		addSequential(new CMDAutoTankDrive(100,100));
		addSequential(new CMDAutoTankDrive(100,50));
		addSequential(new CMDWait(2));
		addSequential(new CMDAutoTankDrive(100, 100));
	}
}
