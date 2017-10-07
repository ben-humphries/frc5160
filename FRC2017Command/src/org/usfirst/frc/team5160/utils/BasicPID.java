package org.usfirst.frc.team5160.utils;

import com.ctre.CANTalon;

/**
 * The Basic PID gives us finer control over the built in PID implementation, but may be slightly slower. 
 * @author thomas
 */
public class BasicPID {
	
	protected double P = 0;

	protected double I = 0;

	protected double D = 0;

	protected double F = 0;
	
	protected boolean firstRun = true;
	
	protected double integral = 0;
	
	protected double lastPosition = 0; 
	
	/**
	 * @param p Proportional error weight
	 * @param i Integral error weight
	 * @param d Derivitave error weight
	 */
	public BasicPID(double p, double i, double d){
		setP(p);
		setI(i);
		setD(d);
	}
	
	/**
	 * @param p Proportional error weight
	 * @param i Integral error weight
	 * @param d Derivitave error weight
	 * @param f Baseline power to be supplied
	 */
	public BasicPID(double p, double i, double d, double f){
		setP(p); 
		setI(i);
		setD(d);
		setF(f);
	}
	/**
	 * @param talon The CANTalon to copy PIDF values from
	 */
	public BasicPID(CANTalon talon){
		setP(talon.getP()); 
		setI(talon.getI()); 
		setD(talon.getD());
		setF(talon.getF());
	}

	public double runPID(double position, double target){
		double pVal = P * (target - position); //Calculate proportional part, just P * error or the different between current and desired positions. 
		double iVal = I * (integral); //Calculate integral part, this is done before adding in current error
		double dVal = - D * (position - lastPosition); //Calculate derivative part, is negative because this slows down rapid changes
		double fVal = F; // It is just a constant
		
		//Set the derivative to zero on first run, because there is nothing to base it on
		if(firstRun){
			dVal = 0;
			firstRun = false;
		}
		
		double val = pVal + iVal + dVal + fVal;
		
		lastPosition = position;
		integral = integral + (target - position);
		
		return val;
	}
	
	public double runPID(double position, double target, double velocity){
		firstRun = false;

		double pVal = P * (target - position); //Calculate proportional part, just P * error or the different between current and desired positions. 
		double iVal = I * (integral); //Calculate integral part, this is done before adding in current error
		double dVal = - D * (velocity); //Calculate derivative part, uses the velocity, because thats the derivative, is negative because this slows down rapid changes
		double fVal = F; // It is just a constant
		
				
		double val = pVal + iVal + dVal + fVal;
		
		lastPosition = position;
		integral = integral + (target - position);
		
		return val;
	}
	
	public void reset(){
		firstRun = true;
		integral = 0;
		lastPosition = 0;
	}
	
	public double getP() {
		return P;
	}

	public void setP(double p) {
		P = p;
	}

	public double getI() {
		return I;
	}

	public void setI(double i) {
		I = i;
	}

	public double getD() {
		return D;
	}

	public void setD(double d) {
		D = d;
	}

	public double getF() {
		return F;
	}

	public void setF(double f) {
		F = f;
	}
}
