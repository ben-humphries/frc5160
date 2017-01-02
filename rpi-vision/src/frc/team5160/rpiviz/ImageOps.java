package frc.team5160.rpiviz;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class ImageOps {
	
	public static BufferedImage toBufferedImage(Mat m){
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
	
	public static Mat sameSizeMat(Mat input){
		return new Mat(input.size(), input.type());
	}
	
	public static MatOfPoint ConvexHull(MatOfPoint cont){
		MatOfInt hullInt = new MatOfInt();
		ArrayList<Point> points = new ArrayList<Point>();
		MatOfPoint hull = new MatOfPoint();
		Imgproc.convexHull( cont, hullInt, false);
		for(int j=0; j < hullInt.toList().size(); j++)
            points.add(cont.toList().get(hullInt.toList().get(j)));
        hull.fromList(points);
        hullInt.release();
        return hull; 
	}
	
}
