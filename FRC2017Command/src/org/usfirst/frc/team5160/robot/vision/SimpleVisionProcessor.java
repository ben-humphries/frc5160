package org.usfirst.frc.team5160.robot.vision;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class SimpleVisionProcessor {
	
	
	public static final int	resizeX = 240, 
			resizeY = 160,
			cameraFOVAngleHorizontal = 60,
			cameraFOVAngleVertical=40,
			cameraPitchAngle = 0;
	protected final double pxToDegHorizontal = (double)cameraFOVAngleHorizontal/(double)resizeX;
	protected final double pxToDegVertical = (double)cameraFOVAngleVertical/(double)resizeY;
	private final long MinElapsedMilli = 15;
	private long lastTime = 0;
	protected Mat resized = new Mat(), 
			greenOnly = new Mat(), redOnly = new Mat(), blueOnly = new Mat(),	
			greenChannelThreshold = new Mat(),  sum = new Mat();
	
	public void resize(Mat image){
		Imgproc.resize(image, resized, new Size(resizeX,resizeY));
	}
	public void extractChannels(){
		Core.extractChannel(resized, redOnly, 2);
		Core.extractChannel(resized, greenOnly, 1);
		Core.extractChannel(resized, blueOnly, 0);
	}
	public void sumChannels(){
		sum.release();
		sum = new Mat();
		
		Core.addWeighted(greenOnly, 1, blueOnly, -0.2, 1, sum);
		Core.addWeighted(sum, 1, redOnly, -0.5, 1, sum);
		Imgproc.threshold(sum, greenChannelThreshold, 0, 255, Imgproc.THRESH_OTSU);
	}
	public ArrayList<MatOfPoint> findContours(){
		Mat contouredGreen = greenChannelThreshold.clone();
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		Imgproc.findContours(contouredGreen, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		contouredGreen.release();
		return contours;
	}
	public MatOfPoint[] findTwoLargestContours(ArrayList<MatOfPoint> contours){
		MatOfPoint[] tmp = new MatOfPoint[2];
		
		double topArea = 0;
		double bottomArea = 0;
		
		for(MatOfPoint contour : contours){			
			double ac = Imgproc.contourArea(contour);
			
			if(ac > topArea){
				tmp[1] = tmp[0];
				bottomArea = topArea;
				tmp[0] = contour;
				topArea=ac;
				
			}
			else if(ac>bottomArea){
				tmp[1] = contour;
				bottomArea=ac;
			}
		}
		return tmp;
	}
	
}
