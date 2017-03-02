package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Shoot while button is held.
 */
public class CMDShoot extends Command {
	
	double targetSpeed;
	//double currentSpeed;
	
	// double SPEEDGAIN = (1.0 / 50.0) / 0.2;

    public CMDShoot(double speed) {
        requires(Robot.SHOOTER);
    //    currentSpeed = 0;
        this.targetSpeed = speed;
    }
    @Override
    protected void execute() {
    //	if (currentSpeed < targetSpeed)
    //	{ currentSpeed += SPEEDGAIN; }
    //	else { currentSpeed = targetSpeed; }
    	Robot.SHOOTER.shoot(targetSpeed);
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
