package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CMDMoveForwards extends Command{

	protected void execute(){
		Robot.BASE.mecanumDrive(0, 0.55, 0);	
		}
	@Override
	protected boolean isFinished() {
		return false;
	}

}
