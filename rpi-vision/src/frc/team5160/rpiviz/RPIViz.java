package frc.team5160.rpiviz;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.opencv.core.Core;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * 
 * This is set up to run on PCs, but will be ported over to a raspberry pi 2/3 
 *
 */
public class RPIViz {
	public static void main(String[] args){
		guize();
	}
	public static void guize(){
		VizGui gui =  new VizGui();
		gui.prepareInputs();
		JFrame frame = new JFrame("RPIViz");
		frame.addKeyListener(gui);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(gui,BorderLayout.CENTER);
		frame.setSize(1600, 800);
		frame.setVisible(true);
		
		//MJPGServer m = new MJPGServer();
		while(true){
			gui.repaint();
		}
	}
}
