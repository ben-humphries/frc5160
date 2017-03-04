
package org.usfirst.frc.team5160.robot;

import org.usfirst.frc.team5160.robot.autonomous.BoilerSideAuto;
import org.usfirst.frc.team5160.robot.autonomous.FarSideAuto;
import org.usfirst.frc.team5160.robot.autonomous.HopperShooterAuto;
import org.usfirst.frc.team5160.robot.autonomous.MiddleAuto;
import org.usfirst.frc.team5160.robot.autonomous.ShooterTest;
import org.usfirst.frc.team5160.robot.autonomous.TestAutoEncoders;
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

	//Create subsystems
	public static final Base BASE = new Base();
	public static final Climber CLIMBER = new Climber();
	public static final GearMechanism GEAR_MECHANISM = new GearMechanism();
	public static final IntakeMechanism INTAKE_MECHANISM = new IntakeMechanism();
	public static final Shooter SHOOTER = new Shooter();
	public static OI oi;
	public static VisionManager vision;
	
	//statics for conviniece, shared values, etc.
	public static boolean currentTeleOpDriveMode = true;
	public static int currentCamera = 0;
	public static boolean switchCamera = false;
	
	
	//Dashboard widgets
	 private SendableChooser autoModeChooser;
	 private Command autonomousCommand;
	 private SendableChooser autoColorChooser;
	public static double fShoot=0.014, pShoot = 0.12, iShoot = 0, dShoot = 0.5;
	 public static AllianceColor RobotColor; 
    
	 //Testing values for shooter
    public static double shootVel = 2000;
    public static double debugShooterVelocity = 0;
   
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
		
    	oi = new OI();
		
    	//Initialize auto chooser
        autoModeChooser = new SendableChooser();
        autoModeChooser.addDefault("Vision Test", new TestAutoVision()); //Nothing damaging
        autoModeChooser.addObject("Shooter Test", new ShooterTest());
        autoModeChooser.addObject("Encoder Test", new TestAutoEncoders());
        autoModeChooser.addObject("Boiler Side Auto", new BoilerSideAuto());
        autoModeChooser.addObject("Middle Auto", new MiddleAuto());
        autoModeChooser.addObject("Far Side Auto", new FarSideAuto());
        autoModeChooser.addObject("Hooper Shooting Auto", new HopperShooterAuto());
        SmartDashboard.putData("Auto mode", autoModeChooser);
        
        //Initialize color chooser
        autoColorChooser = new SendableChooser();
        autoColorChooser.addDefault("Auto Color Red", AllianceColor.RED); //Red is what the autos are default programmed to
        autoColorChooser.addObject("Auto Color Blue", AllianceColor.BLUE);
        SmartDashboard.putData("Alliance Selector", autoColorChooser);
        
        //Start vision
        vision = new VisionManager();
    	new Thread(vision).start();
    	
        
        SmartDashboard.putData("Enable Tank Drive", new CMDTeleOpTankDrive()); //Put other things into dashboard
        SmartDashboard.putNumber("shootVel", shootVel);
        SmartDashboard.putNumber("fShoot", fShoot);
        SmartDashboard.putNumber("pShoot", pShoot);
        SmartDashboard.putNumber("iShoot", iShoot);
        SmartDashboard.putNumber("dShoot", dShoot);
        SmartDashboard.putNumber("debugShooterVelocity", debugShooterVelocity);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    @Override
    public void disabledInit(){

    }
	@Override
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
    	//Check dashboard values.
    	updateSmartDashboard();
    	RobotColor = (AllianceColor) autoColorChooser.getSelected();
        autonomousCommand = (CommandGroup) autoModeChooser.getSelected();
        System.out.println("autoCommand"+autonomousCommand);
    	try{
    		//Run autonomous
        if (autonomousCommand != null && RobotColor != null) autonomousCommand.start();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	updateSmartDashboard(); //Update dashboard
        Scheduler.getInstance().run(); //Continue running
    }

    public void teleopInit() {
    	updateSmartDashboard(); //Update dashboard
    	
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
    	updateSmartDashboard();     //Continue running
        Scheduler.getInstance().run(); 

    }
    private void updateSmartDashboard(){
    	System.out.println("Updating SDB");
    	 SmartDashboard.putNumber("debugShooterVelocity", debugShooterVelocity);
    	 SmartDashboard.putString("Current Drive Mode: ", currentTeleOpDriveMode ? "Mecanum" : "Tank");
    	 fShoot = SmartDashboard.getNumber("fShoot",0.02);
    	 pShoot = SmartDashboard.getNumber("pShoot",0.02);
    	 iShoot = SmartDashboard.getNumber("iShoot",0.02);
    	 dShoot = SmartDashboard.getNumber("dShoot",0.02);
    	 SmartDashboard.getNumber("pShoot");
    	 shootVel=SmartDashboard.getNumber("shootVel", shootVel);
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    //Default is red/1
    public static int autoColorMultiplier(){
    	if(RobotColor == AllianceColor.BLUE){
    		return -1;
    	}
    	return 1;
    }

}