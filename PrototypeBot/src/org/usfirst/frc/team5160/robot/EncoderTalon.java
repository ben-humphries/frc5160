package org.usfirst.frc.team5160.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class EncoderTalon extends CANTalon {
	
	private TalonControlMode mode = null;
	private double clicksPerUnit = 1.0;
	private double zeroPoint = 0.0;
	private boolean beenZeroed = false;
	
	private int dir = 0;
	private double destPos = 0;

	public EncoderTalon(int deviceNumber) {
		super(deviceNumber);
	}

	public EncoderTalon(int deviceNumber, double clicksPerUnit) {
		super(deviceNumber);
		setConversion(clicksPerUnit);
	}
	
	public void setConversion(double clicksPerUnit) {
		this.clicksPerUnit = clicksPerUnit;
	}
	
	public void init() {
		configNominalOutputVoltage(+0.0f, -0.0f);
		configPeakOutputVoltage(+12.0f, -12.0f);
    	setFeedbackDevice(FeedbackDevice.QuadEncoder);
	}
	
	public void setSlave(CANTalon slave) {
    	slave.changeControlMode(TalonControlMode.Follower);
    	slave.set(getDeviceID());
	}
	
	public void ensureMode(TalonControlMode neededMode) {
		if (mode != neededMode) {
			mode = neededMode;
			changeControlMode(mode);
		}
	}
	
	public double getPosition() {
		return (getEncPosition() - zeroPoint) / clicksPerUnit;
	}
	
	public double getVelocity() {
		try {
			return getEncVelocity() / clicksPerUnit;
		} catch(Exception e) {
			return 0.0;
		}
	}
	
	//dist cannot be zero
	public void move(double dist, double perc) {
		dir = (int) (dist / Math.abs(dist));
		destPos = getPosition() + dist;
		setPercent(perc*dir);
	}
	
	//Call this repeatedly after calling "move" to update.
	public boolean thereYet() {
		if (dir * (getPosition() - destPos) >= 0) {
			setPercent(0.0);
			return true;
		}
		return false;
	}
	
	public void setPosition(double position) {
		ensureMode(TalonControlMode.Position);
		set((position * clicksPerUnit) + zeroPoint);
	}
	
	public void setVelocity(double velocity) {
		ensureMode(TalonControlMode.Speed);
		set(velocity * clicksPerUnit);
	}
	
	public void setPercent(double percent) {
		ensureMode(TalonControlMode.PercentVbus);
		set(percent);
	}
	

	
	public void zero() {
		zeroPoint = getEncPosition();
		beenZeroed = true;
	}
	
	public boolean beenZeroed() { return beenZeroed; }
	
	public void setReverseLimitPos(double position) {
		setReverseSoftLimit((position * clicksPerUnit) + zeroPoint);
	 	enableReverseSoftLimit(true);
	}
}
