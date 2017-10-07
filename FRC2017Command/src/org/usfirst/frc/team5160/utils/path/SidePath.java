package org.usfirst.frc.team5160.utils.path;

import java.util.Map;
import java.util.TreeMap;

public class SidePath implements Path{

	private Map<Double, Object> timeMap;
	private Map<Double, Object> distanceMap;
	
	public SidePath(){
		timeMap = new TreeMap<Double, Object>();
		distanceMap = new TreeMap<Double, Object>();
	}
	
	public void addDistanceWaypoint(double distance, Waypoint point){
		distanceMap.put(distance, point);
	}
	
	public void addTimeWaypoint(double time, Waypoint point){
		timeMap.put(time, point);
	}
	
	@Override
	public Waypoint getPointAtTime(double t){
		return (Waypoint) SplineUtils.findNearest(timeMap, t);
	}
	
	@Override
	public Waypoint getPointAtDistance(double distance) {
		return (Waypoint) SplineUtils.findNearest(distanceMap, distance);
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
}
