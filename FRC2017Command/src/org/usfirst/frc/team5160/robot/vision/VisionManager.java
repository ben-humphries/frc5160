package org.usfirst.frc.team5160.robot.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionManager implements Runnable{
	
	
	public static final int gearId = 0, shooterId = 1, intakeId = 2; 
	
	public VisionProcessorBoiler boilerProcessor;
	public VisionProcessorGear gearProcessor;
	public UsbCamera gearCam, boilerCam, intakeCam;
	public CvSink gearSink, boilerSink, intakeSink;
	
	private static final long MinElapsedMilli = 40;
	private long lastTime = 0;
	
	public VisionManager(){
		gearSink = new CvSink("gear");
		boilerSink = new CvSink("boiler");
		intakeSink = new CvSink("intake");
		
		gearCam = new UsbCamera("gear", gearId);
		boilerCam = new UsbCamera("boiler", shooterId);
		intakeCam = new UsbCamera("intake", intakeId);
		
		gearCam.setResolution(SimpleVisionProcessor.resizeX, SimpleVisionProcessor.resizeY);
		boilerCam.setResolution(SimpleVisionProcessor.resizeX, SimpleVisionProcessor.resizeY);
		intakeCam.setResolution(SimpleVisionProcessor.resizeX, SimpleVisionProcessor.resizeY);
		
		gearProcessor = new VisionProcessorGear();
		boilerProcessor = new VisionProcessorBoiler();
		
		gearProcessor.draw=true;
		boilerProcessor.draw=true;
		CameraServer.getInstance().startAutomaticCapture(gearCam);
		CameraServer.getInstance().startAutomaticCapture(boilerCam);
		CameraServer.getInstance().startAutomaticCapture(intakeCam);
		
	}

	@Override
	public void run() {
		try{
		  
		  gearSink.setSource(gearCam);
		  boilerSink.setSource(boilerCam);
		  intakeSink.setSource(intakeCam);
		  
		  Mat boilerImage = new Mat();
		  Mat gearImage = new Mat();
		  Mat intakeImage = new Mat();
		  gearCam.setExposureManual(-5);
		  boilerCam.setExposureManual(-5);
		  intakeCam.setFPS(20);
		  while(!Thread.interrupted()) {
			  if(enoughTimeElapsed()){
			 
			  gearSink.grabFrame(gearImage);
			  gearProcessor.process(gearImage);
			  
			  boilerSink.grabFrame(boilerImage);
			  boilerProcessor.process(boilerImage);
			  
			  intakeSink.grabFrame(intakeImage);
			  if(Robot.currentCamera == 0){

			  }
			  else if (Robot.currentCamera==1){
			  
			  }
			  else{

			  }
			  }
          }
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
