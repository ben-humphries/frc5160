package org.usfirst.frc.team5160.utils.path;
//1.0611612493280322
//1.0611633082237117
public class Spline implements PathSegment{
	public static final double PRECISION = 0.001; 
	private double[] a = new double[4];
	private double[] b = new double[4];
	public double s0, s1;
	public double length = 0;
	private Spline(){}
	
	public Spline(double x0, double y0, double i0, double j0, double x1, double y1, double i1, double j1){
		// a[3]*t^3 + a[2]*t^2 + a[1]*t + a[0]
		a[0] = x0;
		a[1] = i0;
		a[3] = i1 - 2*x1 + a[1] + 2*a[0];
		a[2] = x1 - a[3] - a[1] - a[0];
		// b[3]*t^3 + b[2]*t^2 + b[1]*t + b[0]
		b[0] = y0;
		b[1] = j0;
		b[3] = j1 - 2*y1 + b[1] + 2*b[0];
		b[2] = y1 - b[3] - b[1] - b[0];
	}
	
	public Spline(double x0, double y0, double i0, double j0, double s0, double x1, double y1, double i1, double j1, double s1){
		// a[3]*t^3 + a[2]*t^2 + a[1]*t + a[0]
		a[0] = x0;
		a[1] = i0;
		a[3] = i1 - 2*x1 + a[1] + 2*a[0];
		a[2] = x1 - a[3] - a[1] - a[0];
		// b[3]*t^3 + b[2]*t^2 + b[1]*t + b[0]
		b[0] = y0;
		b[1] = j0;
		b[3] = j1 - 2*y1 + b[1] + 2*b[0];
		b[2] = y1 - b[3] - b[1] - b[0];
		
		this.s0 = s0;
		this.s1 = s1; 
	}
	
	@Override
	public double calculateX(double percent){
		return a[3] * Math.pow(percent, 3) + a[2] * Math.pow(percent, 2) + a[1] * percent + a[0];
	}
	
	@Override
	public double calculateY(double percent){
		return b[3] * Math.pow(percent, 3) + b[2] * Math.pow(percent, 2) + b[1] * percent + b[0];
	}
	
	@Override
	public double calculateDxDt(double percent){
		return 3*a[3]*Math.pow(percent, 2) + 2 * a[2] * percent + a[1]; 
	}
	
	@Override
	public double calculateDyDt(double percent){
		return 3 * b[3] * Math.pow(percent, 2) + 2 * b[2] * percent + b[1];
	}
	
	@Override
	public double calculateChange(double percent){
		return Math.sqrt( Math.pow(calculateDxDt(percent), 2) + Math.pow(calculateDyDt(percent), 2) );
	}
	
	private void calculateDistance(){
		double integral = 0;
		for(double t = PRECISION; t < 1; t = t + PRECISION){
			integral = integral + PRECISION * calculateChange(t);
		}
		this.length = integral;
	}
	
	@Override
	public double getLength(){
		if(length == 0){
			calculateDistance();
		}
		return this.length;
	}
	
	public static void main(String[] args){
		long start = System.currentTimeMillis();
		Spline s = new Spline(0, 0, 1, 1, 0, 1, 0, 1);
		System.out.println(s.getLength());
		System.out.println("Time in ms: " + (System.currentTimeMillis()-start));
	}

	
}
