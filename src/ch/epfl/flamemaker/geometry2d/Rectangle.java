package ch.epfl.flamemaker.geometry2d;

public class Rectangle { //classe du rect
	private Point center; //centre du rectangle 
	private double height; //hateur
	private double width; // largeur

	
	/**
	 *  constructeur de l objet rectangle, vérifie si les paramètre son négatifs ou nulls
	 * @param center centre du rctangle
	 * @param width largeur du rectangle
	 * @param height hateur du rectangle
	 */
	Rectangle(Point center, double width, double height) { //constructeur du rectangle
		this.center = center;
		
		if(height<=0 || width<=0){
			throw new IllegalArgumentException("bite");//changer le message amnesia
		}
		this.height = height;
		this.width = width;
		
		
	}
	
	/**
	 * 
	 * @return renvoie le point du rectangle le plus a gauche
	 */
	public double left() { //renvoie le point du rectangle le plus a gauche
		return center.x() - (width/2);
	}
	
	/**
	 * 
	 * @return renvoie le point du rectangle le plus a droite
	 */
	public double right() { //renvoie le point du rectangle le plus a droite
		return center.x() + (width/2);
	}
	
	/**
	 * 
	 * @return renvoie le point du rectangle le plus bas
	 */
	public double bottom() { //renvoie le point du rectangle le plus bas
		return center.y() - (height/2);
	}
	
	/**
	 * 
	 * @return renvoie le point du rectangle le plus haut
	 */
	public double top() { //renvoie le point du rectangle le plus haut
		return center.y() + (height/2);
	}
	
	/**
	 * 
	 * @return retourne la largeur du rectangle
	 */
	public double width() { //renvoie la largeur du rectangle
		return width;
	}

	/**
	 * 
	 * @return hauteur du rectangle
	 */
	public double height() { //renvoie la hateur du rectangle
		return height;
	}
	
	
	/**
	 * 
	 * @return point du centre du rectangle
	 */
	public Point center() { //renvoie le point du centre du rectangle
		return center;
	}
	
	/**
	 * La classe sert a renvoie "true"si le point est au milieu du rectangle
	 * @param p est le point vérifié
	 * @return retourne vrai si le point est dans le rectangle, sinn "false"
	 */
	public boolean contains(Point p) { //vérifie si le point est a l intérieur du rectangle
		if( p.x()>=left() && p.x()<right() && p.y()>=bottom() && p.y()<top()) {
		return true;}
		else return false;  
	}
	
	/**
	 * 
	 * @return le ratio entre largeur et hauteur
	 */
	public double aspectRatio() { //ratio entre largeur et hauteur, pour comparer la taille des rectangle
		 return width/height;
	 }
	
	/**
	 * crée un rectangle au meme centre que le "recu" pour crée le plus petit rectangle qui englobe le "recu.
	 * @param aspectRatio le ratio a obtenir
	 * @return le rectangle créé
	 */
	public Rectangle expandToAspectRatio(double aspectRatio){ //
		
		if(aspectRatio<=0){throw new IllegalArgumentException("mew");}
		
		if(aspectRatio>aspectRatio()){
			return new Rectangle(this.center,aspectRatio*this.height , this.height);
		}
		else if(aspectRatio < aspectRatio()){
			return new Rectangle(this.center, this.width, this.width/aspectRatio);
		}
		else return new Rectangle(this.center, this.width, this.height);
	}
	
	/**
	 * retourne les coordonnées du rectangle
	 */
	public String toString() {
		return "(" + center.toString() + "," + width + "," + height + ")";
	}
	 
	 
}
