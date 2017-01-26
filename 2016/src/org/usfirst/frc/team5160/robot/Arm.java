package org.usfirst.frc.team5160.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Arm {
	private Joystick thirdStick;
    private EncoderTalon vertical = new EncoderTalon(6);
    private EncoderTalon rotational = new EncoderTalon(7);
    
    private int softLimit = -2300;
    private int scorePosition = -555;
    private int portcullisPosition = -2200;
    
    public Arm(Joystick thirdStick) {
		this.thirdStick = thirdStick;
		
		PID rotationalPID = new PID("Rotational_PID", 3.97, 20, 10.0, 0);
		rotational.setPID(rotationalPID);
		rotational.configPeakOutputVoltage(1.2, -1.2); //only applies for PID control mode
        rotational.enableBrakeMode(true);
        rotational.init();
    }
    
    public void operatorControlTick() {
    	vertical.setPercent(thirdStick.getY());
    	
    	int pov = thirdStick.getPOV();
    	
    	if (pov == -1) {
    		if (rotational.beenZeroed()) {
//    			else
    				rotational.setPercent(0);
    		} else {
    			rotational.setPercent(0);
    		}
    	} else {
        	int dir = 0;
        	if (pov == 0) dir = 1;
        	else if (pov == 180) dir = -1;
        	rotational.setPercent(dir * Robot.configHash.get("MaxArmRotational"));
    	}
    	
    	if (thirdStick.getRawButton(11)) {
    		calibrate();
    	}
    	
//    	Robot.debugHash.put("ArmRotationalVelocity", rotational.getVelocity());
    	try {
    		Robot.debugHash.put("ArmRotationalPosition", rotational.getPosition());
    	} catch (Exception e) {}
	}
    
    public void calibrate() {
    	vertical.set(0.2);
		rotational.setPercent(Robot.configHash.get("ArmRotationalCalibrateSpeed"));
		if (rotational.isFwdLimitSwitchClosed()) {
			rotational.zero();
			rotational.setReverseLimitPos(softLimit);
		}
    }
}
