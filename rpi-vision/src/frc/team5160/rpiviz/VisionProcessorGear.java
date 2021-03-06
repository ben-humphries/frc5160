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
	boolean draw = false;
	public VisionProcessorGear(int cameraId) {
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
			computeDistanceGear(top[0], top[1]);
			computeDeltaAngle(top[0], top[1]);
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
		return (av-resizeX/2.0)*pxToDeg;
	}
	public double computeDistanceGear(MatOfPoint topContour, MatOfPoint bottomContour){
		Rect bottomBound = Imgproc.boundingRect(bottomContour);
		Rect topBound = Imgproc.boundingRect(topContour);
		
		double top = topBound.tl().x;
		double bottom = bottomBound.tl().x;
		double deltaPxTargets = bottom-top;
		double deltaDegTargets = deltaPxTargets*pxToDeg;
		
		
		return 12/Math.tan(Math.toRadians(deltaDegTargets/2));
	}
}
