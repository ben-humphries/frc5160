package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.vision.VisionManager;

import edu.wpi.first.wpilibj.command.Command;
/**
 * This will rotate the robot to aline to the gear peg. 
 */

public class CMDTrackGearGround extends Command{
	private static final double DEGREE_ERROR = 3; // Acceptable error in degrees +/-
	public CMDTrackGearGround(){
		requires(Robot.BASE);
	}
	@Override
	protected void initialize(){
		Robot.vision.setNormal();
	}
	@Override
	protected void execute() {
		double delta = Robot.vision.deltaAngle;
		double dir = RMath.sign(delta);
		Robot.BASE.mecanumDrive(0, 0, dir*0.2);
	}
	@Override
	protected boolean isFinished() {
		return finishedRotate();
	}
	private boolean finishedRotate(){
		return Math.abs(Robot.vision.gearProcessor.getDeltaAngle()) <= DEGREE_ERROR;
	}
}
