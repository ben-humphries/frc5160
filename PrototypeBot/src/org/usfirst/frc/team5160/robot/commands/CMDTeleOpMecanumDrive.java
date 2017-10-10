package org.usfirst.frc.team5160.robot.commands;


import org.usfirst.frc.team5160.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

/**
 *Default Base command, moves robot during Teleop based on joystick input.
 */
public class CMDTeleOpMecanumDrive extends Command {

    public CMDTeleOpMecanumDrive() {
    	requires(Robot.BASE);
    	Robot.currentTeleOpDriveMode = true;
    }
    @Override
    protected void execute() {
    	Robot.BASE.arcadeDrive(Robot.oi.getJoystickY(), Robot.oi.getJoystickSecondX());
    	//Robot.BASE.cheesyDrive(Robot.oi.getJoystickY(), Robot.oi.getJoystickRotation(), Robot.oi.slowDown());
    }
    @Override
    protected boolean isFinished() {
        return false; //never finishes
    }
    @Override
    protected void end(){
    	Robot.BASE.stopMotors();
    }
    @Override
    protected void interrupted(){
    	end();
    }

}
