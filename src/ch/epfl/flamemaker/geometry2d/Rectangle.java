package ch.epfl.flamemaker.geometry2d;

public class Rectangle { //classe du rect
	private Point center; //centre du rectangle 
	private double height; //hateur
	private double width; // largeur

	Rectangle(Point center, double height, double width) { //constructeur du rectangle
		this.center = center;
		this.height = height;
		this.width = width;
		// *amnesia* lever l'exception
	}
	
	public double left() { //renvoie le point du rectangle le plus a gauche
		return center.x() - (width/2);
	}
	
	public double right() { //renvoie le point du rectangle le plus a droite
		return center.x() + (width/2);
	}
	
	public double bottom() { //renvoie le point du rectangle le plus bas
		return center.y() - (height/2);
	}
	
	public double top() { //renvoie le point du rectangle le plus haut
		return center.y() + (height/2);
	}
	
	public double width() { //renvoie la largeur du rectangle
		return width;
	}

	public double height() { //renvoie la hateur du rectangle
		return height;
	}
	
	public Point center() { //renvoie le point du centre du rectangle
		return center;
	}
	
	public boolean contains(Point p) { //vérifie si le point est a l intérieur du rectangle
		if( p.x()>=left() && p.x()<right() && p.y()<=top() && p.y()>bottom()) {
		return true;}
		else return false;  
	}
	
	public double aspectRatio() { //ratio entre largeur et hauteur, pour comparer la taille des rectangle
		 return width/height;
	 }
	
	public Rectangle expandToAspectRatio(double aspectRatio){ //compare les rectangle qui on le meme centre *amnesia* 
		//à finir!!
		
	}
	
	public String toString() {
		return "(" + center.toString() + "," + width + "," + height + ")";
	}
	 
	 
}
