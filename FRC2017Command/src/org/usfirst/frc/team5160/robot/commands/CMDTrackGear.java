package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.vision.VisionManager;
import org.usfirst.frc.team5160.utils.BasicPID;

import edu.wpi.first.wpilibj.command.Command;
/**
 * This will rotate the robot to aline to the gear peg. 
 */

public class CMDTrackGear extends Command{
	private double target = 0;
	private BasicPID drivePid; 
	private BasicPID turnPid;
	private static final double error = 1;
	public CMDTrackGear(double target){
		requires(Robot.BASE);
		this.target = target;
		this.drivePid = new BasicPID(0.075, 0.0, 0.015);
		this.turnPid = new BasicPID(0.15, 0.0, 0.05);
	}
	@Override
	protected void initialize(){
		super.initialize();
		Robot.BASE.resetEncoders();
	}
	@Override
	protected void execute() {
		double delta = Robot.vision.gearProcessor.getDeltaAngle();
		super.execute();
		double value = drivePid.runPID(Robot.BASE.getAverageEncoderDistance(), target);
		value = RMath.clamp(-0.8, 0.8, value);
		double turnValue = turnPid.runPID(delta, 0);
		turnValue = -RMath.clamp(-0.5, 0.5, turnValue);
		System.out.println(value +" , " + turnValue);
		Robot.BASE.arcadeDrive(value, turnValue);
	}
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.BASE.getAverageEncoderDistance() - target) < error;
	}
}