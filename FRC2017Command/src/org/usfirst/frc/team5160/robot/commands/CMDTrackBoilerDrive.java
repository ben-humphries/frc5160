package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.vision.VisionManager;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
/**
 * This will have the robot always face the boiler.
 */
public class CMDTrackBoilerDrive extends Command{
	private static final double DEGREE_ERROR = 1; // Acceptable error in degrees +/-
	public CMDTrackBoilerDrive(){
		requires(Robot.BASE);
	}
	@Override
	protected void initialize(){
		Robot.BASE.setInvertAuto();
	}
	@Override
	protected void execute() {
		double delta =Robot.vision.boilerProcessor.getDeltaAngle();
		double dir = RMath.sign(delta);
		Robot.BASE.mecanumDrive(Robot.oi.getJoystickX(), Robot.oi.getJoystickY(), dir*0.2);
	}
	@Override
	protected boolean isFinished() {
		return finishedRotate();
	}
	private boolean finishedRotate(){
		return Math.abs(Robot.vision.boilerProcessor.getDeltaAngle()) <= DEGREE_ERROR;
	}
}
