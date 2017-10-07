package org.usfirst.frc.team5160.utils.path;

public class Waypoint {
	double x, y, i, j, speed, distance, time; 
	public Waypoint(double x, double y, double i, double j, double speed){
		this.x = x;
		this.y = y;
		this.i = i;
		this.j = j;
		this.speed = speed;
	}
	public Waypoint(double x, double y, double i, double j, double speed, double distance, double time){
		this.x = x;
		this.y = y;
		this.i = i;
		this.j = j;
		this.speed = speed;
		this.distance = distance;
		this.time = time;
	}
	public Waypoint(double x, double y, double speed){
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	public Waypoint(double x, double y){
		this.x = x;
		this.y = y;
	}
}
