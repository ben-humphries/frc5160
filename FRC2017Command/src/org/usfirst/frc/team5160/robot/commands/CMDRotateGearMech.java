package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.subsystems.GearMechanism;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CMDRotateGearMech extends Command {
	
	private double speed;

    public CMDRotateGearMech() {
        requires(Robot.GEAR_MECHANISM);
        
        this.speed = Robot.oi.getOperatorJoystickY();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.GEAR_MECHANISM.rotate(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
