package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Intakes balls while button is held.
 */
public class CMDIntakeIn extends Command {
	
	double speed;

    public CMDIntakeIn(double speed) {
        requires(Robot.INTAKE_MECHANISM);
        
        this.speed = speed;
    }
    @Override
    protected void execute() {
    	Robot.INTAKE_MECHANISM.intake(speed, speed);
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
    @Override
    protected void end() {
    	Robot.INTAKE_MECHANISM.stopMotors();
    }

    protected void interrupted() {
    	end();
    }
}
