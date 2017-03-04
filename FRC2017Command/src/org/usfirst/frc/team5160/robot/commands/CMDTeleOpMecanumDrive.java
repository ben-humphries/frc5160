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
    	double mul = 1;
    	if(Robot.oi.slowDown()){
    		mul = 0.5;
    	}
    	if(Robot.oi.fieldControl()){
    		Robot.BASE.mecanumDriveField(Robot.oi.getJoystickX()*mul, Robot.oi.getJoystickY()*mul, Robot.oi.getJoystickRotation()*mul);
    	}
    	else{
    		Robot.BASE.mecanumDrive(Robot.oi.getJoystickX()*mul, Robot.oi.getJoystickY()*mul, Robot.oi.getJoystickRotation()*mul);
    	}
    		
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
