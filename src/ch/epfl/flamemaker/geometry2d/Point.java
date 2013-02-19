package ch.epfl.flamemaker.geometry2d;
import   java.lang.Math.*;



public class Point {
private double x;
private double y;
Point(double x, double y){
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

public static Point ORIGIN= new Point(0,0); //on est pas sur *amnesia* !!!!!IMPORTANT

}

