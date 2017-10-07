package org.usfirst.frc.team5160.utils.path;

public interface Path {
	public Waypoint getPointAtTime(double time);
	public Waypoint getPointAtDistance(double time);
}
