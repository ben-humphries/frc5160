package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Pushes gear forward until button is released
 */
public class PushGear extends Command {
	
	double speed;

    public PushGear(double speed) {
        requires(Robot.GEAR_MECHANISM);
        
        this.speed = speed;
    }

    protected void execute() {
    	Robot.GEAR_MECHANISM.pushGear(speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.GEAR_MECHANISM.stopMotor();
    }

    protected void interrupted() {
    	end();
    }
}
