
package org.usfirst.frc.team5160.robot;

import org.usfirst.frc.team5160.robot.autonomous.ShooterTest;
import org.usfirst.frc.team5160.robot.autonomous.TestAutoVision;
import org.usfirst.frc.team5160.robot.commands.CMDTeleOpTankDrive;
import org.usfirst.frc.team5160.robot.subsystems.Base;
import org.usfirst.frc.team5160.robot.subsystems.Climber;
import org.usfirst.frc.team5160.robot.subsystems.GearMechanism;
import org.usfirst.frc.team5160.robot.subsystems.IntakeMechanism;
import org.usfirst.frc.team5160.robot.subsystems.Shooter;
import org.usfirst.frc.team5160.robot.vision.VisionManager;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
enum AllianceColor{RED,BLUE};

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
	public static VisionManager vision;
	
    private Command autonomousCommand;
    private SendableChooser autoModeChooser;
    private SendableChooser autoColorChooser;
    public static double driveP = 0.2, driveI = 0.01, driveD=0.1, driveF = 0.15;
    public static double shootP = 0.2, shootI = 0.01, shootD=0.1, shootF = 0.02, shootVel = 1000;
    public static double debugShooterVelocity = 0;
    private long lastUpdate = 0;
    private int updateDelay = 100;
    public static AllianceColor RobotColor; 
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
        autoModeChooser = new SendableChooser<CommandGroup>();
        autoModeChooser.addDefault("Default Auto", new TestAutoVision());
        autoModeChooser.addObject("My Auto", new ShooterTest());
        autoColorChooser = new SendableChooser<AllianceColor>();
        autoColorChooser.addDefault("Auto Color Red", AllianceColor.RED);
        autoColorChooser.addObject("Auto Color Blue", AllianceColor.BLUE);
        vision = new VisionManager();
    	new Thread(vision).start();
        SmartDashboard.putData("Auto mode", autoModeChooser);
        
        SmartDashboard.putData("Enable Tank Drive", new CMDTeleOpTankDrive());
        SmartDashboard.putNumber("driveP", driveP);
        SmartDashboard.putNumber("driveI", driveI);
        SmartDashboard.putNumber("driveD", driveD);
        SmartDashboard.putNumber("driveF", driveF);
        SmartDashboard.putNumber("shootP", shootP);
        SmartDashboard.putNumber("shootI", shootI);
        SmartDashboard.putNumber("shootD", shootD);
        SmartDashboard.putNumber("shootF", shootF);
        SmartDashboard.putNumber("shootVel", shootVel);
        SmartDashboard.putNumber("debugShooterVelocity", debugShooterVelocity);
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
    	updateSmartDashboard();
    	RobotColor = (AllianceColor) autoColorChooser.getSelected();
        autonomousCommand = (CommandGroup) autoModeChooser.getSelected();
        if (autonomousCommand != null && RobotColor != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	updateSmartDashboard();
    	
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
    	 long time = System.currentTimeMillis();
        Scheduler.getInstance().run();
        
        
    	
        if (time - lastUpdate > updateDelay) {
    		updateSmartDashboard();
    	}
       
        if(switchCamera){
        	
        	//CameraServer.getInstance().startAutomaticCapture(cameras[currentCamera]);
        	
        	switchCamera = false;
        }

    }
    private void updateSmartDashboard(){
    	 SmartDashboard.putNumber("debugShooterVelocity", debugShooterVelocity);
    	 SmartDashboard.putString("Current Drive Mode: ", currentTeleOpDriveMode ? "Mecanum" : "Tank");
    	 updatePID();
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    private void updatePID(){
		driveP=SmartDashboard.getNumber("driveP", driveP);
		driveI=SmartDashboard.getNumber("driveI", driveI);
		driveD=SmartDashboard.getNumber("driveD", driveD);
		driveF=SmartDashboard.getNumber("driveF", driveF);
		shootP=SmartDashboard.getNumber("shootP", shootP);
		shootI=SmartDashboard.getNumber("shootI", shootI);
		shootD=SmartDashboard.getNumber("shootD", shootD);
		shootF=SmartDashboard.getNumber("shootF", shootF);
		shootVel=SmartDashboard.getNumber("shootVel", shootVel);
	}
}