package org.usfirst.frc.team5160.robot.vision;

public class VisionManager {
	public VisionProcessorGear gearProcessor;
	public VisionProcessorBoiler boilerProcessor;
	public VisionManager(){
		gearProcessor = new VisionProcessorGear();
		boilerProcessor = new VisionProcessorBoiler();
	}
}
