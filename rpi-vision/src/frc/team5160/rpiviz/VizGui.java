package frc.team5160.rpiviz;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;


public class VizGui extends JPanel{
	
	VisionProcessor vision = new VisionProcessor();
	 public void paintComponent(Graphics g){
		 if(this.isVisible() == false){
			 return;
		 }
		 g.drawImage(ImageOps.toBufferedImage(vision.process()),0,0,400,400,this);
	 }
	
	 
	 
	 
}
