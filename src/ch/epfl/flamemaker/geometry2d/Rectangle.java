package ch.epfl.flamemaker.geometry2d;

public class Rectangle {
	private Point center;
	private double height;
	private double width;

	Rectangle(Point center, double height, double width) {
		this.center = center;
		this.height = height;
		this.width = width;
		// *amnesia* lever l'exception
	}
	
	public double left() {
		return center.x() - (width/2);
	}
	
	public double right() {
		return center.x() + (width/2);
	}
	
	public double bottom() {
		return center.y() - (width/2);
	}
	
	public double top() {
		return center.y() - (width/2);
	}
	
	public double width() {
		return width;
	}

	public double height() {
		return height;
	}
	
	public Point center() {
		return center;
	}
	
	public boolean contains(Point p) {
		
	}
	
	public double double aspectRatio() {
		 return width/height;
	 }
	
	public String toString() {
		return "(" + center.toString() + "," + width + "," + height + ")";
	}
	 
	 
}
