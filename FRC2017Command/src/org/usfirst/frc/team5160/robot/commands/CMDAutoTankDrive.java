package org.usfirst.frc.team5160.robot.commands;

import javax.swing.text.Position;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.subsystems.Base;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This allows the robot to move in encoded tank mode
 */

public class CMDAutoTankDrive extends Command{
	public CMDAutoTankDrive(double dist, double angle, double turn){
		requires(Robot.BASE);
		Robot.BASE.positionTankDriveSet(dist, angle, turn);
	}
	@Override
	protected void initialize(){
		Robot.BASE.setInvertAuto();
	}
	@Override 
	protected void execute(){

		Robot.BASE.positionTankDriveExecute();
		Robot.BASE.printEncoders();
	}
	@Override
	protected boolean isFinished() {
		return Robot.BASE.positionTankDriveFinished();
	}

}
