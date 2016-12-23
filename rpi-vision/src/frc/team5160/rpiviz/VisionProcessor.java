package frc.team5160.rpiviz;

import java.awt.image.ImageProducer;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class VisionProcessor {
	
	VideoCapture camera;
	ArrayList<Mat> debug;
	public VisionProcessor(){
		camera = new VideoCapture("http://pr_nh_webcam.axiscam.net:8000/mjpg/video.mjpg");
		System.out.print(camera.grab());
		debug = new ArrayList<Mat>();
	}
	public Mat process(){
		Mat image = new Mat();
		camera.read(image);
		//Mat m = process(image,false);
		//image.release();
		return image;
	}
	public Mat process(Mat picture, boolean isDebug){
		wipeDebug();
		int medianBlurSize = 3, 
				resizeX = 480, 
				resizeY = 360, 
				greenChannelMin = 80; 
		Mat medianBlur = ImageOps.sameSizeMat(picture),
				resized = new Mat(resizeX, resizeY, picture.type()), 
				greenChannelThreshold = new Mat(resized.size(),Imgproc.COLOR_GRAY2BGR);
		Imgproc.medianBlur(picture, medianBlur, medianBlurSize);
		Imgproc.threshold(medianBlur, greenChannelThreshold, greenChannelMin, 255, Imgproc.THRESH_BINARY);
		//If not debugging erase unneeded pictures, otherwise add them to debug arraylist
		if(!isDebug){
			//wipeAll(resized, medianBlur);
		}
		else{
			addAll(resized, medianBlur, greenChannelThreshold.clone());
		}
		return resized;
	}
	
	public void wipeDebug(){
		for (Mat m : debug){
			m.release();
		}
		debug.clear();
	}
	public void wipeAll(Mat...mats){
		for (Mat m : mats){
			m.release();
		}
	}
	public void addAll(Mat...mats){
		for (Mat m : mats){
			debug.add(m);
		}
	}
}
