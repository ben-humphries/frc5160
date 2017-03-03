package org.usfirst.frc.team5160.robot.commands;

import javax.swing.text.Position;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.subsystems.Base;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This allows the robot to move in encoded tank mode
 */

public class CMDAutoTankDrive extends Command{
	public CMDAutoTankDrive(double dLeft,double dRight){
		requires(Robot.BASE);
		Robot.BASE.positionTankDriveSet(dLeft, dRight);
	}
	@Override
	protected void initialize(){
		Robot.BASE.setInvertAuto();
		Robot.BASE.positionTankDriveExecute();
	}
	@Override 
	protected void execute(){
		Robot.BASE.printEncoders();
	}
	@Override
	protected boolean isFinished() {
		return Robot.BASE.positionTankDriveReached();
	}

}
