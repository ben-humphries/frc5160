
package org.usfirst.frc.team5160.robot;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team5160.robot.autonomous.BoilerSideAuto;
import org.usfirst.frc.team5160.robot.commands.CMDTeleOpTankDrive;
import org.usfirst.frc.team5160.robot.subsystems.Base;
import org.usfirst.frc.team5160.robot.subsystems.Climber;
import org.usfirst.frc.team5160.robot.subsystems.GearMechanism;
import org.usfirst.frc.team5160.robot.subsystems.IntakeMechanism;
import org.usfirst.frc.team5160.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	
	public static final Base BASE = new Base();
	public static final Climber CLIMBER = new Climber();
	public static final GearMechanism GEAR_MECHANISM = new GearMechanism();
	public static final IntakeMechanism INTAKE_MECHANISM = new IntakeMechanism();
	public static final Shooter SHOOTER = new Shooter();
	
	public static OI oi;
	
	public static boolean currentTeleOpDriveMode = true;
	public static int currentCamera = 0;
	public static boolean switchCamera = false;
	
	UsbCamera camera0;
	UsbCamera camera1;
	UsbCamera camera2;
	
	UsbCamera[] cameras;
	

    Command autonomousCommand;
    SendableChooser chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new BoilerSideAuto());
        chooser.addObject("My Auto", new BoilerSideAuto());
        SmartDashboard.putData("Auto mode", chooser);
        
        SmartDashboard.putData("Enable Tank Drive", new CMDTeleOpTankDrive());
        
        camera0 = new UsbCamera("cam0", 0);
        camera1 = new UsbCamera("cam1", 1);
        camera2 = new UsbCamera("cam2", 2);
        
        cameras = new UsbCamera[]{camera0, camera1, camera2};
       
        //
        //CameraServer.getInstance().startAutomaticCapture(camera0);
        
        
        
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new BoilerSideAuto();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new BoilerSideAuto();
			break;
		}
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        SmartDashboard.putString("Current Drive Mode: ", currentTeleOpDriveMode ? "Mecanum" : "Tank");
        
        
        if(switchCamera){
        	
        	//CameraServer.getInstance().startAutomaticCapture(cameras[currentCamera]);
        	
        	switchCamera = false;
        }

    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
