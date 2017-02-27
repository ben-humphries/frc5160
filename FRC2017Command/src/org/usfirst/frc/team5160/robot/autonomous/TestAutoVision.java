package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.commands.CMDTrackBoiler;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestAutoVision extends CommandGroup{
	public TestAutoVision(){
		addParallel(new CMDTrackBoiler());
	}
}
