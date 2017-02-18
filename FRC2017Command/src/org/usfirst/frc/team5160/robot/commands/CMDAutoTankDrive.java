package org.usfirst.frc.team5160.robot.commands;

import javax.swing.text.Position;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CMDAutoTankDrive extends Command{
	private double targetLeft, targetRight;
	public CMDAutoTankDrive(double dLeft,double dRight){
		requires(Robot.BASE);
		targetLeft = dLeft;
		targetRight = dRight; 
		Robot.BASE.positionTankDriveSet(dLeft, dRight);
	}
	@Override 
	protected void initialize(){
		Robot.BASE.positionTankDriveExecute();
	}
	@Override
	protected boolean isFinished() {
		return Robot.BASE.positionTankDriveReached();
	}

}
