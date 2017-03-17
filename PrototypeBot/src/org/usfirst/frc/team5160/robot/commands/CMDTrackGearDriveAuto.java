package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.vision.VisionManager;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This drives the robot in a straight line, but strafing to aline to the gear. 
 */
/*
public class CMDTrackGearDriveAuto extends Command{
	private static final double DEGREE_ERROR = 1; // Acceptable error in degrees +/-
	private double distance, turnMagnitude;
	public CMDTrackGearDriveAuto(double distance, double turnMagnitude){
		requires(Robot.BASE);
		this.distance = distance;
		this.turnMagnitude = turnMagnitude;
	}
	@Override
	protected void initialize(){
		Robot.BASE.setInvertAuto();
		Robot.BASE.resetEncoders();
	}
	@Override
	protected void execute() {
		double delta = Robot.vision.gearProcessor.getDeltaAngle();
		double dir = RMath.sign(delta);
		Robot.BASE.mecanumDriveField(0.5,  dir*turnMagnitude,0);
	}
	@Override
	protected boolean isFinished() {
		return Robot.BASE.getAverageEncoder() >= distance;
	}
}
*/