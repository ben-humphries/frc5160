
package org.usfirst.frc.team5160.robot.commands;

import org.usfirst.frc.team5160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CMDToggleCamera extends Command {
	

    public CMDToggleCamera(int camera) {
    	Robot.currentCamera = camera;
    	System.out.println("Camera is "+camera);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
