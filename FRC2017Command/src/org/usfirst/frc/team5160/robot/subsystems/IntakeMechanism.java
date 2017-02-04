package org.usfirst.frc.team5160.robot.subsystems;

import org.usfirst.frc.team5160.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeMechanism extends Subsystem {
    
    private CANTalon motor1;
    private CANTalon motor2;
    
    public IntakeMechanism(){
    	
    	motor1 = new CANTalon(RobotMap.INTAKE_CIM_1);
    	motor2 = new CANTalon(RobotMap.INTAKE_CIM_2);
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

