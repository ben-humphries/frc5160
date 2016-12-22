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
	
	
	 public void paintComponent(Graphics g){
		 if(this.isVisible() == false){
			 return;
		 }
		 Mat image = new Mat();
	     camera.read(image);
	     Core.multiply(image, new Scalar(0,1,0), image);
	     Imgproc.resize(image, image, new Size(32,32));
	     Imgproc.blur(image, image,new Size(2,2));
	     Imgproc.resize(image, image, new Size(320,320));
	      g.drawImage(toBufferedImage(image),0,0, this);
	      image.release();
	 }
	 public static Image toBufferedImage(Mat m){
	        int type = BufferedImage.TYPE_BYTE_GRAY;
	        if ( m.channels() > 1 ) {
	            type = BufferedImage.TYPE_3BYTE_BGR;
	        }
	        int bufferSize = m.channels()*m.cols()*m.rows();
	        byte [] b = new byte[bufferSize];
	        m.get(0,0,b); // get all the pixels
	        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
	        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	        System.arraycopy(b, 0, targetPixels, 0, b.length);  
	        return image;

	    }
	 
}
