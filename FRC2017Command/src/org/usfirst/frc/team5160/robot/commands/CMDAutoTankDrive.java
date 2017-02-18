package org.usfirst.frc.team5160.robot.commands;

import javax.swing.text.Position;

import org.usfirst.frc.team5160.robot.Robot;
import org.usfirst.frc.team5160.robot.subsystems.Base;

import edu.wpi.first.wpilibj.command.Command;

public class CMDAutoTankDrive extends Command{
	public CMDAutoTankDrive(double dLeft,double dRight){
		requires(Robot.BASE);
		Robot.BASE.positionTankDriveSet(Base.inchToEncoderTick(dLeft), Base.inchToEncoderTick(dRight));
	}
	@Override 
	protected void initialize(){
		Robot.BASE.positionTankDriveExecute();
	}
	@Override 
	protected void execute(){
		Robot.BASE.positionTankDriveExecute();
	}
	@Override
	protected boolean isFinished() {
		return Robot.BASE.positionTankDriveReached();
	}

}
