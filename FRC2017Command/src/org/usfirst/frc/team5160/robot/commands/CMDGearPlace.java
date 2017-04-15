package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.subsystems.GearMechanism;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CMDGearPlace extends Command {
	

    public CMDGearPlace() {
        requires(Robot.GEAR_MECHANISM);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.GEAR_MECHANISM.moveDown();
    	Robot.GEAR_MECHANISM.intake(-0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.GEAR_MECHANISM.moveDown();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.GEAR_MECHANISM.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
