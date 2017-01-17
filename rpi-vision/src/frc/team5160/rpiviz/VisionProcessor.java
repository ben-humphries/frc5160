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
	
	VideoCapture camera;
	ArrayList<Mat> debug;
	Mat image = new Mat(), medianBlur = new Mat(), resized = new Mat(), 
			greenOnly = new Mat(), redOnly = new Mat(), blueOnly = new Mat(),
					
			greenChannelThreshold = new Mat(), drawnContours = new Mat(480,360,16); 
	public VisionProcessor(){
		camera = new VideoCapture(0);
		camera.set(Videoio.CAP_PROP_EXPOSURE, 0);
	}
	public Mat process(){
		camera.read(image);
		//image.release();
		
		return process(image);
	}
	public Mat process(Mat picture){
		
		int medianBlurSize = 5, 
				resizeX = 480, 
				resizeY = 360;
		
		Imgproc.medianBlur(picture, medianBlur, medianBlurSize);
		Imgproc.resize(medianBlur, resized, new Size(resizeX,resizeY));
		Core.extractChannel(resized, redOnly, 0);
		Core.extractChannel(resized, greenOnly, 1);
		Core.extractChannel(resized, blueOnly, 2);
		Mat sum = new Mat();
		Core.addWeighted(greenOnly, 1, blueOnly, -0.1, 1, sum);
		Core.addWeighted(sum, 1, redOnly, -0.9, 1, sum);
		Imgproc.threshold(sum, greenChannelThreshold, 0, 255, Imgproc.THRESH_OTSU);
		Mat contouredGreen = greenChannelThreshold.clone();
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		Imgproc.findContours(contouredGreen, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		contouredGreen.release();
		drawnContours.release();
		drawnContours = Mat.zeros(resized.size(), 16);
		ArrayList<MatOfPoint> hulls = new ArrayList<MatOfPoint>();
		for(int i = 0; i < contours.size();){
			MatOfPoint cont = contours.get(i);
			Rect bound = Imgproc.boundingRect(cont);
			MatOfPoint hull = ImageOps.ConvexHull(cont);
			hulls.add(hull);
			int x = bound.x, y = bound.y, w = bound.width, h = bound.height, cx = x+w/2, cy = y+h/2;
			double ac = Imgproc.contourArea(cont);
			double		ar = w*h, 
					ah = Imgproc.contourArea(hull);
			double aspectRatio = ((double)w)/h;
			double hullExtent = ar/ah; 
			double solidity = ac/ah;
			if(!(w > 20 && h > 10 
					//&& (hullExtent < 2 && hullExtent > 0.7)
					//&& solidity < 0.3
					)){
				contours.remove(i);	
			}
			else{
				Imgproc.rectangle(drawnContours, bound.br(),bound.tl(), new Scalar(0,0,255));
				Imgproc.circle(drawnContours, new Point(cx, cy-Math.min(h/5, w/5)), w/8, new Scalar(0,0,255),5);
				i++;
			}
		}
		Imgproc.drawContours(drawnContours, hulls, -1, new Scalar(255,0,0));
		Imgproc.drawContours(drawnContours,contours,-1,new Scalar(0,255,0),2);
		
		return drawnContours;
	}
}
