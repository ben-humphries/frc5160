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
		double dm = Robot.BASE.inchToEncoderTick(1);
		int multiplier = Robot.autoColorMultiplier(); //Multiplier for rotation
		//Moves forwards 90 inches, rotates, tracks gear, drives a foot.
		addSequential(new CMDAutoTankDrive(dm*90,dm*90)); //2s
		addSequential(new CMDAutoRotate(multiplier*30, 0.5,false)); //1s
		addSequential(new CMDTrackGear(), 2); //1s
		addSequential(new CMDAutoTankDrive(dm*12,dm*12)); //0.5s
		addSequential(new CMDAutoTankDrive(dm*-4,dm*-4));
	}
}
