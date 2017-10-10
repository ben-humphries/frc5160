package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CMDTeleOpArcadeDrive extends Command{
	 
	public CMDTeleOpArcadeDrive() {
	    	requires(Robot.BASE);
	    }
	    @Override
	    protected void execute() {
	    		Robot.BASE.arcadeDrive(Robot.oi.getJoystickY(Robot.oi.rightJoystick), Robot.oi.getJoystickTwist(Robot.oi.rightJoystick));
	    }
	    @Override
	    protected boolean isFinished() {
	        return false; 
	    }
	    @Override
	    protected void end(){
	    	Robot.BASE.stopMotors();
	    }
	    @Override
	    protected void interrupted(){
	    	end();
	    }

}
