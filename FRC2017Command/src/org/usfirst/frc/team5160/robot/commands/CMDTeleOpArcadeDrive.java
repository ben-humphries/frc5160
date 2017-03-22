package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CMDTeleOpArcadeDrive extends Command{
	 
	public CMDTeleOpArcadeDrive() {
	    	requires(Robot.BASE);
	    }
	    @Override
	    protected void execute() {
	    	if(Robot.oi.isSlowed()){
	    		Robot.BASE.cheesyDrive(Robot.oi.getRightJoystickY(), Robot.oi.getJoystickRotation(), true);
	    	}
	    	else{
	    		Robot.BASE.cheesyDrive(Robot.oi.getRightJoystickY(), Robot.oi.getJoystickRotation(), false);
	    	}
	    	
	    		
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
