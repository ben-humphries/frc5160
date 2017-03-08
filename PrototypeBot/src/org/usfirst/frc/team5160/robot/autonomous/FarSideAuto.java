package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDAutoRotate;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
import org.usfirst.frc.team5160.robot.commands.CMDTrackGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FarSideAuto extends CommandGroup{
	public FarSideAuto(){
		addSequential(new CMDAutoTankDrive(10000,10000));
		//addSequential(new CMDAutoTankDrive(90,90)); //2s
		/*addSequential(new CMDAutoRotate(-30, 0.5,false)); //1s
		addSequential(new CMDTrackGear(), 1); //1s
		addSequential(new CMDAutoTankDrive(12,12)); //0.5s*/
	}
}
