package org.usfirst.frc.team5160.robot.commands;


import org.usfirst.frc.team5160.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleOpMecanumDrive extends Command {

    public TeleOpMecanumDrive() {
    	requires(Robot.BASE);
    }
    protected void initialize() {
    }

    protected void execute() {
    	Robot.BASE.mecanumDrive(Robot.oi.getJoystickX(), Robot.oi.getJoystickY(), Robot.oi.getJoystickRotation());
    }

    protected boolean isFinished() {
        return false; //never finishes
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
