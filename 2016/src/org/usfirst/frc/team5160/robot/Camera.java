package org.usfirst.frc.team5160.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class Camera {
	
    private Image frame;
    private Joystick joystick;
    String[] cameraNames = new String[] {"cam1", "cam0", "cam2"};
    int[] sessions = new int[3];
    int curSesh;
    int[] buttons = {3, 2, 5};
    boolean[] prevButtonState = {false, false, false};
    
    //call during robot initialization
    public void init(Joystick joystick) {
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        for (int i = 0; i < cameraNames.length; i++) {
        	try {
        		sessions[i] = NIVision.IMAQdxOpenCamera(cameraNames[i], NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        	catch (Exception e) {
        		sessions[i] = -1;
        	}
        }
        this.joystick = joystick;
    }
    
    public void start() { start(sessions[1]); }
	
	//call before use
	public void start(int sesh) {
		try {
			NIVision.IMAQdxConfigureGrab(sesh);
			NIVision.IMAQdxStartAcquisition(sesh);
			curSesh = sesh;
		} catch (Exception e) {
			curSesh = -1;
		}
	}
	
	//call after use
	public void stop() {
		if (curSesh != -1)
			stop(curSesh);
	}
	
	public void stop(int sesh) {
		NIVision.IMAQdxStopAcquisition(sesh);
	}
	
	//call frequently during use
	public void operatorControlTick() {
		if (curSesh != -1) {
			NIVision.IMAQdxGrab(curSesh, frame, 1);
			if (curSesh == sessions[0]) {
		        drawLine(frame,
		        		Robot.configHash.get("P1_x"), Robot.configHash.get("P1_y"),
		        		Robot.configHash.get("P2_x"), Robot.configHash.get("P2_y"));
		        drawLine(frame,
		        		Robot.configHash.get("P2_x"), Robot.configHash.get("P2_y"),
		        		Robot.configHash.get("P3_x"), Robot.configHash.get("P3_y"));
		        drawLine(frame,
				        Robot.configHash.get("P3_x"), Robot.configHash.get("P3_y"),
				        Robot.configHash.get("P4_x"), Robot.configHash.get("P4_y"));
			}
	        
	        CameraServer.getInstance().setImage(frame);
		}
        
        for (int i = 0; i < buttons.length; i++) {
        	boolean curState = joystick.getRawButton(buttons[i]);
        	if (prevButtonState[i] && !curState) {
        		stop();
        		start(sessions[i]);
        	}
        	prevButtonState[i] = curState;
        }
	}
    
    private void drawLine(NIVision.Image frame, double x1, double y1, double x2, double y2) {
    	NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE,
        		new NIVision.Point((int)x1, (int)y1),
        		new NIVision.Point((int)x2, (int)y2),
        		(float)(double)Robot.configHash.get("lineColor"));
    }
}
