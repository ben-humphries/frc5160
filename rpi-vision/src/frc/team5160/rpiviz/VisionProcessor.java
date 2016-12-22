package frc.team5160.rpiviz;

import org.opencv.videoio.VideoCapture;

public class VisionProcessor {
	
	VideoCapture camera;
	public VisionProcessor(){
		camera = new VideoCapture(0);
	}
}
