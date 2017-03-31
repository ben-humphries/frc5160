package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDAutoRotate;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
import org.usfirst.frc.team5160.robot.commands.CMDTrackGear;
import org.usfirst.frc.team5160.robot.commands.CMDTrackGearGround;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAuto extends CommandGroup {
	public TwoGearAuto(){
		addSequential(new CMDAutoTankDrive(64*80,0,1),1.45);
		addSequential(new CMDAutoTankDrive(64*80,0,-1),1.3);
		addSequential(new CMDAutoRotate(90, 0.5),1);
		addSequential(new CMDTrackGearGround(),1.5);
		addSequential(new CMDAutoTankDrive(64*80,0,-1),0.3);
		addSequential(new CMDAutoRotate(-90, 0.5),1);
		addSequential(new CMDTrackGear(),2);
		
	}
}
