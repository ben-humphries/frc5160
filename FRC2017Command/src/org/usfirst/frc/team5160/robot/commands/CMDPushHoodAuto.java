package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Pushes gear forward until button is released
 */
public class CMDPushHoodAuto extends Command {

	double speed;
    public CMDPushHoodAuto(double speed) {
    	this.speed = speed;
        requires(Robot.GEAR_MECHANISM);
            }
    @Override
    protected void execute() {
    	Robot.GEAR_MECHANISM.pushHood(speed);
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
    @Override
    protected void end() {
    	Robot.GEAR_MECHANISM.stopMotor();
    }

    protected void interrupted() {
    	end();
    }

	@Override
	protected void initialize() {
		
	}
}