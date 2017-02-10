package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Shoot while button is held.
 */
public class CMDShoot extends Command {
	
	double speed;

    public CMDShoot(double speed) {
        requires(Robot.SHOOTER);
        
        this.speed = speed;
    }

    protected void execute() {
    	Robot.SHOOTER.shoot(speed);
    }

    protected boolean isFinished() {
        return false;
    }
    protected void end(){
    	Robot.SHOOTER.stopMotors();
    }
    protected void interrupted(){
    	end();
    }

}
