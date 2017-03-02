package org.usfirst.frc.team5160.robot.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionManager implements Runnable{
	
	
	public static final int gearId = 0, shooterId = 1, intakeId = 2; 
	
	public VisionProcessorBoiler boilerProcessor;
	public VisionProcessorGear gearProcessor;
	public MjpegServer streamer;
	public CvSource outputStream;
	public UsbCamera gearCam, boilerCam, intakeCam;
	public CvSink gearSink, boilerSink, intakeSink;
	
	private static final long MinElapsedMilli = 20;
	private long lastTime = 0;
	
	public VisionManager(){
		outputStream = CameraServer.getInstance().putVideo("OutputStream", 240, 160);
		streamer = CameraServer.getInstance().addServer("Streamer");	
		
		gearSink = new CvSink("gear");
		boilerSink = new CvSink("boiler");
		intakeSink = new CvSink("intake");
		
		gearCam = new UsbCamera("gear", gearId);
		boilerCam = new UsbCamera("boiler", shooterId);
		intakeCam = new UsbCamera("intake", intakeId);
		
		
		
		gearProcessor = new VisionProcessorGear();
		boilerProcessor = new VisionProcessorBoiler();
		
		gearProcessor.draw=true;
		boilerProcessor.draw=true;
	}

	@Override
	public void run() {
		try{
		  streamer.setSource(outputStream);
		  
		  gearSink.setSource(gearCam);
		  boilerSink.setSource(gearCam);
		  intakeSink.setSource(gearCam);
		  
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
			  
              outputStream.putFrame(gearProcessor.drawnContours);
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
