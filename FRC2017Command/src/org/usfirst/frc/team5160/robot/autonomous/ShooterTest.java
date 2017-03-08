package org.usfirst.frc.team5160.robot.autonomous;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.commands.CMDIntakeIn;
import org.usfirst.frc.team5160.robot.commands.CMDShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShooterTest extends CommandGroup{
	public ShooterTest(){
		addSequential(new CMDShoot(Robot.shootVel), 2);
		addSequential(new UptakeAndShoot(Robot.shootVel),15);
	}
}
