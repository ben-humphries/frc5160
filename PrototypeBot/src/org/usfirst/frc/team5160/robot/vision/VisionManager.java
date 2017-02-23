package org.usfirst.frc.team5160.robot.vision;

import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionManager implements Runnable{
	
	
	public static final int gearId = 0, shooterId = 0, intakeId = 0; 
	
	public VisionProcessorBoiler boilerProcessor;
	public VisionProcessorGear gearProcessor;
	public VideoCapture intakeCam;
	
	public VisionManager(){
	//	boilerProcessor = new VisionProcessorBoiler(shooterId);
		gearProcessor = new VisionProcessorGear(gearId);
	//	intakeCam = new VideoCapture(intakeId);
	}

	@Override
	public void run() {
		  CvSource outputStream = CameraServer.getInstance().putVideo("Yay", 160, 120);
		  MjpegServer streamer = CameraServer.getInstance().addServer("Yay");	
		  streamer.setSource(outputStream);
		  while(!Thread.interrupted()) {
			  gearProcessor.process();
              outputStream.putFrame(gearProcessor.image);
             
          }
	}
	
	
}
