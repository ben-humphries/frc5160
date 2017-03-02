package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.commands.CMDAutoMecanumDrive;
import org.usfirst.frc.team5160.robot.commands.CMDAutoMecanumStraightStrafe;
import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;
import org.usfirst.frc.team5160.robot.commands.CMDRevShooter;
import org.usfirst.frc.team5160.robot.commands.CMDShoot;
import org.usfirst.frc.team5160.robot.commands.CMDTrackBoiler;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Runs to the hopper to get balls and then shoots em' all to the boiler. 
 * 
 * Moves forwards a distance, strafes to the side triggering hopper and intaking, then starts to shoot. 
 */
public class HopperShooterAuto extends CommandGroup{
	public HopperShooterAuto(){
		int multiplier = Robot.autoColorMultiplier(); //Multiplier for rotation
		addSequential(new CMDAutoTankDrive(75,75)); //1.5s
		addSequential(new CMDAutoMecanumStraightStrafe(1, 0),1); //1s
		addParallel(new CMDAutoMecanumStraightStrafe(-0.2, 0), 0.4);
		addParallel(new CMDShoot(2000), 1.5);
		addParallel(new CMDTrackBoiler(), 1.5); //2s
	}
}
