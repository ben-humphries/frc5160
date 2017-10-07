package org.usfirst.frc.team5160.utils;

import com.ctre.CANTalon.TalonControlMode;

public interface FeedbackController {
	TalonControlMode getControlMode();
	double getFeedback(double position, double velocity);
}
