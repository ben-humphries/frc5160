package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.commands.CMDAutoRotate;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
import org.usfirst.frc.team5160.robot.commands.CMDTrackGear;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 * places the gear on the far side. 
 */
public class FarSideAuto extends CommandGroup{
	public FarSideAuto(){
		int multiplier = Robot.autoColorMultiplier(); //Multiplier for rotation
		//Moves forwards 90 inches, rotates, tracks gear, drives a foot.
		addSequential(new CMDAutoTankDrive(90,90)); //2s
		addSequential(new CMDAutoRotate(multiplier*30, 0.5,false)); //1s
		addSequential(new CMDTrackGear(), 2); //1s
		addSequential(new CMDAutoTankDrive(12,12)); //0.5s
		addSequential(new CMDAutoTankDrive(-4,-4));
	}
}
