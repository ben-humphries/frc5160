package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Tele Op Tank Drive
 */
public class CMDTeleOpTankDrive extends Command {

    public CMDTeleOpTankDrive() {
        requires(Robot.BASE);
    }
    @Override
    protected void execute() {
    	Robot.BASE.tankDrive(Robot.oi.getLeftJoystickY(), Robot.oi.getRightJoystickY());
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
    @Override
    protected void end() {
    	Robot.BASE.stopMotors();
    }
    @Override
    protected void interrupted() {
    	end();
    }
}