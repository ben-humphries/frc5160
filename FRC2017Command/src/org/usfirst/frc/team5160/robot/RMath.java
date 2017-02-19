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
	public static double clamp(double min, double max, double val){
		if(val < min) return min;
		else if(val > max) return max;
		return val;
	}
}
