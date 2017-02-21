package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDAutoMecanumDrive;
import org.usfirst.frc.team5160.robot.commands.CMDAutoRotate;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
import org.usfirst.frc.team5160.robot.commands.CMDRevShooter;
import org.usfirst.frc.team5160.robot.commands.CMDShoot;
import org.usfirst.frc.team5160.robot.commands.CMDTrackBoiler;
import org.usfirst.frc.team5160.robot.commands.CMDTrackGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleAuto extends CommandGroup{
	public MiddleAuto(){
		addSequential(new CMDAutoTankDrive(10000,10000));
		//addSequential(new CMDAutoTankDrive(57,57)); //2s
	/*	addSequential(new CMDTrackGear(), 1); //1s
		addSequential(new CMDAutoTankDrive(12,12)); //0.5s
		addSequential(new CMDAutoMecanumDrive(0.5, 0.5, 90, 0.5, true),2); //2s
		addParallel(new CMDTrackBoiler(), 1); //1s
		addParallel(new CMDRevShooter(1),1);
		addSequential(new CMDShoot(1)); //2s */
		
	}
}
