
package org.usfirst.frc.team5160.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import java.io.IOException;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
	
	
	
    RobotDrive robot;
    Joystick joystick;
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    SendableChooser chooser;
    
    ADXRS450_Gyro gyro;
    
    Talon talon = new Talon(0);
    
    //Record Autonomous variables
    boolean recordAuto = false;

    public Robot() {
    	
        robot = new RobotDrive(1, 3, 2, 4); //four wheel drive, using ports 1,2,
        																  //3,4
        robot.setExpiration(0.1);
        
        //port 0
        joystick = new Joystick(0);
        
        gyro = new ADXRS450_Gyro(/*port*/);
        
    }
    
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto modes", chooser);
    }

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the if-else structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomous() {
    	
    	String autoSelected = (String) chooser.getSelected();
//		String autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    	
    	switch(autoSelected) {
    	case customAuto:
            robot.setSafetyEnabled(false);
            robot.drive(-0.5, 1.0);	// spin at half speed
            Timer.delay(2.0);		//    for 2 seconds
            robot.drive(0.0, 0.0);	// stop robot
            break;
    	case defaultAuto:
    	default:
            robot.setSafetyEnabled(false);
            robot.drive(-0.5, 0.0);	// drive forwards half speed
            Timer.delay(2.0);		//    for 2 seconds
            robot.drive(0.0, 0.0);	// stop robot
            break;
    	}
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
    	
    	try {
			SmartDashboard.putData("Record Autonomous", new RecordAutonomous("test", 30));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	
    	try {
			SmartDashboard.putData("Play Autonomous", new RecordAutonomous("", 30));
		} catch (IOException e) {
			e.printStackTrace();
		}
    
        robot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
            robot.mecanumDrive_Polar(joystick.getMagnitude(), joystick.getDirectionDegrees(), joystick.getTwist()); // mecanum drive
            
            Timer.delay(0.005);		// wait for a motor update time
        }
    }
    public void rotateDegrees(double degrees, double rotationMag){
    	boolean angleReached = false;
    	double currentAngle = 0;
    	
    	while(isEnabled() && !angleReached){
    		
    		double startAngle = gyro.getAngle();
    		
    		robot.mecanumDrive_Polar(0, 0, rotationMag);
    		
    		Timer.delay(0.005);
    		currentAngle += (gyro.getAngle() - startAngle);
    		
    		if(Math.abs(currentAngle) > Math.abs(degrees)){
    			angleReached = true;
    		}
    	}
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}
