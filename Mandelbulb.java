//***************************************************************************************************************
// Author: Non-Euclidean Dreamer
// OMain Class for creating 2d-crosssections of 4d-Mandelbulbs
//****************************************************************************************************************

import java.text.DecimalFormat;

public class Mandelbulb {
	static DecimalFormat df=new DecimalFormat("0000");
	static double t=0;
	public static int[] impuls= {-1,0},zero= {0,0}; 
	public static double 
			x0=0,y0=0,z0,w0, bound=2,start=5880,zoom=0.34877533593255056;   
	public static double[] ax=new double[3],ay=new double[3]; 
	static double k=-2;
	public static Quaternion location=new Quaternion(.01,.1*k,.1*k,.1*k),
			l=new Quaternion(Math.cos(0.003),0,Math.sin(0.003),0),r=new Quaternion(Math.cos(0.003),0,-Math.sin(0.003),0);  
	public static int p=0,colorscale=5;
	static String name0="M"
			,type="png"; 
	
	public static void main(String[] args)     
	{	     
		 Observer eye=new Observer(location,new Quaternion(1,0,0,0),new Quaternion(0,1,0,0),zoom);
		int depth=720;  
   
		double sin=Math.sin(0.5* Math.PI/depth), cos=Math.cos(0.5*Math.PI/depth);
	//	for(t=0;t<start;t++) {eye.turnLeft(l);eye.turnRight(r);}
	for(t=0;t<depth/2;t+=1) 
    {  
	//	l.vary(0.001);
	//	r.vary(0.001); 
	//	eye.location=new Quaternion(0,0,Math.sin(t*2*Math.PI/depth),(1-Math.cos(t*2*Math.PI/depth)));
	//	eye.location.z=Math.pow(Math.sin((t+start)*Math.PI/depth*12), 3)/eye.zoom/3;
		eye.draw(name0+df.format(t+start));
	//	{eye.zoom*=0.999;factor=4.0/zoom/Pilot.height;}
	//if(t%120==0) {		eye.it=(int) Math.min(zoom*Observer.height/colorscale,100000);		eye.colorList=Pilot.colorList(eye.it); 	}
	//	eye.c=eye.c.add(new Quaternion(0.005,-0.000,-0.0005,0));
	//	eye.turnLeft(l);
	// 	eye.turnRight(r);
	eye.turnLeft(new Quaternion(cos,0,0,sin));    	eye.turnRight(new Quaternion(cos,0,0,-sin));
   	eye.print();
    	
    }
	start+=depth/2;
	for(t=0;t<depth/2;t+=1) 
    {  
		eye.draw(name0+df.format(t+start));
    	eye.turnLeft(new Quaternion(cos,sin,0,0));
    	eye.turnRight(new Quaternion(cos,-sin,0,0));
    	eye.print();
    }
	start+=depth/2;
	for(t=0;t<depth/2;t+=1) 
    {  
		eye.draw(name0+df.format(t+start));
    	eye.turnLeft(new Quaternion(cos,0,sin,0));
    	eye.turnRight(new Quaternion(cos,0,-sin,0));
    	eye.print();
    }
		}
	

}
