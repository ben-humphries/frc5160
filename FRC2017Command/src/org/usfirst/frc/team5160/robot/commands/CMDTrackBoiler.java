package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.vision.VisionManager;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class CMDTrackBoiler extends Command{
	private static final double DEGREE_ERROR = 3; // Acceptable error in degrees +/-
	public CMDTrackBoiler(){
		requires(Robot.BASE);
	}
	@Override
	protected void execute() {
		double delta =Robot.vision.boilerProcessor.getDeltaAngle();
		double dir = RMath.sign(delta);
		Robot.BASE.mecanumDrive(0, 0, dir*RMath.clamp(0.2, 0.75, 1.0-1.0/Math.abs(0.1*delta) ));
	}
	@Override
	protected boolean isFinished() {
		return finishedRotate();
	}
	private boolean finishedRotate(){
		return Math.abs(Robot.vision.boilerProcessor.getDeltaAngle()) <= DEGREE_ERROR;
	}
}
