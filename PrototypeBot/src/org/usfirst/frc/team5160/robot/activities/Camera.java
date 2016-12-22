package org.usfirst.frc.team5160.robot.activities;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;

public class Camera extends Activity{
	private CameraServer camera = CameraServer.getInstance();
	private Image frame;
	private final String[] cameraNames = new String[] {"cam1", "cam0", "cam2"};
	private int[] sessions = new int[cameraNames.length];
    private int currentSession = 0;
	
    @Override
	public void init() {
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		camera.setQuality(25);
        for (int i = 0; i < cameraNames.length; i++) {
        	try {
        		sessions[i] = NIVision.IMAQdxOpenCamera(cameraNames[i], NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        	}catch (Exception e) {
        		sessions[i] = -1;
        	}
        }
        SwitchCamera(0);
	}
	public void SwitchCamera(int session) {
		if(currentSession!=-1){
			StopCamera(currentSession);
		}
		try {
			NIVision.IMAQdxConfigureGrab(session);
			NIVision.IMAQdxStartAcquisition(session);
			currentSession = session;
		} catch (Exception e) {
			currentSession = -1;
		}
	}
	public void StopCamera(int session){
		NIVision.IMAQdxStopAcquisition(session);
	}

	private void loop(double deltaTime) {
		if (currentSession != -1) {
			NIVision.IMAQdxGrab(currentSession, frame, 1);
	        camera.setImage(frame);
		}
		
	}

	@Override
	public void stop() {
		StopCamera(currentSession);
	}
	
	@Override
	public void loopTele(double deltaTime) {
		loop(deltaTime);
	}
	@Override
	public void loopAuto(double deltaTime) {
		loop(deltaTime);
	}

}
