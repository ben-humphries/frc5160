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
    @Override
    protected void initialize() {
    }
    @Override
    protected void execute() {
    	Robot.CLIMBER.tilt(speed);
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
    @Override
    protected void end() {
    	Robot.CLIMBER.stopMotors();
    }
    @Override
    protected void interrupted() {
    	end();
    }
}
