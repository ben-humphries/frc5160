package org.usfirst.frc.team5160.robot.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionManager{
	
	
	public static final int shooterId = 0; 
	
	public VisionProcessorGear gearProcessor;
	public MjpegServer streamer;
	public CvSource outputStream;
	public UsbCamera gearCam;
	public CvSink  gearSink;
	
	private static final long MinElapsedMilli = 20;
	private long lastTime = 0;
	
	public VisionManager(){
		
		gearProcessor = new VisionProcessorGear();
		
		gearProcessor.draw=true;
		gearSink = new CvSink("gear");
		gearSink.setSource(Robot.camera);
	}

	public void run() {
		try{
		  Mat gearImage = new Mat();
		  
		  Robot.camera.setExposureManual(-1);
			gearSink.grabFrame(gearImage);
			  
			  gearProcessor.process(gearImage);
			  
			  
			gearImage.release();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private boolean enoughTimeElapsed(){
		if(System.currentTimeMillis()-lastTime >= MinElapsedMilli){
			lastTime = System.currentTimeMillis();
			return true;
		}
		else{
			return false;
		}
	}
	
}
