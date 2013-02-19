package ch.epfl.flamemaker.geometry2d;

public class AffineTransformation implements Transformation{
	double a;
	double b;
	double c;
	double d;
	double e;
	double f;
	public static AffineTransformation IDENTITY= new AffineTransformation(1,0,0,0,1,0); //matric identité I3
	
	
	AffineTransformation(double a, double b, double c, double d, double e, double f){
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
		this.e=e;
		this.f=f;
	}
	
	public static AffineTransformation newTranslation(double dx, double dy){ //translation de x et y... *amnesia*???
		AffineTransformation t= new AffineTransformation(1,0,dx,0,1,dy);
		return t;
	}
	
	public static AffineTransformation newRotation(double theta){ //rotation ?? *amnesia*??
		AffineTransformation r= new AffineTransformation(Math.cos(theta),-Math.sin(theta),0,Math.sin(theta),Math.cos(theta),0);
		return r;
	}

	public static AffineTransformation newScaling(double sx, double sy){ // amnesia
		AffineTransformation s= new AffineTransformation(sx,0,0,0,sy,0);
		return s;

	}

	public static AffineTransformation newShearX(double sx){ //amnesia
		AffineTransformation shx= new AffineTransformation(1,sx,0,0,1,0);
		return shx;
	}

	public static AffineTransformation newShearY(double sy){ //amnesia
		AffineTransformation shy= new AffineTransformation(1,0,0,sy,1,0);
		return shy;
	}
	
	public Point transformPoint(Point p){
		
	}
	
	public double translationX(){
		
	}
	
	public double translationY(){
		
	}
	
	public AffineTransformation composeWith(AffineTransformation that){
		
	}
	
	
}
