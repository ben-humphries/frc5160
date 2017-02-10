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
    protected void execute() {
    	Robot.CLIMBER.stopMotors();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    	end();
    }
}
