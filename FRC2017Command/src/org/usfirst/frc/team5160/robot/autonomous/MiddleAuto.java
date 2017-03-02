package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDAutoMecanumDrive;
import org.usfirst.frc.team5160.robot.commands.CMDAutoRotate;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
import org.usfirst.frc.team5160.robot.commands.CMDRevShooter;
import org.usfirst.frc.team5160.robot.commands.CMDShoot;
import org.usfirst.frc.team5160.robot.commands.CMDTrackBoiler;
import org.usfirst.frc.team5160.robot.commands.CMDTrackGear;
import org.usfirst.frc.team5160.robot.commands.CMDTrackGearDriveAuto;
import org.usfirst.frc.team5160.robot.commands.CMDWait;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 * Places the gear on the middle peg, then attempts to shoot.
 */
public class MiddleAuto extends CommandGroup{
	/**
	 * First uses the CMDTrackGearAuto to move forward while tracking gear, then switches to a move forward, waits, backs up, aims and shoots. 
	 */
	public MiddleAuto(){
		//Moves forwards/strafes to align to gear, drives 12 inches, waits for lift, moves closer to boiler, tracks boiler, shoots. 
		addSequential(new CMDTrackGearDriveAuto(57,0.05)); //2s
		addSequential(new CMDAutoTankDrive(12,12)); //0.5s
		addSequential(new CMDAutoTankDrive(-4,-4));
		addSequential(new CMDWait(2),2); //2s
		addSequential(new CMDAutoMecanumDrive(0.5, 0.5, 90, 0.5, true),2); //2s
		addParallel(new CMDTrackBoiler(), 1); //1s
		addParallel(new CMDRevShooter(1),1); //1
		addSequential(new CMDShoot(1), 7); //4s 
		
	}
}
