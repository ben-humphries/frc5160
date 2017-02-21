package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Shoot while button is held.
 */
public class CMDShoot extends Command {
	
	double targetSpeed;
	double currentSpeed;
	double intakeSpeed;
	
	const double SPEEDGAIN = (1.0 / 50.0) / 0.2;

    public CMDShoot(double speed, double intakeSpeed) {
        requires(Robot.SHOOTER);
        requires(Robot.INTAKE_MECHANISM);
        currentSpeed = 0;
        this.targetSpeed = speed;
        this.intakeSpeed = intakeSpeed;
    }
    @Override
    protected void execute() {
    	if (currentSpeed < targetSpeed)
    	{ currentSpeed += SPEEDGAIN; }
    	else { currentSpeed = targetSpeed; }
    	Robot.SHOOTER.shoot(currentSpeed);
    	Robot.INTAKE_MECHANISM.intake(intakeSpeed, -1 * intakeSpeed);
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
    @Override
    protected void end(){
    	Robot.SHOOTER.stopMotors();
    	Robot.INTAKE_MECHANISM.stopMotors();
    }
    @Override
    protected void interrupted(){
    	end();
    }
}