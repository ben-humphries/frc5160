package org.usfirst.frc.team5160.robot.vision;

import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionManager {
	
	private static VisionManager _instance = null; 
	
	
	
	public VisionProcessorBoiler boilerProcessor;
	public VisionProcessorGear gearProcessor;
	
	private VisionManager(VideoCapture gearCam, VideoCapture shooterCam){
		boilerProcessor = new VisionProcessorBoiler(shooterCam);
		gearProcessor = new VisionProcessorGear(gearCam);
	}
	
	public static void CreateInstance(VideoCapture gearCam, VideoCapture shooterCam){
		_instance = new VisionManager(gearCam, shooterCam);
	}
	
	public static VisionManager GetInstance(){
		return _instance; 
	}
}
