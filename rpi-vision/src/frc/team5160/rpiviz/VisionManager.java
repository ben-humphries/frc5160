package frc.team5160.rpiviz;

import org.opencv.core.Core;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionManager {
	
	private static VisionManager _instance = new VisionManager(); 
	
	private NetworkTable piTable;
	
	public static final int gearCamId = 0;
	public static final int boilerCamId = 1;
	
	VisionProcessorBoiler boilerProcessor;
	VisionProcessorGear gearProcessor;
	
	private VisionManager(){
		//Load OpenCV
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//System.loadLibrary("opencv_ffmpeg300_64");
		

		
		//Initialize Vision processing
		boilerProcessor = new VisionProcessorBoiler(boilerCamId);
		gearProcessor = new VisionProcessorGear(gearCamId);
	}
	public static VisionManager GetInstance(){
		return _instance; 
	}
}
