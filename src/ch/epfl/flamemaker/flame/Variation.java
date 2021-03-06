package ch.epfl.flamemaker.flame;

import java.util.*;

import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Transformation;

public abstract class Variation implements Transformation 
{
    private final String name;
    private final int index;
    
    /**
     * Constructeur
     * @param index 
     * @param name
     */
    private Variation(int index, String name) 
    { 
    	this.name=name;
    	this.index=index;
    }

    public String name() 
    { 
    	return this.name; 
    }
    
    public int index() 
    { 
    	return this.index; 
    }
    
    public final static List<Variation> ALL_VARIATIONS =
        Arrays.asList(new Variation(0, "Linear") 
        {
            public Point transformPoint(Point p) 
            {
        		return p;
            }
        },new Variation(1,"Sinusoidal")
        {
        	public Point transformPoint(Point p)
        	{
        		double x=Math.sin(p.x());
        		double y=Math.sin(p.y());
        		return new Point(x,y);
        	}
        },new Variation(2,"Spherical")
        {
        	public Point transformPoint(Point p)
        	{
        		double x=p.x()/Math.pow(p.r(), 2);
        		double y=p.y()/Math.pow(p.r(), 2);
        		return new Point(x,y);
        	}
        },new Variation(3,"Swirl")
        {
        	public Point transformPoint(Point p)
        	{
        		double x=p.x()*Math.sin(Math.pow(p.r(), 2))-p.y()*Math.cos(Math.pow(p.r(), 2));
        		double y=p.x()*Math.cos(Math.pow(p.r(), 2))+p.y()*Math.sin(Math.pow(p.r(), 2));
        		return new Point(x,y);
        	}
        },new Variation(4,"Horseshoe")
        {
        	public Point transformPoint(Point p)
        	{
        		double x=(p.x()-p.y())*(p.x()+p.y())/p.r();
        		double y=(p.x()*2*p.y())/p.r();
        		return new Point(x,y);
        	}
        },new Variation(5,"Bubble")
        {
        	public Point transformPoint(Point p)
        	{
        		double x=(4*p.x())/((Math.pow(p.r(), 2))+4);
        		double y=(4*p.y())/((Math.pow(p.r(), 2))+4);
        		return new Point(x,y);
        	}
        }
            ); //ferme la liste des variations.
}