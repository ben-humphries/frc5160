package org.usfirst.frc.team5160.robot.commands;


import org.usfirst.frc.team5160.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

/**
 *Default Base command, moves robot during Teleop based on joystick input.
 */
public class TeleOpMecanumDrive extends Command {

    public TeleOpMecanumDrive() {
    	requires(Robot.BASE);
    }
    
    protected void execute() {
    	Robot.BASE.mecanumDrive(Robot.oi.getJoystickX(), Robot.oi.getJoystickY(), Robot.oi.getJoystickRotation());
    }

    protected boolean isFinished() {
        return false; //never finishes
    }

}
