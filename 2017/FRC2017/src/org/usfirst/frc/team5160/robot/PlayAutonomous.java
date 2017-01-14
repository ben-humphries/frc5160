package org.usfirst.frc.team5160.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PlayAutonomous extends Command {
	
	Scanner scanner;
	
	long startTime;
	
	boolean nextLine = false;
	
	Talon testTalon = new Talon(0);

    public PlayAutonomous(String fileDirectory) throws FileNotFoundException{
        
    	scanner = new Scanner(new File(fileDirectory));
    	scanner.useDelimiter(",|\\n");
    	
    }
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    }

    protected void execute() {
    	
    	float instancedStartTime = 0;
    	if(scanner.hasNextFloat()){
    		
    		if(nextLine){
    			instancedStartTime = scanner.nextFloat();
    		}
    		if(instancedStartTime - (System.currentTimeMillis() - startTime) <= 0){
    			
    			//set motor values
    			//Example:
    			//robot.frontLeftMotor.set(scanner.nextFloat());
    			
    			testTalon.setPosition(scanner.nextDouble());
    			
    			nextLine = true;
    		}else{
    			nextLine = false;
    		}
    	}else{
    		end();
    	}
    }

    protected void end() {
    	scanner.close();
    }
    
    protected void interrupted() {
    	scanner.close();
    }

	protected boolean isFinished() {
		return false;
	}
}
