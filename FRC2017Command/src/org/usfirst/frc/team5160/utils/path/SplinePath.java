package org.usfirst.frc.team5160.utils.path;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import usfirst.frc.team2168.robot.FalconLinePlot;

import java.util.TreeMap;

public class SplinePath implements Path{
	
	private Waypoint[] waypoints;
	private Spline[] splines; 
	private double totalDistance; 
	private double VMAX = 1; 
	private double AMAX = 1;
	Map<Double, Object> timeMap = new TreeMap<Double, Object>();
	Map<Double, Object> distanceMap = new TreeMap<Double, Object>();
	
	public SplinePath(Waypoint... waypoints){
		this.waypoints = waypoints;
		splines = new Spline[waypoints.length - 1];
		Waypoint lw = waypoints[0]; //Last waypoint
		for (int i = 1; i < waypoints.length; i++){
			Waypoint w = waypoints[i];
			splines[i-1] = new Spline(lw.x, lw.y, lw.i, lw.j, lw.speed, w.x, w.y, w.i, w.j, w.speed);
			totalDistance += splines[i-1].getLength();
			lw = w;
		}
	}
	@Override
	public Waypoint getPointAtTime(double t){
		return (Waypoint) SplineUtils.findNearest(timeMap, t);
	}
	
	@Override
	public Waypoint getPointAtDistance(double distance) {
		return (Waypoint) SplineUtils.findNearest(distanceMap, distance);
	}
	
	public SplinePath compile(){
		double time = 0;
		double distance = 0;
		timeMap.put(time, waypoints[0]);
		distanceMap.put(distance, waypoints[0]);
		for (Spline spline : splines){
			double splineDistance = 0;
			double splineTime = 0;
			for(double t = 0; t <= 1-Spline.PRECISION; t = t + Spline.PRECISION){
				double x, y, i, j, dd, dt, s;
				
				dd = Spline.PRECISION * spline.calculateChange(t);
				
				splineDistance = splineDistance + dd;
				
				distance = distance + dd;
				
				dt = SplineUtils.getTimeFromDistance(splineDistance, spline.length, spline.s0, spline.s1, VMAX, AMAX) - splineTime;
				splineTime = splineTime + dt;
				time = time + dt;
				x = spline.calculateX(t);
				y = spline.calculateY(t);
				i = spline.calculateDxDt(t);
				j = spline.calculateDyDt(t);
				s = SplineUtils.getSpeedTimeDistance(splineTime, spline.length, spline.s0, spline.s1, VMAX, AMAX);
				Waypoint tmp = new Waypoint(x, y, i, j, s, distance, time);
				//System.out.println();
				timeMap.put(time, tmp);
				distanceMap.put(distance, tmp);
				
			}
			
		}
		return this;
	}
	public double[][] getCoordinatesFromTimes(double... times){
		double[][] tmp = new double[times.length][2];
		for (int i = 0; i < tmp.length; i++){
			Waypoint way = (Waypoint) SplineUtils.findNearest(timeMap, times[i]);
			tmp[i][0] = way.x;
			tmp[i][1] = way.y;
		}
		return tmp;
	}
	public double[][] getTimeSpeeds(double[] times) {
		double[][] tmp = new double[times.length][2];
		for (int i = 0; i < tmp.length; i++){
			Waypoint way = (Waypoint) SplineUtils.findNearest(timeMap, times[i]);
			tmp[i][0] = times[i];
			tmp[i][1] = way.speed;
		}
		return tmp;
	}
	
	public SidePath[] createLeftRightPaths(double width){
		SidePath[] paths = {new SidePath(), new SidePath()};
		Waypoint lastLeft = null;
		Waypoint lastRight = null;
		for (double time : timeMap.keySet()){
			Waypoint tmp = (Waypoint) timeMap.get(time);
			double headingMagnitude = Math.sqrt( Math.pow(tmp.i, 2) + Math.pow(tmp.j, 2));
			double cosAngle = tmp.i/headingMagnitude;
			double sinAngle = tmp.j/headingMagnitude;
			
			double xL, xR, yL, yR, sL = 0, sR = 0, dL = 0, dR = 0;
			xL = tmp.x - width * sinAngle / 2;
			yL = tmp.y + width * cosAngle / 2;
			xR = tmp.x + width * sinAngle / 2;
			yR = tmp.y - width * cosAngle / 2;
			
			if(lastLeft != null && lastRight != null){
				double dt = time - lastLeft.time;
				double lastAngle = Math.atan2(lastLeft.j, lastLeft.i);
				double angle = Math.atan2(tmp.j, tmp.i);
				double angularVelocity = (angle-lastAngle)/dt;
				
				//double ddL = Math.sqrt(Math.pow(xL - lastLeft.x, 2) + Math.pow(yL - lastLeft.y, 2));
				//dL = lastLeft.distance + ddL;
				
				//sL = ddL/dt;
			
				//double ddR = Math.sqrt(Math.pow(xR - lastRight.x, 2) + Math.pow(yR - lastRight.y, 2));
				//dR = lastRight.distance + ddR;
				
				//sR = ddR/dt;
				
				
				sL = tmp.speed-angularVelocity;
				sR = tmp.speed+angularVelocity;
				dL = lastRight.distance + Math.abs(sL)*dt;
				dR = lastRight.distance + Math.abs(sR)*dt;
				//System.out.println(time+" , "+angularVelocity+ " , "+ (sR-sL)/width);
			}
			
			
			lastLeft = new Waypoint(xL, yL, tmp.i, tmp.j, sL, dL, time);
			lastRight = new Waypoint(xR, yR, tmp.i, tmp.j, sR, dR, time);
			
			paths[0].addDistanceWaypoint(dL, lastLeft);
			paths[1].addDistanceWaypoint(dR, lastRight);
			paths[0].addTimeWaypoint(time, lastLeft);
			paths[1].addTimeWaypoint(time, lastRight);
		}
		return paths;
	}
	
	public static void main(String[] args){
		long start = System.currentTimeMillis();
		Waypoint[] waypoints = {
				new Waypoint(4, 6, 4 , -4, 0),
				new Waypoint(7, 6, 0 , -5, 0),
				//new Waypoint(9, 9, 10 , 10, 0),
				//new Waypoint(30, 25, 10 , 0, 0),
			
		};
		SplinePath se = new SplinePath(waypoints).compile();
		se = new SplinePath(waypoints).compile();
		SidePath[] paths = se.createLeftRightPaths(2.5);
		System.out.println("Time in ms: " + (System.currentTimeMillis()-start));
		double[] times = new double[200];
		for (int i = 0; i < times.length; i++){
			times[i] = i/20.0;
		}
		double [][] points = se.getCoordinatesFromTimes(times);
		FalconLinePlot fig2 = new FalconLinePlot(points,null,Color.blue);
		fig2.addData(paths[0].getCoordinatesFromTimes(times), Color.green);
		fig2.addData(paths[1].getCoordinatesFromTimes(times), Color.red);
		fig2.yGridOn();
		fig2.xGridOn();
		fig2.setYLabel("Y");
		fig2.setXLabel("X");
		fig2.setTitle("Time movement profile");
		points = se.getTimeSpeeds(times);
		FalconLinePlot fig3 = new FalconLinePlot(points,null,Color.blue);
		fig3.addData(paths[0].getTimeSpeeds(times), Color.green);
		fig3.addData(paths[1].getTimeSpeeds(times), Color.red);
		fig3.yGridOn();
		fig3.xGridOn();
		fig3.setYLabel("Y");
		fig3.setXLabel("X");
		fig3.setTitle("Time movement profile");
		
	}


	
	

}
