//***************************************************************************************************************
// Author: Non-Euclidean Dreamer
// Object with a certain position and orientation within the Quaternions for taking a crossection
//****************************************************************************************************************


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Observer 
{
	public Quaternion location, right,up,c=new Quaternion(-2,0,.4,0);
	static int width=2560,height=1440;
	public double zoom, bound=2;
	static int colorscale=500;
	static String type="png",
			escape="escape",
			ds="DynamicalSystem",
			mode=ds;
	ArrayList<Integer> colorList; 
	int it;
	
	public Observer(Quaternion l, Quaternion r, Quaternion u, double z)
	{
		location=l;
		right=r;
		up=u;
		zoom=z;
		it=(int) Math.min(zoom*height/colorscale,100000);
		colorList=Pilot.colorList(it); 
		if(mode==ds)it=10;
	}
	
	//Here on can change the iteration function
	private int iterate(Quaternion z, Quaternion c, int it) {
		int k=0;
		//Quaternion tline=new Quaternion(t,0,0,0),//tcirc=ComplexNumber.polar(1,t),
				Quaternion i=Quaternion.i;
				Quaternion one=Quaternion.one;
		//System.out.print("ha");
		while((z.norm()<bound/*||Math.abs(z.y%(2*Math.PI))<Math.PI/2||Math.abs(z.y%(2*Math.PI))>3*Math.PI/4*/) && k<it+1)
		{
			//c.times(c.norm()%6/c.norm()).subtract(one.times(2));
		//	z=z.times(z.tanh().times(tline)).add(z.times(z).times(1-t)).add(c);//(tline.add((c.add(z.sin())).sin())).sin();
		//	z=z.times(z).add(c);//.divided(z.add(tcirc));
			z=z.power(2).add(c);
			//z=z.power(2).add(m).divided(z.power(2).add(c));
			//z=m.times(m).times(Math.pow(z.norm(),-Math.pow((z.subtract(one.times(0.5))).norm(),3))).subtract(z.times(1.0/3));
			//z=z.power(2).add(s.times(z)).add(z);
			//z=(z.add(one.times(2)).divided(new ComplexNumber(s.add(one.times(4)).norm(),0).power(s)));
		//	z=z.times(z).add(c);
			//z=z.cosh().add(s);
			//z=z.exp().add(s);
			k++;
		}
		//if(k==n+1)System.out.print(".");
		
		
		return k;
	}
	
	public void shift(Quaternion dir)
	{
		location=location.add(dir);
	}
	
	//Left-Multiplicates the Quaternion
	public void turnLeft(Quaternion factor)
	{
		right=factor.times(right);
		up=factor.times(up);
	}
	//Right-Multiplicates the Quaternion
	public void turnRight(Quaternion factor)
	{
		right=right.times(factor);
		up=up.times(factor);
	}
	
	public void draw( String name)
	{
		BufferedImage canvas=new BufferedImage(width, height,BufferedImage.TYPE_4BYTE_ABGR);
		int[][]iBuffer=new int[width][height];
		double factor=4.0/zoom/Pilot.height;
		{for (int i=0;i<width;i++) 
		{   
			//System.out.print("."+factor); 
			for (int j=0;j<height;j++) 
			{ 

				double r=1.5, t0=factor*(i-width/2.0);
				Quaternion z=location.add(right.times(factor*(i-width/2.0))).add(up.times(factor*(j-890)));//new Quaternion(location[0]+factor*(i-width/2.0),location[1]*Math.cos(t*Math.PI/depth/20)+location[2]*Math.sin(t*Math.PI/depth/20)+factor*(j-height/2.0),location[2]*Math.cos(t*Math.PI/depth/20)+location[1]*Math.sin(t*Math.PI/depth/20),location[3]);//
				z.z+=t0/500;
				if(mode==escape) {
				iBuffer[i][j]=//mandelvariations.iterate(location[0]+factor*(i-Pilot.width/2.0),location[1]+factor*(j-Pilot.height/2.0),it,p);
						iterate(z,z/*1.8501953125000081,0.22636718749999993)1.8291015625000098,-0.0005859375000002293)*/,it);
				
				canvas.setRGB(i, j, colorList.get(iBuffer[i][j]));}
				else {
					for(int t=0;t<it;t++)
					{
						z=z.function(z, 0);
					}
					canvas.setRGB(i, j, colormap(z));
				}
			}
	
		}
		}
	
		File outputfile = new File(name+"."+type);
		try {  
			ImageIO.write(canvas, type, outputfile);
			System.out.println(name+" finished.");
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
		
	}
	public void iterate( String name)
	{
		BufferedImage canvas=new BufferedImage(width, height,BufferedImage.TYPE_4BYTE_ABGR);
		boolean[][]iBuffer=new boolean[width][height];
		Quaternion[][]zBuffer=new Quaternion[width][height];
		for(int i=0;i<width;i++)for(int j=0;j<height;j++)zBuffer[i][j]=new Quaternion(0,0,0,0);
		double factor=4.0/zoom/Pilot.height;
		int steps=10;
		for(int t=0;t<(it+steps-1)/steps;t++)
		{for (int i=0;i<width;i++) 
		{   
			//System.out.print("."+factor); 
			for (int j=0;j<height;j++) 
			{ 
				if(!iBuffer[i][j]) {
				double r=1.5, t0=factor*(i-width/2.0);
				Quaternion z=location.add(right.times(factor*(i-width/2.0))).add(up.times(factor*(j-height/2.0)));//new Quaternion(location[0]+factor*(i-width/2.0),location[1]*Math.cos(t*Math.PI/depth/20)+location[2]*Math.sin(t*Math.PI/depth/20)+factor*(j-height/2.0),location[2]*Math.cos(t*Math.PI/depth/20)+location[1]*Math.sin(t*Math.PI/depth/20),location[3]);//
				//z.z+=t0/100;
				int t1=0;
				while(t1+steps*t<it&&!iBuffer[i][j]) {
					zBuffer[i][j]=zBuffer[i][j].power(2).add(z);
					if(zBuffer[i][j].norm()>bound)iBuffer[i][j]=true;
					else t1++;
				}
				int x=t*steps+t1;
				if (t1==steps)x=it;
				canvas.setRGB(i, j, colorList.get(x));}
				
			}System.out.print(".");
		
		}
	System.out.println();
		File outputfile = new File(name+"."+type);
		try {  
			ImageIO.write(canvas, type, outputfile);
			System.out.println(name+" finished.");
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
		}
	}
	
	//Colormap for Dynamical Systems using 3 angles to get rgb
	public static int colormap(Quaternion z)
	{
		int r=(int)(Math.atan2(z.y, z.x)/Math.PI*128+127.5),g=(int)(Math.atan(z.w/z.z)/Math.PI*256+127.5),b=(int)(Math.atan2(z.z, z.y)/Math.PI*128+127.5);
		return new Color(r,g,b).getRGB();
	}
		
	

	public void print() {
		DecimalFormat df=new DecimalFormat("0.0");
		System.out.print("Observer ");
		location.print(df);
		System.out.print(", ");
		right.print(df);
		System.out.print(", ");
		up.print(df);	
		System.out.print(", ");
		c.print(df);
		System.out.print(", ");
		System.out.println(zoom);
	
	}

	public void move(Quaternion dir) {
		location=location.add(dir);
	}
}
