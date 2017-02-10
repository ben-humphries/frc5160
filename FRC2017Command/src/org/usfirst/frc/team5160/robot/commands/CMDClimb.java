package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Climbs until limit switch is activated or button is released.
 */
public class CMDClimb extends Command {
	
	double speed;

    public CMDClimb(double speed) {
        requires(Robot.CLIMBER);
        
        this.speed = speed;
    }

    protected void execute() {
    	Robot.CLIMBER.climb(speed);
    }

    protected boolean isFinished() {
    	if(Robot.CLIMBER.getSwitch()){
    		return false;
    	}
        return true;
    }

    protected void end() {
    	Robot.CLIMBER.stopMotors();
    }

    protected void interrupted() {
    	end();
    }

	@Override
	protected void initialize() {
		
	}
}
