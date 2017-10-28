package org.usfirst.frc.team5160.robot.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionManager implements Runnable{
	
	
	public static final int shooterId = 0; 
	
	public VisionProcessorGear gearProcessor;
	public VideoSink server;
	public CvSource outputStream;
	public UsbCamera gearCam;
	public CvSink  gearSink;
	
	private static final long MinElapsedMilli = 20;
	private long lastTime = 0;
	
	public VisionManager(){
		gearCam = CameraServer.getInstance().startAutomaticCapture(shooterId);
		gearCam.setExposureManual(1);
		server = CameraServer.getInstance().getServer();
		gearSink = new CvSink("gear");
		gearSink.setSource(gearCam);
		gearSink.setEnabled(true);
		gearProcessor = new VisionProcessorGear();
		
		gearProcessor.draw=true;
	}

	@Override
	public void run() {
		try{		  
		  Mat gearImage = new Mat();
		  
		  while(!Thread.interrupted()) {
			  if(true){
			  
			  gearSink.grabFrame(gearImage);
			  gearProcessor.process(gearImage);
			  
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