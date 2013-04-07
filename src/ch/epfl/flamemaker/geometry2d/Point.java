package ch.epfl.flamemaker.geometry2d;

public class Point {

	private double x;
	private double y;
	public static Point ORIGIN = new Point(0, 0); // point static, fait partie
													// de la classe et non de
													// l'objet ;)
	/**
	 * Constructeur de Point
	 * @param x coordonn�e en abscisse du point	
	 * @param y coodonn�e en ordonn�e du point
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Retourne l'abscisse du point
	 * @return abscisse du point
	 */
	public double x() {
		return x;
	}
	
	/**
	 * Retourne l'ordonn�e du point
	 * @return ordonn�e du point
	 */
	public double y() {
		return y;
	}
	
	/**
	 * Retourne la coordonn�e polaire r du point
	 * @return coordonn�e polaire r du point
	 */
	public double r() {
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Retourne la coordonn�e theta (angle) du point
	 * @return coordonn�e theta du point 
	 */
	public double theta() {
		return Math.atan2(x, y);
	}
	
	/**
	 * Retourne une rep�sentation textuelle du point du type : (x,y)
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
