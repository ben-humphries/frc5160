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

public class VisionProcessorGear extends SimpleVisionProcessor{
	
	public Mat drawnContours; 
	public boolean draw = false;
	private double deltaAngle; 
	private double distance;
	//values in inches
	public static final double CenterBandToCenterBandDistance = 8.2;
	public VisionProcessorGear() {
		super();
		drawnContours = new Mat(resizeX,resizeY,16);
	}
	
	
	public void process(Mat picture){
		
		resize(picture);
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
			distance = computeDistanceGear(top[0], top[1]);
			deltaAngle = computeDeltaAngle(top[0], top[1]);
		}
		else{
			
		}
		
		for (MatOfPoint p : contours){
			p.release();
		}
		contours.clear();
		
	}
	public double computeDeltaAngle(MatOfPoint topContour, MatOfPoint bottomContour){
		Rect bottomBound = Imgproc.boundingRect(bottomContour);
		Rect topBound = Imgproc.boundingRect(topContour);
		double top = topBound.tl().x;
		double bottom = bottomBound.tl().x;
		double av = (top+bottom)/2.0;
		return (av-resizeX/2.0)*pxToDegHorizontal;
	}
	public double computeDistanceGear(MatOfPoint topContour, MatOfPoint bottomContour){
		Rect bottomBound = Imgproc.boundingRect(bottomContour);
		Rect topBound = Imgproc.boundingRect(topContour);
		
		double top = (topBound.tl().x+topBound.br().x)/2;
		double bottom = (bottomBound.tl().x+bottomBound.br().x)/2;
		double deltaPxTargets = bottom-top;
		double deltaDegTargets = deltaPxTargets*pxToDegHorizontal;
		
		return CenterBandToCenterBandDistance/(2*Math.tan(Math.toRadians(deltaDegTargets)));
	}
	public double getDeltaAngle() {
		return deltaAngle;
	}
	public double getDistance(){
		return distance;
	}
}
