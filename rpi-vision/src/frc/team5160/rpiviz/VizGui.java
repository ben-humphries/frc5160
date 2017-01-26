package frc.team5160.rpiviz;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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



public class VizGui extends JPanel implements KeyListener{
	
	VisionProcessor vision = new VisionProcessor();
	ArrayList<Mat> images = new ArrayList<Mat>();
	int index = 0;
	 public void paintComponent(Graphics g){
		 if(this.isVisible() == false){
			 return;
		 }
		 g.drawImage(ImageOps.toBufferedImage(vision.process()),0,0,400,400,this);
	//	 g.drawImage(ImageOps.toBufferedImage(vision.process(images.get(index))),0,0,400,400,this);
		 g.drawImage(ImageOps.toBufferedImage(vision.sum),0,400,400,400,this);
		 g.drawImage(ImageOps.toBufferedImage(vision.image),400,0,400,400,this);
	 }
	
	 public void prepareInputs(){
			try {
				Files.walk(Paths.get("images")).forEach(filePath -> {
				    if (Files.isRegularFile(filePath)) {
				        images.add(Imgcodecs.imread(filePath.toUri().getPath().toString().substring(1)));
				        System.out.println(filePath.toUri().getPath().toString().substring(1));
				    }
				});
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(index < images.size()-1){
				index++;
			}
			else{
				index = 0;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(index > 0){
				index--;
			}
			else{
				index = images.size()-1;
			}
		}
		//System.out.println(index);
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	 
	 
}
