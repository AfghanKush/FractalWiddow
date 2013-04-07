package ch.epfl.flamemaker.geometry2d;



public class Point {
	
private double x;
private double y;
public static Point ORIGIN= new Point(0,0); //point static, fait partie de la classe et non de l'objet ;)

public Point(double x, double y){
	this.x=x;
	this.y=y;
}

public double x(){
	return x;
}

public double y(){
	return y;
}

public double r(){
	return Math.sqrt(x*x+y*y);
}

public double theta(){
	return Math.atan2(x, y);
}

public String toString(){
	return "("+x+", "+y+")";
}


}

