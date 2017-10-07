package org.usfirst.frc.team5160.utils.path;

public interface PathSegment {
	public double calculateX(double percent);
	public double calculateY(double percent);
	public double calculateDxDt(double percent);
	public double calculateDyDt(double percent);
	public double calculateChange(double percent);
	public double getLength();
}
