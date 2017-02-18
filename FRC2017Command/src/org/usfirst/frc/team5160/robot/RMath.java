package org.usfirst.frc.team5160.robot;

public class RMath {
	public static int sign(double d){
		if(d == 0){
			return 0;
		}
		else if (d > 0){
			return 1;
		}
		return -1;
	}
}
