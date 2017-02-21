package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeMechanism extends Subsystem {
    
    private Spark motor1;
    private Spark motor2;
    
    public IntakeMechanism(){
    	
    	motor1 = new Spark(RobotMap.INTAKE_BAG_1);
    	motor2 = new Spark(RobotMap.INTAKE_BAG_2);
    }
    
    public void intake(double speed){
    	
    	motor1.set(speed);
    	motor2.set(-1*speed);
    	
    }
    public void stopMotors(){
    	motor1.set(0);
    	motor2.set(0);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

