package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.vision.VisionManager;

import edu.wpi.first.wpilibj.command.Command;
/**
 * This will rotate the robot to aline to the gear peg. 
 */

public class CMDTrackGear extends Command{
	private static final double DEGREE_ERROR = 3; // Acceptable error in degrees +/-
	public CMDTrackGear(){
		requires(Robot.BASE);
	}
	@Override
	protected void initialize(){

	}
	@Override
	protected void execute() {
		double delta = Robot.vision.gearProcessor.getDeltaAngle();
		double dir = RMath.sign(delta);
		Robot.BASE.arcadeDrive(0, dir*0.2);
	}
	@Override
	protected boolean isFinished() {
		return finishedRotate();
	}
	private boolean finishedRotate(){
		return Math.abs(Robot.vision.gearProcessor.getDeltaAngle()) <= DEGREE_ERROR;
	}
}
