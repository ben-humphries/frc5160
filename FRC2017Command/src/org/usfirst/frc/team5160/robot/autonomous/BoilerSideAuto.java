package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDAutoMecanumDrive;
import org.usfirst.frc.team5160.robot.commands.CMDAutoRotate;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
import org.usfirst.frc.team5160.robot.commands.CMDRevShooter;
import org.usfirst.frc.team5160.robot.commands.CMDRotateBase;
import org.usfirst.frc.team5160.robot.commands.CMDShoot;
import org.usfirst.frc.team5160.robot.commands.CMDTrackBoiler;
import org.usfirst.frc.team5160.robot.commands.CMDTrackGear;
import org.usfirst.frc.team5160.robot.commands.CMDWait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BoilerSideAuto extends CommandGroup{
	//Drive forward 90 in, turn 30 ish, 2ft forward, stop 2 seconds, turn, shoot. 
	public BoilerSideAuto(){
		addSequential(new CMDAutoTankDrive(90,90)); //2s
		addSequential(new CMDAutoRotate(30, 0.5,false)); //1s
		addSequential(new CMDTrackGear(), 1); //1s
		addSequential(new CMDAutoTankDrive(12,12)); //0.5s
		addSequential(new CMDWait(2)); // 2s
		addSequential(new CMDAutoMecanumDrive(0.5,0.5,180,0.5,true), 2); //2s
		addParallel(new CMDTrackBoiler(), 1); //1s
		addParallel(new CMDRevShooter(500),1);
		addSequential(new CMDShoot(500)); //6s 
	}
}
