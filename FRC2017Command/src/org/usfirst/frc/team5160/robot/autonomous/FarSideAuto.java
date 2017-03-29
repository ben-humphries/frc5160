package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.commands.CMDAutoRotate;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 * places the gear on the far side. 
 */
public class FarSideAuto extends CommandGroup{
	public FarSideAuto(){
		double dm = Robot.BASE.inchToEncoderTick(1);
		int multiplier = Robot.autoColorMultiplier(); //Multiplier for rotation
		addSequential(new CMDAutoTankDrive(dm*64*80,0,1),1.45);
		addSequential(new CMDAutoRotate(-60, 1));
		addSequential(new CMDAutoTankDrive(dm*64*80, 0, 1));
	}
}
