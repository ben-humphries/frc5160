package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDAutoTankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestAutoEncoders extends CommandGroup{
	public TestAutoEncoders(){
		addSequential(new CMDAutoTankDrive(90,90));
	}
}
