package org.usfirst.frc.team5160.utils.path;

import java.util.Map;
import java.util.Map.Entry;

public class SplineUtils {
	
	public static Object findNearest(Map<Double, Object> map, double value) {
	    Map.Entry<Double, Object> previousEntry = null;
	    
	    for (Entry<Double, Object> e : map.entrySet()) {
	        if (e.getKey() >= value) {
	            if (previousEntry == null) {
	                return e.getValue();
	            } else {
	                if (e.getKey() - value >= value - previousEntry.getKey()) {
	                    return previousEntry.getValue();
	                } else {
	                    return e.getValue();
	                }
	            }
	        }
	        previousEntry = e;
	    }
	    return previousEntry.getValue();
	}
	
	public static SidePath[] getLeftRightFromPath(Path path){
		
		return null;
	}
	
	public static double getTimeFromDistance(double distance, double targetDistance, double v0, double v1, double vmax, double amax){
		double aTime = (vmax - v0) / amax; //Time it takes to accelerate to vmax
		double dTime = (vmax - v1) / amax; //Time it takes to deccellerate to v1
		double aDist = aTime * (vmax + v0) / 2;
		double dDist = dTime * (vmax + v1) / 2;
		
		double fDist = targetDistance - aDist - dDist;
		double fTime = fDist / vmax; //Time velocity is at vmax
		
		//If vmax is never reached
		if(aDist + dDist > targetDistance){
			vmax = Math.sqrt( (2 * targetDistance * amax + Math.pow(v0, 2) + Math.pow(v1, 2) ) / 2.0 ); // Readjust vmax
			aTime = (vmax - v0) / amax; //Time it takes to accelerate to vmax
			dTime = (vmax - v1) / amax; //Time it takes to deccellerate to v1
			aDist = aTime * (vmax + v0) / 2;
			dDist = dTime * (vmax + v1) / 2;
			
			fDist = 0;
			fTime = 0;
		}
		if (distance < aDist){
			return (Math.sqrt(Math.pow(v0, 2) + 2 * amax*distance) - v0)/amax;
		}
		else if (distance < aDist + fDist){
			return aTime + (distance - aDist)/vmax;
		}
		else if (distance < aDist + fDist + dDist){
			return aTime + fTime + vmax/amax - Math.sqrt(Math.pow(vmax, 2) - 2*amax*(distance - fDist - aDist))/amax;
		}
		else {
			return aTime+fTime+dTime;
		}
		
	}
	
	public static double getSpeedDistanceDistance(double distance, double targetDistance, double v0, double v1, double vmax, double amax){
		double aTime = (vmax - v0) / amax; //Time it takes to accelerate to vmax
		double dTime = (vmax - v1) / amax; //Time it takes to deccellerate to v1
		double aDist = aTime * (vmax + v0) / 2;
		double dDist = dTime * (vmax + v1) / 2;
		
		double fDist = targetDistance - aDist - dDist;
		double fTime = fDist / vmax; //Time velocity is at vmax
		
		//If vmax is never reached
		if(aDist + dDist > targetDistance){
			vmax = Math.sqrt( (2 * targetDistance * amax + Math.pow(v0, 2) + Math.pow(v1, 2) ) / 2.0 ); // Readjust vmax
			aTime = (vmax - v0) / amax; //Time it takes to accelerate to vmax
			dTime = (vmax - v1) / amax; //Time it takes to deccellerate to v1
			aDist = aTime * (vmax + v0) / 2;
			dDist = dTime * (vmax + v1) / 2;
			
			fDist = 0;
			fTime = 0;
		}
		
		if(distance < aDist){
			return Math.sqrt(Math.pow(v0, 2) + 2 * amax*distance);
		}
		else if (distance < aDist + fDist){
			return vmax; 
		}
		else if (distance < aDist + fDist + dDist){
			return Math.sqrt(Math.pow(vmax, 2) - 2 * amax * (distance - fDist - aDist));
		}
		else {
			return v1;
		}
		
	}
	public static double getSpeedTimeDistance(double time, double targetDistance, double v0, double v1, double vmax, double amax){
		double aTime = (vmax - v0) / amax; //Time it takes to accelerate to vmax
		double dTime = (vmax - v1) / amax; //Time it takes to deccellerate to v1
		double aDist = aTime * (vmax + v0) / 2;
		double dDist = dTime * (vmax + v1) / 2;
		
		double fDist = targetDistance - aDist - dDist;
		double fTime = fDist / vmax; //Time velocity is at vmax
		
		//If vmax is never reached
		if(aDist + dDist > targetDistance){
			vmax = Math.sqrt( (2 * targetDistance * amax + Math.pow(v0, 2) + Math.pow(v1, 2) ) / 2.0 ); // Readjust vmax
			aTime = (vmax - v0) / amax; //Time it takes to accelerate to vmax
			dTime = (vmax - v1) / amax; //Time it takes to deccellerate to v1
			aDist = aTime * (vmax + v0) / 2;
			dDist = dTime * (vmax + v1) / 2;
			
			fDist = 0;
			fTime = 0;
		}
		
		if(time < aTime){
			return time * amax + v0; 
		}
		else if (time < aTime + fTime){
			return vmax; 
		}
		else if (time < aTime + fTime + dTime){
			return vmax - amax*(time - fTime - aTime);
		}
		else {
			return v1;
		}
		
	}
}
