package org.usfirst.frc.team5160.robot;

import java.util.List;
import java.util.ArrayList;

public class PID {
	
	private List<EncoderTalon> dependencies;
	private String fKey, pKey, iKey, dKey;
	private double f, p, i, d;

	public PID(String profileName, double f, double p, double i, double d) {
		this.f = f;
		this.p = p;
		this.i = i;
		this.d = d;
		fKey = profileName + "-F";
		pKey = profileName + "-P";
		iKey = profileName + "-I";
		dKey = profileName + "-D";
		dependencies = new ArrayList<EncoderTalon>();
		register();
	}
	
	public void addDependency(EncoderTalon talon) {
		dependencies.add(talon);
	}
	
	public void removeDependency(EncoderTalon talon) {
		dependencies.remove(talon);
	}
	
	//submits the PID values to all associated dependent EncoderTalons
	public void sendToDeps() {
		for (EncoderTalon talon : dependencies) {
			talon.submitPIDValues(this);
		}
	}
	
	public void register() {
		Robot.registeredPIDs.add(this);
		Robot.configHash.put(fKey, f);
		Robot.configHash.put(pKey, p);
		Robot.configHash.put(iKey, i);
		Robot.configHash.put(dKey, d);
	}
	
	public void fetchFromConfig() {
		double
			newF = Robot.configHash.get(fKey),
			newP = Robot.configHash.get(pKey),
			newI = Robot.configHash.get(iKey),
			newD = Robot.configHash.get(dKey);
		if (newP != p || newI != i || newD != d) {
			f = newF;
			p = newP;
			i = newI;
			d = newD;
			sendToDeps();
		}
	}

	public double getF() { return f; }
	public double getP() { return p; }
	public double getI() { return i; }
	public double getD() { return d; }
}
