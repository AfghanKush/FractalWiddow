package ch.epfl.flamemaker.color;

public class Color 
{
	private double r;
	private double g;
	private double b;
	
	public static Color BLACK=new Color(0,0,0);
	public static Color WHITE=new Color(1,1,1);
	public static Color RED=new Color(1,0,0);
	public static Color GREEN=new Color(0,1,0);
	public static Color BLUE=new Color(0,0,1);
	
		public Color(double r, double g, double b)
		{
			if((r>1)||(g>1)||(b>1)||(r<0)||(g<0)||(b<0))
				throw new IllegalArgumentException("Coodonnées à tester invaldes");
			else
			{
				this.r=r;
				this.g=g;
				this.b=b;
			}
		}
		
		public double red()
		{
			return this.r;
		}
		public double green()
		{
			return this.g;
		}
		public double blue()
		{
			return this.b;
		}

		public Color mixWith(Color that, double proportion)
		{
			if(proportion<0||proportion>1)
				throw new IllegalArgumentException("la proportion de la couleur est invalide");
			else
				return new Color(r+that.red()*proportion,b+that.green()*proportion,b+that.blue()*proportion);
		}
		
		public int asPackedRGB()
		{
			int r1=(int)(r*255);
			int g1=(int)(g*255);
			int b1=(int)(b*255);
			
			r1= r1<<16;
			g1=g1<<8;
			return r1&g1&b1;
			
		}
		
		int sRGBEncode(double v, int max)
		{
			if(v<=0.0031308)
			{
				v*=12.92;
			} 
			else
			{
				v=1.055*Math.pow(v, 1/2.4)-0.055;
			}
			
			return (int)(v*max);
				
		}
}
