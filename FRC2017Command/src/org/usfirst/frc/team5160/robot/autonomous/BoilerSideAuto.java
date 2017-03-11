package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.commands.CMDAutoMecanumDrive;
import org.usfirst.frc.team5160.robot.commands.CMDAutoRotate;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
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
		addSequential(new CMDAutoTankDrive(dm*90,dm*90),5); //2s
		addSequential(new CMDAutoRotate(multiplier*-30, 0.5,false)); //1s
//		addSequential(new CMDTrackGear(), 1.5); //1s
		addSequential(new CMDAutoTankDrive(dm*18,dm*18)); //0.5s
		addSequential(new CMDAutoTankDrive(dm*-4,dm*-4));
	}
}
