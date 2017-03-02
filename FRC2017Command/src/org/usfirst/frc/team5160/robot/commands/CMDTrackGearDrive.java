package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.vision.VisionManager;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This will have the robot always face the gear peg
 */

public class CMDTrackGearDrive extends Command{
	private static final double DEGREE_ERROR = 1; // Acceptable error in degrees +/-
	public CMDTrackGearDrive(){
		requires(Robot.BASE);
	}
	@Override
	protected void initialize(){
		Robot.BASE.setInvertAuto();
	}
	@Override
	protected void execute() {
		double delta = Robot.vision.gearProcessor.getDeltaAngle();
		double dir = RMath.sign(delta);
		Robot.BASE.mecanumDrive(Robot.oi.getJoystickX(), Robot.oi.getJoystickY(), dir*RMath.clamp(0.2, 0.75, 1.0-1.0/Math.abs(0.1*delta) ));
	}
	@Override
	protected boolean isFinished() {
		return finishedRotate();
	}
	private boolean finishedRotate(){
		return Math.abs(Robot.vision.gearProcessor.getDeltaAngle()) <= DEGREE_ERROR;
	}
}
