package frc.team5160.rpiviz;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.opencv.core.Core;

/**
 * 
 * This is set up to run on PCs, but will be ported over to a raspberry pi 2/3 
 *
 */
public class RPIViz {
	public static void main(String[] args){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.loadLibrary("opencv_ffmpeg310_64");
		VizGui gui =  new VizGui();
		JFrame frame = new JFrame("RPIViz");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(gui,BorderLayout.CENTER);
		frame.setSize(800, 600);
		frame.setVisible(true);
		while(frame.isValid()){
			//gui.repaint();
		}
	}
}
