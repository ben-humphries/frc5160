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

public class VisionProcessor {
	
	public static final int medianBlurSize = 3, 
			resizeX = 480, 
			resizeY = 360,
			cameraFOVAngle = 40,
			cameraPitchAngle = 0
			;
	
	
	private VideoCapture camera;
	
	public Mat image = new Mat(), medianBlur = new Mat(), resized = new Mat(), 
			greenOnly = new Mat(), redOnly = new Mat(), blueOnly = new Mat(),	
			greenChannelThreshold = new Mat(), drawnContours = new Mat(480,360,16), sum = new Mat(); 
	
	public VisionProcessor(){
		camera = new VideoCapture(1);
		camera.set(Videoio.CAP_PROP_EXPOSURE, -10);
	}
	public Mat process(){
		camera.read(image);
		
		//image.release();
		
		return process(image);
	}
	public Mat process(Mat picture){
		
		
		
		Imgproc.medianBlur(picture, medianBlur, medianBlurSize);
		Imgproc.resize(medianBlur, resized, new Size(resizeX,resizeY));
		
		Core.extractChannel(resized, redOnly, 2);
		Core.extractChannel(resized, greenOnly, 1);
		Core.extractChannel(resized, blueOnly, 0);
		
		sum.release();
		sum = new Mat();
		
		Core.addWeighted(greenOnly, 1, blueOnly, -0.2, 1, sum);
		Core.addWeighted(sum, 1, redOnly, -0.5, 1, sum);
		Imgproc.threshold(sum, greenChannelThreshold, 0, 255, Imgproc.THRESH_OTSU);
		
		Mat contouredGreen = greenChannelThreshold.clone();
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		Imgproc.findContours(contouredGreen, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		
		contouredGreen.release();
		drawnContours.release();
		
		drawnContours = Mat.zeros(resized.size(), 16);
		ArrayList<MatOfPoint> hulls = new ArrayList<MatOfPoint>();
		
		double topArea = 0;
		double bottomArea = 0;
		MatOfPoint topContour=null;
		MatOfPoint bottomContour=null;
		
		for(MatOfPoint contour : contours){			
			double ac = Imgproc.contourArea(contour);
			
			if(ac > topArea){
				System.out.println("ta = "+ac);
				bottomContour = topContour;
				bottomArea = topArea;
				topContour = contour;
				topArea=ac;
				
			}
			else if(ac>bottomArea){
				System.out.println("ba = "+ac);
				bottomContour = contour;
				bottomArea=ac;
			}
		}
		if(topContour!=null){
			Rect topBound = Imgproc.boundingRect(topContour);
			Imgproc.rectangle(drawnContours, topBound.br(),topBound.tl(), new Scalar(0,0,255));
		}
		if(bottomContour!=null){
			Rect bottomBound = Imgproc.boundingRect(bottomContour);
			Imgproc.rectangle(drawnContours, bottomBound.br(),bottomBound.tl(), new Scalar(0,0,255));
		}
		Imgproc.drawContours(drawnContours,contours,-1,new Scalar(0,255,0),2);
		if(topContour!=null && bottomContour!=null){
			computeDistance(topContour, bottomContour);
			
		}
		
		for (MatOfPoint p : contours){
			p.release();
		}
		contours.clear();
		
		return drawnContours;
	}
	public void computeDistance(MatOfPoint topContour, MatOfPoint bottomContour){
		Rect bottomBound = Imgproc.boundingRect(bottomContour);
		Rect topBound = Imgproc.boundingRect(topContour);
		
		double top = topBound.tl().y;
		double bottom = bottomBound.tl().y;
		double deltaPxTargets = bottom-top;
		double deltaDegTargets = deltaPxTargets*cameraFOVAngle/resizeY;
		
		
		System.out.println(deltaDegTargets+",   "+deltaPxTargets+",   "+4.0/Math.tan(Math.toRadians(deltaDegTargets/2)));
	}
}
