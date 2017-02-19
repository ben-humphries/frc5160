package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.vision.VisionManager;

import edu.wpi.first.wpilibj.command.Command;

public class CMDTrackGear extends Command{
	private static final double DEGREE_ERROR = 3; // Acceptable error in degrees +/-
	public CMDTrackGear(){
		requires(Robot.BASE);
	}
	@Override
	protected void execute() {
		double delta = VisionManager.GetInstance().gearProcessor.getDeltaAngle();
		double dir = RMath.sign(delta);
		Robot.BASE.mecanumDrive(0, 0, dir*RMath.clamp(0.2, 0.75, 1.0-1.0/Math.abs(0.1*delta) ));
	}
	@Override
	protected boolean isFinished() {
		return finishedRotate();
	}
	private boolean finishedRotate(){
		return Math.abs(VisionManager.GetInstance().gearProcessor.getDeltaAngle()) <= DEGREE_ERROR;
	}
}
