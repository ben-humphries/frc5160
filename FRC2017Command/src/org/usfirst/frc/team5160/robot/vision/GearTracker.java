package org.usfirst.frc.team5160.robot.vision;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import edu.wpi.first.wpilibj.vision.VisionPipeline;

import org.opencv.core.*;
import org.opencv.core.Core.*;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.*;
import org.opencv.objdetect.*;

/**
* GearTracker class.
*
* <p>An OpenCV pipeline generated by GRIP.
*
* @author GRIP
*/
public class GearTracker {

	//Outputs
	private Mat resizeImageOutput = new Mat();
	private Mat cvMedianblur0Output = new Mat();
	private Mat blurOutput = new Mat();
	private Mat cvMedianblur1Output = new Mat();
	private Mat hsvThresholdOutput = new Mat();
	private Mat rgbThresholdOutput = new Mat();
	private Mat cvBitwiseAndOutput = new Mat();
	Mat points = new Mat();
	Mat reduced = new Mat();

	/**
	 * This is the primary method that runs the entire pipeline and updates the outputs.
	 */
	public double process(Mat source0) {
		// Step Resize_Image0:
		Mat resizeImageInput = source0;
		double resizeImageWidth = 240.0;
		double resizeImageHeight = 160.0;
		int resizeImageInterpolation = Imgproc.INTER_CUBIC;
		resizeImage(resizeImageInput, resizeImageWidth, resizeImageHeight, resizeImageInterpolation, resizeImageOutput);

		// Step CV_medianBlur0:
		Mat cvMedianblur0Src = resizeImageOutput;
		double cvMedianblur0Ksize = 3.0;
		cvMedianblur(cvMedianblur0Src, cvMedianblur0Ksize, cvMedianblur0Output);

		// Step Blur0:
		Mat blurInput = cvMedianblur0Output;
		BlurType blurType = BlurType.get("Gaussian Blur");
		double blurRadius = 0.0;
		blur(blurInput, blurType, blurRadius, blurOutput);

		// Step CV_medianBlur1:
		Mat cvMedianblur1Src = blurOutput;
		double cvMedianblur1Ksize = 3.0;
		cvMedianblur(cvMedianblur1Src, cvMedianblur1Ksize, cvMedianblur1Output);

		// Step HSV_Threshold0:
		Mat hsvThresholdInput = cvMedianblur1Output;
		double[] hsvThresholdHue = {0.0, 78.63481228668942};
		double[] hsvThresholdSaturation = {107.77877697841726, 255.0};
		double[] hsvThresholdValue = {0.0, 255.0};
		hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, hsvThresholdOutput);

		// Step RGB_Threshold0:
		Mat rgbThresholdInput = cvMedianblur1Output;
		double[] rgbThresholdRed = {112.36510791366906, 255.0};
		double[] rgbThresholdGreen = {110.07194244604317, 255.0};
		double[] rgbThresholdBlue = {0.0, 98.34470989761094};
		rgbThreshold(rgbThresholdInput, rgbThresholdRed, rgbThresholdGreen, rgbThresholdBlue, rgbThresholdOutput);

