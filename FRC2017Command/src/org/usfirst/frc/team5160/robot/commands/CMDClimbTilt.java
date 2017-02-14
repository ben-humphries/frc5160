package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CMDClimbTilt extends Command {
	
	double speed;

    public CMDClimbTilt(double speed) {
        requires(Robot.CLIMBER);
        
        this.speed = speed;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.CLIMBER.tilt(speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.CLIMBER.stopMotors();
    }
    
    protected void interrupted() {
    	end();
    }
}
