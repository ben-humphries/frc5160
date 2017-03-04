package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Shoot while button is held.
 */
public class CMDShoot extends Command {
	
	double targetSpeed;
	

    public CMDShoot(double speed) {
        requires(Robot.SHOOTER);
        this.targetSpeed = speed;
    }
    @Override
    protected void execute() {
    	Robot.debugShooterVelocity = Robot.SHOOTER.getSpeed();
    	Robot.SHOOTER.shoot(targetSpeed+0.5*Robot.oi.getOperatorJoystickY());
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
