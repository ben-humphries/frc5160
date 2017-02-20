package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Shoot while button is held.
 */
public class CMDShoot extends Command {
	
	double targetSpeed;
	double currentSpeed;
	
	const double SPEEDGAIN = 0.2 / 50.0;

    public CMDShoot(double speed) {
        requires(Robot.SHOOTER);
        requires(Robot.INTAKE_MECHANISM);
        currentSpeed = 0;
        this.targetSpeed = speed;
    }
    @Override
    protected void execute() {
    	if (currentSpeed < targetSpeed)
    	{ currentSpeed += SPEEDGAIN; }
    	else { currentSpeed = targetSpeed; }
    	Robot.SHOOTER.shoot(currentSpeed);
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
