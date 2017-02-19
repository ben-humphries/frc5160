package org.usfirst.frc.team5160.robot.vision;

import org.opencv.core.Core;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionManager {
	
	private static VisionManager _instance = null; 
	
	
	public static final int gearCamId = 0;
	public static final int boilerCamId = 1;
	
	public VisionProcessorBoiler boilerProcessor;
	public VisionProcessorGear gearProcessor;
	
	private VisionManager(){
		boilerProcessor = new VisionProcessorBoiler(boilerCamId);
		gearProcessor = new VisionProcessorGear(gearCamId);
	}
	
	
	public static VisionManager GetInstance(){
		if(_instance == null){
			_instance = new VisionManager();
		}
		return _instance; 
	}
}