		// Step CV_bitwise_and0:
		Mat cvBitwiseAndSrc1 = hsvThresholdOutput;
		Mat cvBitwiseAndSrc2 = rgbThresholdOutput;
		cvBitwiseAnd(cvBitwiseAndSrc1, cvBitwiseAndSrc2, cvBitwiseAndOutput);
		
		
		Core.findNonZero(cvBitwiseAndOutput, points);
		Core.reduce(points,reduced, 0, Core.REDUCE_AVG);
		return (Core.mean(reduced).val[0]-120)*SimpleVisionProcessor.pxToDegHorizontal;
	}

	/**
	 * This method is a generated getter for the output of a Resize_Image.
	 * @return Mat output from Resize_Image.
	 */
	public Mat resizeImageOutput() {
		return resizeImageOutput;
	}

	/**
	 * This method is a generated getter for the output of a CV_medianBlur.
	 * @return Mat output from CV_medianBlur.
	 */
	public Mat cvMedianblur0Output() {
		return cvMedianblur0Output;
	}

	/**
	 * This method is a generated getter for the output of a Blur.
	 * @return Mat output from Blur.
	 */
	public Mat blurOutput() {
		return blurOutput;
	}

	/**
	 * This method is a generated getter for the output of a CV_medianBlur.
	 * @return Mat output from CV_medianBlur.
	 */
	public Mat cvMedianblur1Output() {
		return cvMedianblur1Output;
	}

	/**
	 * This method is a generated getter for the output of a HSV_Threshold.
	 * @return Mat output from HSV_Threshold.
	 */
	public Mat hsvThresholdOutput() {
		return hsvThresholdOutput;
	}

	/**
	 * This method is a generated getter for the output of a RGB_Threshold.
	 * @return Mat output from RGB_Threshold.
	 */
	public Mat rgbThresholdOutput() {
		return rgbThresholdOutput;
	}

	/**
	 * This method is a generated getter for the output of a CV_bitwise_and.
	 * @return Mat output from CV_bitwise_and.
	 */
	public Mat cvBitwiseAndOutput() {
		return cvBitwiseAndOutput;
	}


	/**
	 * Scales and image to an exact size.
	 * @param input The image on which to perform the Resize.
	 * @param width The width of the output in pixels.
	 * @param height The height of the output in pixels.
	 * @param interpolation The type of interpolation.
	 * @param output The image in which to store the output.
	 */
	private void resizeImage(Mat input, double width, double height,
		int interpolation, Mat output) {
		Imgproc.resize(input, output, new Size(width, height), 0.0, 0.0, interpolation);
	}

	/**
	 * An indication of which type of filter to use for a blur.
	 * Choices are BOX, GAUSSIAN, MEDIAN, and BILATERAL
	 */
	enum BlurType{
		BOX("Box Blur"), GAUSSIAN("Gaussian Blur"), MEDIAN("Median Filter"),
			BILATERAL("Bilateral Filter");

		private final String label;

		BlurType(String label) {
			this.label = label;
		}

		public static BlurType get(String type) {
			if (BILATERAL.label.equals(type)) {
				return BILATERAL;
			}
			else if (GAUSSIAN.label.equals(type)) {
			return GAUSSIAN;
			}
			else if (MEDIAN.label.equals(type)) {
				return MEDIAN;
			}
			else {
				return BOX;
			}
		}

		@Override
		public String toString() {
			return this.label;
		}
	}

	/**
	 * Softens an image using one of several filters.
	 * @param input The image on which to perform the blur.
	 * @param type The blurType to perform.
	 * @param doubleRadius The radius for the blur.
	 * @param output The image in which to store the output.
	 */
	private void blur(Mat input, BlurType type, double doubleRadius,
		Mat output) {
		int radius = (int)(doubleRadius + 0.5);
		int kernelSize;
		switch(type){
			case BOX:
				kernelSize = 2 * radius + 1;
				Imgproc.blur(input, output, new Size(kernelSize, kernelSize));
				break;
			case GAUSSIAN:
				kernelSize = 6 * radius + 1;
				Imgproc.GaussianBlur(input,output, new Size(kernelSize, kernelSize), radius);
				break;
			case MEDIAN:
				kernelSize = 2 * radius + 1;
				Imgproc.medianBlur(input, output, kernelSize);
				break;
			case BILATERAL:
				Imgproc.bilateralFilter(input, output, -1, radius, radius);
				break;
		}
	}

	/**
	 * Performs a median blur on the image.
	 * @param src image to blur.
	 * @param kSize size of blur.
	 * @param dst output of blur.
	 */
	private void cvMedianblur(Mat src, double kSize, Mat dst) {
		Imgproc.medianBlur(src, dst, (int)kSize);
	}

	/**
	 * Segment an image based on hue, saturation, and value ranges.
	 *
	 * @param input The image on which to perform the HSL threshold.
	 * @param hue The min and max hue
	 * @param sat The min and max saturation
	 * @param val The min and max value
	 * @param output The image in which to store the output.
	 */
	private void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val,
	    Mat out) {
		Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
		Core.inRange(out, new Scalar(hue[0], sat[0], val[0]),
			new Scalar(hue[1], sat[1], val[1]), out);
	}

	/**
	 * Segment an image based on color ranges.
	 * @param input The image on which to perform the RGB threshold.
	 * @param red The min and max red.
	 * @param green The min and max green.
	 * @param blue The min and max blue.
	 * @param output The image in which to store the output.
	 */
	private void rgbThreshold(Mat input, double[] red, double[] green, double[] blue,
		Mat out) {
		Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2RGB);
		Core.inRange(out, new Scalar(red[0], green[0], blue[0]),
			new Scalar(red[1], green[1], blue[1]), out);
	}

	/**
	 * Computes the per channel and of two images.
	 * @param src1 The first image to use.
	 * @param src2 The second image to use.
	 * @param dst the result image when the and is performed.
	 */
	private void cvBitwiseAnd(Mat src1, Mat src2, Mat dst) {
		Core.bitwise_and(src1, src2, dst);
	}




}

