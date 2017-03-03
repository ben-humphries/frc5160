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
	
	
	public static final int shooterId = 0; 
	
	public VisionProcessorBoiler boilerProcessor;
	public MjpegServer streamer;
	public CvSource outputStream;
	public UsbCamera boilerCam;
	public CvSink  boilerSink;
	
	private static final long MinElapsedMilli = 20;
	private long lastTime = 0;
	
	public VisionManager(){
		outputStream = CameraServer.getInstance().putVideo("OutputStream", 240, 160);
		streamer = CameraServer.getInstance().addServer("Streamer");	

		boilerSink = new CvSink("boiler");
		
		boilerCam = new UsbCamera("boiler", shooterId);
		
		
		
		boilerProcessor = new VisionProcessorBoiler();
		
		boilerProcessor.draw=true;
	}

	@Override
	public void run() {
		try{
		  streamer.setSource(outputStream);
		  
		  boilerSink.setSource(boilerCam);
		  
		  Mat boilerImage = new Mat();
		  
		  boilerCam.setExposureManual(-5);
		  while(!Thread.interrupted()) {
			  if(true){
			 
			  
			  boilerSink.grabFrame(boilerImage);
			  boilerProcessor.process(boilerImage);
			  
			  
              outputStream.putFrame(boilerProcessor.drawnContours);
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
