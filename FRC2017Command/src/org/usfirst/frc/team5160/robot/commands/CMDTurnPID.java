package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.RMath;
import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.utils.BasicPID;

import edu.wpi.first.wpilibj.command.Command;

public class CMDTurnPID extends Command {
	
	private double target; 
	private BasicPID pid; 
	private double error = 1;
	public CMDTurnPID(double target){
		requires(Robot.BASE);
		this.target = target;
		this.pid = new BasicPID(0.06, 0.0005, 0.013);
	}
	@Override
	protected void initialize() {
		super.initialize();
		Robot.BASE.resetGyro();
		Robot.BASE.resetEncoders();
	}
	@Override 
	protected void execute() {
		super.execute();
		double turnValue = pid.runPID(-Robot.BASE.getGyroAngle(), target);
		turnValue = RMath.clamp(-0.7, 0.7, turnValue);
		System.out.println(turnValue);
		Robot.BASE.arcadeDrive(0, turnValue);
	}
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.BASE.getGyroAngle() - target) < error;
	}

}
