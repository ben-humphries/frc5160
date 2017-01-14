package org.usfirst.frc.team5160.robot;

import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RecordAutonomous extends Command {
	
	FileWriter writer;
	
	long startTime;
	
	float millisPerTick;
	float lastTime;
	
	Talon testTalon = new Talon(0);
	
    public RecordAutonomous(String fileDirectory, int ticksPerSecond) throws IOException {
    	
    	writer = new FileWriter(fileDirectory);
    	
    	millisPerTick = 1000/ticksPerSecond;
    	
    	
    }

    protected void initialize() {
    	
    	startTime = System.currentTimeMillis();
    	
    }

    protected void execute() {
    	if(System.currentTimeMillis() - lastTime >= millisPerTick){
    		lastTime = System.currentTimeMillis();
    		
    		float instancedStartTime = System.currentTimeMillis() - startTime;
    		double motorValue = testTalon.getPosition(); //Get motor values
    		
    		
    		
    		try {
    			writer.append("" + instancedStartTime);
    			
    			writer.append(motorValue + ",");
    			
    			writer.append("\n");
    			
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    	}
    }



    protected void end() {
    	try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }


    protected void interrupted(){
    	end();
    	
    }
    
    protected boolean isFinished() {
        return false;
    }
}
