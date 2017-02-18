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
    @Override
    protected void execute() {
    	Robot.SHOOTER.shoot(speed);
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
    @Override
    protected void end(){
    	Robot.SHOOTER.stopMotors();
    }
    @Override
    protected void interrupted(){
    	end();
    }

}
