package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Pushes gear forward until button is released
 */
public class CMDPushHood extends Command {
	
    public CMDPushHood() {
        requires(Robot.GEAR_MECHANISM);
            }
    @Override
    protected void execute() {
    	if(Math.abs(Robot.oi.getOperatorJoystickX())>Math.abs(Robot.oi.getOperatorJoystickY()) &&  Math.abs(Robot.oi.getOperatorJoystickX())>0.25);
    	Robot.GEAR_MECHANISM.pushHood(Robot.oi.getOperatorJoystickX());
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
