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
	private double deltaAngle; 
	private double distance;
	//Values in inches and degrees
	public static final double BoilerTopBandPeakHeight = 88,
						BoilerBottomBandPeakHeight = 80,
						CameraHeight = 18,
						CameraCenterLineAngle = 30;
						
	public VisionProcessorBoiler() {
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
		drawnContours = picture.clone();
		Imgproc.drawContours(drawnContours,contours,-1,new Scalar(0,255,0),2);
		}
		
		MatOfPoint[] top = findTwoLargestContours(contours);
		
		
		if(top[0]!=null && top[1]!=null){
			distance = computeDistanceBoiler(top[0], top[1]);
			deltaAngle = computeDeltaAngle(top[0], top[1]);
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
	//	System.out.println(top);
		return (av-resizeX/2.0)*pxToDegHorizontal;
	}
	public double computeDistanceBoiler(MatOfPoint topContour, MatOfPoint bottomContour){
		Rect bottomBound = Imgproc.boundingRect(bottomContour);
		Rect topBound = Imgproc.boundingRect(topContour);
		
		double top = topBound.tl().y;
		double bottom = bottomBound.tl().y;
		double bottomCameraAngle = CameraCenterLineAngle - cameraFOVAngleHorizontal/2d;
		double angleToBottom = bottom*pxToDegVertical+bottomCameraAngle;
		double angleToTop = bottom*pxToDegVertical+bottomCameraAngle;
		double distanceByBottom = (BoilerBottomBandPeakHeight-CameraHeight)/Math.tan(Math.toDegrees(angleToBottom));
		double distanceByTop = (BoilerTopBandPeakHeight-CameraHeight)/Math.tan(Math.toDegrees(angleToTop));
	//	System.out.println("Distances, B,T,A : "+distanceByBottom+", "+distanceByTop+", "+(distanceByBottom+distanceByTop)/2d);
		return ((distanceByBottom+distanceByTop)/2d);
	}

	public double getDeltaAngle() {
		return deltaAngle;
	}
	public double getDistance(){
		return distance;
	}
}
