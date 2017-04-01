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
	public GearTracker ground; 
	public CvSink  gearSink;
	public static double deltaAngle = 0; 
	private static final long MinElapsedMilli = 20;
	private long lastTime = 0;
	boolean dark = true; 
	public VisionManager(){
		
		gearProcessor = new VisionProcessorGear();
		ground = new GearTracker();
		gearProcessor.draw=true;
		gearSink = new CvSink("gear");
		gearSink.setSource(Robot.camera);
		Robot.camera.setExposureManual(1);
	}

	public void run() {
		if(enoughTimeElapsed())
		try{
		  Mat gearImage = new Mat();
		  
			gearSink.grabFrame(gearImage);
			  if(dark){
			  gearProcessor.process(gearImage);
			  }
			  else{
				  deltaAngle = ground.process(gearImage);
			  }
			  
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
	
	public void setNormal(){
		dark = false;
	}
	public void setDark(){
		dark = true;
		
	}
	
}
