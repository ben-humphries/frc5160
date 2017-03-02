package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Strafe without changing angles
 */
public class CMDAutoMecanumStraightStrafe extends Command{
	private double x,y;
	public CMDAutoMecanumStraightStrafe(double x, double y){
		requires(Robot.BASE);
		this.x = x;
		this.y = y;
	}
	 @Override
	protected void execute() {
		 Robot.BASE.mecanumDriveField(x, y, 0);
	 }
	@Override
	protected boolean isFinished() {
		return false;
	}
}