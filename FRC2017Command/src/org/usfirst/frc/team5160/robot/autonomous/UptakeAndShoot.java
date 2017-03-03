package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDIntakeIn;
import org.usfirst.frc.team5160.robot.commands.CMDShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class UptakeAndShoot extends CommandGroup{
	public UptakeAndShoot(double speed){
		addParallel(new CMDIntakeIn(1,true));
		addParallel(new CMDShoot(speed));
	}
}
