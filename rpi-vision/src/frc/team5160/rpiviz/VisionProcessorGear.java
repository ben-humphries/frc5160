package frc.team5160.rpiviz;

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
	
	Mat drawnContours; 
	
	public VisionProcessorGear(int cameraId) {
		super(cameraId);
		drawnContours = new Mat(resizeX,resizeY,16);
	}
	
	public Mat process(){
		camera.read(image);
		return process(image);
	}
	public Mat process(Mat picture){
		
		resize();
		extractChannels();
		sumChannels();
		ArrayList<MatOfPoint> contours = findContours();
		
		drawnContours.release();
		
		drawnContours = Mat.zeros(resized.size(), 16);
		
		MatOfPoint[] top = findTwoLargestContours(contours);
		
		Imgproc.drawContours(drawnContours,contours,-1,new Scalar(0,255,0),2);
		if(top[0]!=null && top[1]!=null){
			computeDistanceGear(top[0], top[1]);
		}
		
		for (MatOfPoint p : contours){
			p.release();
		}
		contours.clear();
		
		return drawnContours;
	}
	
	public double computeDistanceGear(MatOfPoint topContour, MatOfPoint bottomContour){
		Rect bottomBound = Imgproc.boundingRect(bottomContour);
		Rect topBound = Imgproc.boundingRect(topContour);
		
		double top = topBound.tl().y;
		double bottom = bottomBound.tl().y;
		double deltaPxTargets = bottom-top;
		double deltaDegTargets = deltaPxTargets*cameraFOVAngle/resizeY;
		
		
		return 4.0/Math.tan(Math.toRadians(deltaDegTargets/2));
	}
}
