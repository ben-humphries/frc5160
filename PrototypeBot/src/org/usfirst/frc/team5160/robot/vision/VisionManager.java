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
	
	
	public static final int gearId = 0, shooterId = 0, intakeId = 0; 
	
	public VisionProcessorBoiler boilerProcessor;
	public VisionProcessorGear gearProcessor;
	public MjpegServer streamer;
	public CvSource outputStream;
	public UsbCamera gearCam, boilderCam, intakeCam;
	public CvSink cvSink;
	
	private static final long MinElapsedMilli = 40;
	private long lastTime = 0;
	
	public VisionManager(){
		outputStream = CameraServer.getInstance().putVideo("OutputStream", 160, 120);
		streamer = CameraServer.getInstance().addServer("Streamer");	
		gearCam = new UsbCamera("gear", gearId);
		gearCam.setExposureManual(-5);
		cvSink = new CvSink("sink");
		//gearCam = new UsbCamera("gear", shooterId);
		//boilerProcessor = new VisionProcessorBoiler(shooterId);
		gearProcessor = new VisionProcessorGear();
	//	intakeCam = new VideoCapture(intakeId);
	}

	@Override
	public void run() {
		  streamer.setSource(outputStream);
		  cvSink.setSource(gearCam);
		  Mat image = new Mat();
		  while(!Thread.interrupted()) {
			  gearCam.setExposureManual(-5);
			  cvSink.grabFrame(image);
			  gearProcessor.process(image);
              outputStream.putFrame(gearProcessor.sum);
             
          }
	}
	
	
}
