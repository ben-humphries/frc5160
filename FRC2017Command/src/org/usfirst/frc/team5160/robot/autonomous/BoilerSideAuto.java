package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.commands.CMDAutoRotate;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
import org.usfirst.frc.team5160.robot.commands.CMDTrackGear;
import org.usfirst.frc.team5160.robot.commands.CMDWait;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 * Places the gear on the boiler side, then tries to shoot. 
 */
public class BoilerSideAuto extends CommandGroup{
	//Drive forward 90 in, turn 30 ish, 2ft forward, stop 2 seconds, turn, shoot. 
	public BoilerSideAuto(){
		double dm = Robot.BASE.inchToEncoderTick(1);
		int multiplier = Robot.autoColorMultiplier(); //Multiplier for rotation
		addSequential(new CMDAutoTankDrive(dm*64*80,0,1),1);
		addSequential(new CMDAutoRotate(multiplier*60, 0.5),1.5);
		addSequential(new CMDTrackGear(),2.5);
		addSequential(new CMDAutoTankDrive(dm*64*80,0,1),1);
	}
}
