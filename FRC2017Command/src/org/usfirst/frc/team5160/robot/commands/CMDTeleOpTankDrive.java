package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Tele Op Tank Drive
 */
public class CMDTeleOpTankDrive extends Command {

    public CMDTeleOpTankDrive() {
        requires(Robot.BASE);
        Robot.currentTeleOpDriveMode = false;
    }

    protected void execute() {
    	Robot.BASE.tankDrive(Robot.oi.getTankJoystickY(), Robot.oi.getJoystickY());
    }
    protected boolean isFinished() {
        return false;
    }
    protected void end() {
    	Robot.BASE.stopMotors();
    }
    protected void interrupted() {
    	end();
    }
}
