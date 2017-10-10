package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.subsystems.GearMechanism;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CMDGearMech extends Command {
	

    public CMDGearMech() {
        requires(Robot.GEAR_MECHANISM);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.GEAR_MECHANISM.rotate(-Robot.oi.getJoystickY(Robot.oi.operatorJoystick));
    	Robot.GEAR_MECHANISM.intake(Robot.oi.gearIntakePower());
    	/*if(Robot.GEAR_MECHANISM.isTooFarDown()){
    		Robot.GEAR_MECHANISM.hold(Robot.Gear_Down_Value);
    	}*/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.GEAR_MECHANISM.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
