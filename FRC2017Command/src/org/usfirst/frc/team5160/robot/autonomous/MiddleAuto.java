package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
import org.usfirst.frc.team5160.robot.commands.CMDGearPlace;
import org.usfirst.frc.team5160.robot.commands.CMDIntakeGear;
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
		double dm = Robot.BASE.inchToEncoderTick(1);
		addSequential(new CMDAutoTankDrive(dm*64*80,0,1),1.45); //0.5
	//	addSequential(new CMDIntakeGear(-0.15));
	//	addSequential(new CMDGearPlace(), 1.5);
	}
}
