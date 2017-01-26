package org.usfirst.frc.team5160.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class Intake {
	private Joystick thirdStick;
	private Joystick driverStick;
    public EncoderTalon intakeRot = new EncoderTalon(5, 42.84);
    private Talon intakeSpin = new Talon(1);
    private EncoderTalon shooter = new EncoderTalon(8, -1);
    
    private long timeArrived = 0;
    private boolean needToPullIn = false;
    private long waitTime = 750;
    private boolean waiting = false;
    
    public Intake(Joystick thirdStick, Joystick driverStick) {
		this.thirdStick = thirdStick;
		this.driverStick = driverStick;
    }

    public void init() {
		PID intakePID = new PID("Intake_PID", 3.468, 20, 10.0, 0);
		intakeRot.setPID(intakePID);
        intakeRot.enableBrakeMode(true);
        intakeRot.init();
        
		PID shooterPID = new PID("Shooter_PID", 0.1207, 0.1, 2.0, 0);
		shooter.setPID(shooterPID);
		shooter.reverseOutput(true);
        shooter.enableBrakeMode(true);
        shooter.configNominalOutputVoltage(0.0, 0.0);
        shooter.configPeakOutputVoltage(12.0, -2);
		shooter.init();
    }
    
    public void operatorControlTick() {
    	//TODO: Change to use the third joystick.
    	if (thirdStick.getRawButton(9)) intakeSpin.set(-1.0);
    	else if (thirdStick.getRawButton(7)) intakeSpin.set(1.0);
    	else intakeSpin.set(0.0);
    	
    	if (thirdStick.getRawButton(1) && thirdStick.getRawButton(12)) {
    		shooter.setPercent(1.0);
    	} else if (thirdStick.getRawButton(1)) {
    		shooter.setVelocity(Robot.configHash.get("MaxShooterVelocity"));
    	} else if (thirdStick.getRawButton(4)) {
    		if (thirdStick.getRawButton(12))
    			shooter.setPercent(Robot.configHash.get("SuckInShooterPercent"));
    		else
    			shooter.setPercent(-1.0);
    		needToPullIn = true;
    	} else if (thirdStick.getRawButton(6)) {
    		shooter.setPercent(Robot.configHash.get("PushUpShooterPercent"));
    		intakeSpin.set(-1.0);
    		intakeRot.setPercent(1.0);
    	} else shooter.set(0.0);

    	int pov = -1;//driverStick.getPOV();
    	
    	boolean firing = thirdStick.getRawButton(1) && thirdStick.getRawButton(2);
    	
    	if (driverStick.getRawButton(11)) {
    		calibrate();
    	} else if (pov != -1 || firing) {
        	int dir = 0;
        	if (pov == 0 || firing) { dir = 1; intakeSpin.set(-1.0); }
        	else if (pov == 180) dir = -1;
        	intakeRot.setPercent(dir);
        	needToPullIn = true;
    	} else if (intakeRot.beenZeroed()) {
    		if (driverStick.getRawButton(3)) {
    			extend();
	    	} else if (driverStick.getRawButton(4)) {
	    		waiting = false;
	    		needToPullIn = false;
	    		retract();
	    	} else if (driverStick.getRawButton(2)) {
	    		eject();
	    	} else if (driverStick.getRawButton(1)) {
				needToPullIn = true;
				extend();
			} else if (needToPullIn) {
				long now = System.currentTimeMillis();
				if (retract()) {
					if (!waiting) {
						timeArrived = now;
						waiting = true;
					} else if (now - timeArrived > waitTime) {
						waiting = false;
						needToPullIn = false;
					}
				}
			} else if (!thirdStick.getRawButton(6)) {
				intakeRot.setPercent(0.0);
			}
    	} else if (!thirdStick.getRawButton(6)){
    		intakeRot.setPercent(0.0);
    	}

//    	Robot.debugHash.put("pov", (double)pov);
//    	Robot.debugHash.put("IntakePos", (double)intakeRot.getPosition());
    	//Robot.debugHash.put("ShooterVel", (double)shooter.getVelocity());
    	try {
    		Robot.debugHash.put("ShooterPerc", (double)shooter.getVelocity() / Robot.configHash.get("MaxShooterVelocity"));
    	} catch (Exception e) {}
    }
    
    public void calibrate() {
		intakeRot.setPercent(1.0);
		if (intakeRot.isFwdLimitSwitchClosed()) {
			intakeRot.zero();
			intakeRot.setReverseLimitPos(-175);
		}
    }
    
    private void extend() {
		intakeRot.setPosition(-117);
		intakeSpin.set(-1.0);
    }
    
    //returns true if retract has reached its goal position
    private boolean retract() {
    	int inPos = -48;
		intakeRot.setPosition(inPos);
		intakeSpin.set(-1.0);
		return Math.abs(intakeRot.getPosition() - inPos) < 5;
    }
    
    private void eject() {
		int outPos = -68;
		intakeRot.setPosition(outPos);
		if (Math.abs(intakeRot.getPosition() - outPos) < 5)
			intakeSpin.set(1.0);
    }
}
