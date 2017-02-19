package org.usfirst.frc.team5160.robot.vision;

import java.awt.image.ImageProducer;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class VisionProcessorBoiler extends SimpleVisionProcessor{
	
	public Mat drawnContours; 
	public boolean draw = false;
	public VisionProcessorBoiler(int cameraId) {
		super(cameraId);
		drawnContours = new Mat(resizeX,resizeY,16);
	}
	
	public void process(){
		camera.read(image);
		process(image);
	}
	public void process(Mat picture){
		
		resize();
		extractChannels();
		sumChannels();
		ArrayList<MatOfPoint> contours = findContours();
		
		if(draw){
		drawnContours.release();
		drawnContours = Mat.zeros(resized.size(), 16);
		Imgproc.drawContours(drawnContours,contours,-1,new Scalar(0,255,0),2);
		}
		
		MatOfPoint[] top = findTwoLargestContours(contours);
		
		
		if(top[0]!=null && top[1]!=null){
			computeDistanceBoiler(top[0], top[1]);
		}
		
		for (MatOfPoint p : contours){
			p.release();
		}
		contours.clear();
		
	}
	public void computeDistanceBoiler(MatOfPoint topContour, MatOfPoint bottomContour){
		Rect bottomBound = Imgproc.boundingRect(bottomContour);
		Rect topBound = Imgproc.boundingRect(topContour);
		
		double top = topBound.tl().y;
		double bottom = bottomBound.tl().y;
		double deltaPxTargets = bottom-top;
		double deltaDegTargets = deltaPxTargets*cameraFOVAngle/resizeY;
		
		
		System.out.println(deltaDegTargets+",   "+deltaPxTargets+",   "+4.0/Math.tan(Math.toRadians(deltaDegTargets/2)));
	}
}
