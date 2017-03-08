package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Intakes balls while button is held.
 */
public class CMDIntakeIn extends Command {
	
	double speed;
	boolean shoot;
	public CMDIntakeIn(double speed){
		this.speed = speed;
		
	}
    public CMDIntakeIn(double speed, boolean shoot) {
        requires(Robot.INTAKE_MECHANISM);
        this.shoot = shoot;
        this.speed = speed;
    }
  
    @Override
    protected void execute() {
    	if((!shoot)||Robot.oi.isShooting()){
    		Robot.INTAKE_MECHANISM.uptake(speed);
    	}
    	else{
    		Robot.INTAKE_MECHANISM.intake(speed);
    	}
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