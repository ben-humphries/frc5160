package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Default command for the climber, stops motors.
 */
public class CMDClimbStop extends Command {

    public CMDClimbStop() {
        requires(Robot.CLIMBER);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	Robot.CLIMBER.stopMotors();
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
    @Override
    protected void end() {
    }
    @Override
    protected void interrupted() {
    	end();
    }
}
