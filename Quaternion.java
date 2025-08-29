//***************************************************************************************************************
// Author: Non-Euclidean Dreamer
// Gathering handy methods to deal with Quaternions
//****************************************************************************************************************

import java.text.DecimalFormat;
import java.util.Random;


public class Quaternion {
	public double x,y,z,w,r,phi,psi,theta;//Attributes: real part, imaginary part, radius, argument
	
	//Constants: often used numbers
	public static Quaternion one=new Quaternion(1,0,0,0), i=new Quaternion(0,1,0,0),j=new Quaternion(0,0,1,0),k=new Quaternion(0,0,0,1),
			zero=new Quaternion(0,0,0,0), half=new Quaternion(0.5,0,0,0),
			mi= new Quaternion(0,-1,0,0),mone=new Quaternion(-1,0,0,0);
	
	public Quaternion(double x0,double y0,double z0,double w0)
	{
		x=x0;
		y=y0;
		z=z0;
		w=w0;
		r=Math.sqrt(x0*x0+y0*y0+w0*w0+z0*z0);
		phi=Math.atan2(y,x);
		if(phi>Math.PI)phi-=2*Math.PI;//I prefer discontinuity to be left...
		
		theta=Math.asin(w/r);
		psi=Math.asin(z/r/Math.cos(theta));
	}
	public Quaternion()
	{}
	
	public Quaternion(double[] l) {
		x=l[0];
		y=l[1];
		z=l[2];
		w=l[3];
		r=Math.sqrt(x*x+y*y+w*w+z*z);
		phi=Math.atan2(y,x);
		if(phi>Math.PI)phi-=2*Math.PI;//I prefer discontinuity to be left...
		
		theta=Math.asin(w/r);
		psi=Math.asin(z/r/Math.cos(theta));
	}

	public static Quaternion polar(double r0,double phi0,double psi0,double theta0)
	{
		Quaternion out=new Quaternion();
		out.x=r0*Math.cos(phi0)*Math.cos(psi0)*Math.cos(theta0);
		out.y= r0*Math.sin(phi0)*Math.cos(psi0)*Math.cos(theta0);
		out.z= r0*Math.sin(psi0)*Math.cos(theta0);
		out.w=r0*Math.sin(theta0);
		out.r=r0;
		out.phi=phi0;
		out.psi=psi0;
		out.theta=theta0;
		return out;
	}

	public Quaternion Re()
	{
		return new Quaternion(x,0,0,0);
	}
	
	public Quaternion Im()
	{
		return new Quaternion(0,y,z,w);
	}
	public Quaternion add(Quaternion z)
	{
		return new Quaternion(x+z.x,y+z.y,this.z+z.z,w+z.w);
	}

	public Quaternion times(Quaternion q)
	{
		return new Quaternion(x*q.x-y*q.y-z*q.z-w*q.w,x*q.y+y*q.x+z*q.w-w*q.z,x*q.z-y*q.w+z*q.x+w*q.y,x*q.w+y*q.z-z*q.y+w*q.x);
	}

	public Quaternion copy() {
		
		return new Quaternion(x,y,z,w);
	}

	public Quaternion times(double j) 
	{
		return new Quaternion(x*j,y*j,z*j,w*j);
	}
	
	public Quaternion power(double n)
	{
		return polar(Math.pow(r,n),n*phi,n*psi,n*theta);
	}

	
	public Quaternion conjugate()
	{
		return new Quaternion(x,-y,-z,-w);
	}
	
	
	public String toString()
	{
		return x+"+"+y+"i"+z+"j"+w+"k";
	}
	
	public Quaternion subtract(Quaternion z)
	{
		return new Quaternion(x-z.x,y-z.y,this.z-z.z,w-z.w);
	}

	
	public double norm()
	{
		return Math.sqrt(x*x+y*y+z*z+w*w);
	}

	public void print(DecimalFormat df)
	{
		System.out.print(df.format(x));
		System.out.print("+");
		System.out.print(df.format(y));
		System.out.print("i+");
		System.out.print(df.format(z));
		System.out.print("j+");
		System.out.print(df.format(w));
		System.out.print("k");
	}



//Gives unit quaternion for rotation
	public void vary(double d) {
		Random rand=new Random();
		y+=d*(0.5-rand.nextDouble());
		z+=d*(0.5-rand.nextDouble());
		w+=d*(0.5-rand.nextDouble());
		normalize();
	}


private void normalize() {
	double norm=norm();
	if(norm>0)times(1/norm);
}

public Quaternion function(Quaternion par, double t)
{
	//t=Mandelbulb.t/96.0;
	double y1=y+Math.abs(z)*(Math.abs(z)-1);
	return new Quaternion(//z,Math.abs(x),Math.log(Math.abs((Math.sin(z))%Math.min(x*Math.E, Math.min(x,(y%Math.min(x, z)))-(y*2)* w)))*Math.min(x, Math.max(Math.sinh(z), Math.abs(z))),	x);
							(y/(z)),Math.signum(y1)*Math.pow(Math.abs(y1),z),z*(Math.atan(w)),((Math.max(w, z))%w-x));
}
	
	
}
