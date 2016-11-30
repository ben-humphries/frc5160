
package org.usfirst.frc.team5160.robot;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {
    final String nothing = "Nothing";
    final String ramparts = "Ramparts";
    SendableChooser chooser;

    RobotDrive robotDrive;
    Intake intake;
    Arm arm;
    Camera camera;
    Joystick leftStick, rightStick, thirdStick;
    
    //ben was definitely here what are you talking about?

	//values are read from the SmartDashboard at regular intervals It's static for convenience.
    public static HashMap<String, Double> configHash = new HashMap<String, Double>(){private static final long serialVersionUID = -694703851331954033L;{ //random unique identifier
    	put("MaxVel", 6.403);
    	put("MaxShooterVelocity", 6000.0);
    	put("MaxArmRotational", 0.35);
    	put("ArmRotationalCalibrateSpeed", 0.25);
    	put("SuckInShooterPercent", -0.6);
    	put("PushUpShooterPercent", 0.5);
    	put("P1_x", 300.0);
    	put("P1_y", 100.0);
    	put("P2_x", 280.0);
    	put("P2_y", 255.0);
    	put("P3_x", 438.0);
    	put("P3_y", 257.0);
    	put("P4_x", 420.0);
    	put("P4_y", 100.0);
    	put("lineColor", 499.0);
    }};
    
    //values are sent to the SmartDashboard at regular intervals. It's static for convenience.
    public static HashMap<String, Double> debugHash = new HashMap<String, Double>();
    
    public static List<PID> registeredPIDs = new ArrayList<PID>();
    
    long lastUpdate = 0;
    int updateDelay = 100; //milliseconds

    public Robot() {
    	leftStick = new Joystick(0);
    	rightStick = new Joystick(1);
    	thirdStick = new Joystick(2);
    	robotDrive = new RobotDrive(leftStick, rightStick, thirdStick);
    	intake = new Intake(thirdStick, rightStick);
    	arm = new Arm(thirdStick);
    	camera = new Camera();
    }
    
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault(nothing, nothing);
        chooser.addObject(ramparts, ramparts);
        SmartDashboard.putData("Auto modes", chooser);
        
        robotDrive.init();
        intake.init();
        uploadValues(configHash); //upload default values. This should come last, after the other things init and potentially insert default values.

        camera.init(thirdStick);
    }

    public void autonomous() {
    	
    	String autoSelected = (String) chooser.getSelected();
    	
    	switch(autoSelected) {
    	case nothing:
            while (isEnabled()) { calibrate(); Timer.delay(0.005); }
    	case ramparts:
    		robotDrive.rampartsInit();
    		while (isEnabled()) {
    			robotDrive.rampartsUpdate();
    			calibrate();
    			Timer.delay(0.005);
    		}
    	default:
            break;
    	}
    	
    	robotDrive.stop();
    }
    
    public void calibrate() {
    	intake.calibrate();
    	arm.calibrate();
    }

    public void operatorControl() {
    	camera.start();
    	
        while (isOperatorControl() && isEnabled()) {
        	updateSmartDashboard();
            robotDrive.operatorControlTick();
            intake.operatorControlTick();
            arm.operatorControlTick();
            
            camera.operatorControlTick();
        	
            Timer.delay(0.005);		// wait for a motor update time
        }
        
        camera.stop();
    }

    public void test() {
    	while(isEnabled()) {
    		updateSmartDashboard();
    		Timer.delay(0.005);
    	}
    }
    
    public void updateSmartDashboard() {
    	long time = System.currentTimeMillis();
    	if (time - lastUpdate > updateDelay) {
    		lastUpdate = time;
    		uploadValues(debugHash);
    		for (String key : configHash.keySet()) { //download new values
    			configHash.put(key, SmartDashboard.getNumber(key));
    		}
    		for (PID pid : registeredPIDs) {
    			pid.fetchFromConfig();
    		}
    	}
    }
    
    public void uploadValues(HashMap<String, Double> hash) {
        for (Map.Entry<String, Double> entry : hash.entrySet()) {
        	SmartDashboard.putNumber(entry.getKey(), entry.getValue());
        }
    }
}
