
package org.usfirst.frc.team5160.robot;

import java.util.ArrayList;

import org.usfirst.frc.team5160.robot.activities.Activity;
import org.usfirst.frc.team5160.robot.activities.Autonomous;
import org.usfirst.frc.team5160.robot.activities.Camera;
import org.usfirst.frc.team5160.robot.activities.DriveForwardAuto;
import org.usfirst.frc.team5160.robot.activities.TrackTargetAuto;
import org.usfirst.frc.team5160.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
	//Automode variables
	final String driveForward = "Drive Forward";
    final String trackTarget = "Track Target";
    SendableChooser chooser;
    Autonomous chosenAuto; 
    
    //Timer 
    Timer timer;
    //Robot subsystems
    //Drive drive;
    //Activities
    Camera camera; 
    
    //Input variables
    Joystick driveStick;
    
    RobotDrive drive;
    
    public static TalonSRX frontLeft;
	public static TalonSRX frontRight;
	public static TalonSRX backLeft;
	public static TalonSRX backRight;
    
    public Robot(){
    	//Good idea to call super()
    	super();
    	
    	//Initialize input
    	driveStick = new Joystick(0);
    	
    	//Initialize subsystems
    	//drive = new Drive(leftStick, rightStick);
    	
    	//Initialize activities
    	camera = new Camera();
    	
    }
    @Override
    public void robotInit() {
    	// Configure autonomous modes
        chooser = new SendableChooser();
        chooser.addDefault(driveForward, driveForward);
        chooser.addObject(trackTarget, trackTarget);
        SmartDashboard.putData("Auto choices", chooser);
        
        //Init timer
        timer = new Timer(); 
        
        //Init subsystems
        //drive.init();
        //Init activities
        camera.init();
        
        frontLeft = new TalonSRX(1);
        backLeft = new TalonSRX(2);
        frontRight = new TalonSRX(3);
        backRight = new TalonSRX(4);
        
        drive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
        
    }
    @Override 
    public void autonomousInit() {
    switch((String) chooser.getSelected()) {
    	case driveForward:
    		chosenAuto = new DriveForwardAuto();
    		break;
    	case trackTarget:
    		chosenAuto = new TrackTargetAuto();
    		break;
    	default:
    		chosenAuto = new DriveForwardAuto();
            break;
    	}
    chosenAuto.init();
    	
    }
    @Override
    public void autonomousPeriodic() {
    	chosenAuto.loopAutonomous(timer.get());
    	timer.reset();
    }
    @Override
    public void teleopPeriodic() {
        drive.mecanumDrive_Polar(driveStick.getMagnitude(), driveStick.getDirectionDegrees(), driveStick.getTwist());
    }
    @Override
    public void testPeriodic() {
    
    }
    
}
