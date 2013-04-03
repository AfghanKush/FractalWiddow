package ch.epfl.flamemaker.geometry2d;

public class AffineTransformation implements Transformation //Classe des transformations affines
{
	double a; // élément variable de la matrice homogène
	double b; // élément variable de la matrice homogène
	double c; // élément variable de la matrice homogène
	double d; // élément variable de la matrice homogène
	double e; // élément variable de la matrice homogène
	double f; // élément variable de la matrice homogène
	public static AffineTransformation IDENTITY= new AffineTransformation(1,0,0,0,1,0); //matrice identité I3
	
	/**
	 *  constructeur de l'objet AffineTransformation
	 * @param a 1er élément variable de la matrice homogène
	 * @param b 2ème élément variable de la matrice homogène
	 * @param c 3ème élément variable de la matrice homogène
	 * @param d 4ème élément variable de la matrice homogène
	 * @param e 5ème élément variable de la matrice homogène
	 * @param f 6ème élément variable de la matrice homogène
	 */
	public AffineTransformation(double a, double b, double c, double d, double e, double f){
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
		this.e=e;
		this.f=f;
	}
	
	/**
	 *  transformation affine représentant une translation de dx unités parallélement à l'abscisse 
	 *  et dy unités parallélement à l'ordonnée
	 * @param dx unités de translation parallélement à l'abscisse 
	 * @param dy unités de translation parallélement à l'ordonnée
	 * @return renvoie la matrice de transformation affine de translation
	 */
	public static AffineTransformation newTranslation(double dx, double dy){ 
		AffineTransformation t = new AffineTransformation(1,0,dx,0,1,dy);
		return t;
	}
		
	/**
	 * transformation représentant une rotation d'angle theta (en radians) autour de l'origine
	 * @param theta angle de la rotation
	 * @return renvoie la matrice de transformation affine de rotation
	 */
	public static AffineTransformation newRotation(double theta){ 
		AffineTransformation r= new AffineTransformation(Math.cos(theta),-Math.sin(theta),0,Math.sin(theta),Math.cos(theta),0);
		return r;
	}

	/**
	 * transformation représentant une dilatation d'un facteur sx parallélement à l'abscisse 
	 * et d'un facteur sy parallélement à l'ordonnée
	 * @param sx facteur de dilatation parallélement à l'abscisse 
	 * @param sy facteur de dilatation parallélement à l'ordonnée
	 */
	public static AffineTransformation newScaling(double sx, double sy){ 
		AffineTransformation s = new AffineTransformation(sx,0,0,0,sy,0);
		return s;

	}

	/**
	 * transformation affine représentant une transvection d'un facteur sx parallélement à l'abscisse
	 * @param sx facteur de transvection
	 * @return renvoie la matrice de transformation affine de transvection (parallélement à l'axe des abscisses)
	 */
	public static AffineTransformation newShearX(double sx){ 
		AffineTransformation shx= new AffineTransformation(1,sx,0,0,1,0);
		return shx;
	}
	
	/**
	 * transformation affine représentant une transvection d'un facteur sy parallélement à l'ordonnée
	 * @param sy facteur de transvection
	 * @return renvoie la matrice de transformation affine de transvection (parallélement à l'axe des ordonnées)
	 */
	public static AffineTransformation newShearY(double sy){ 
		AffineTransformation shy= new AffineTransformation(1,0,0,sy,1,0);
		return shy;
	}
	
	/**
	 * retourne le point p transformé par cette transformation
	 * @param p point à transformer
	 * @return renvoie le point transformé
	 */
	public Point transformPoint(Point p){ 
		Point z = new Point(p.x()*a+p.y()*b+c, p.x()*d+p.y()*e+f);
		return z;
		
	}
	
	/**
	 * retourne la valeur horizontale de la translation 
	 * @return renvoie la composante horizontale de la translation (c)
	 */
	public double translationX(){ 
		return c;
	}
	
	/**
	 * retourne la valeurverticale de la translation
	 * @return renvoie la composante verticale de la translation (f)
	 */
	public double translationY(){ 
		return f;
	}
	
	/**
	 * retourne la composition de cette transformation affine avec la transformation affine that
	 * @param that transformation affine à composer 
	 * @return renvoie la transformation affine résultante
	 */
	public AffineTransformation composeWith(AffineTransformation that){
		AffineTransformation j= new AffineTransformation(that.a*this.a+that.b*this.d, that.a*this.b+that.b*this.e, this.c*that.a+that.b*this.f+that.c, this.a*that.d+that.e*this.d, that.d*this.b+that.e*this.e, that.d*this.c+that.e*this.f+that.f);
		return j;
	}
	
	
}
