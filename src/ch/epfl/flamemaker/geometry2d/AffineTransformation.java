package ch.epfl.flamemaker.geometry2d;

public class AffineTransformation implements Transformation{ //Classe des transformations affines
	double a; // �l�ment variable de la matrice homog�ne
	double b; // �l�ment variable de la matrice homog�ne
	double c; // �l�ment variable de la matrice homog�ne
	double d; // �l�ment variable de la matrice homog�ne
	double e; // �l�ment variable de la matrice homog�ne
	double f; // �l�ment variable de la matrice homog�ne
	public static AffineTransformation IDENTITY= new AffineTransformation(1,0,0,0,1,0); //matrice identit� I3
	
	/**
	 *  constructeur de l'objet AffineTransformation
	 * @param a 1er �l�ment variable de la matrice homog�ne
	 * @param b 2�me �l�ment variable de la matrice homog�ne
	 * @param c 3�me �l�ment variable de la matrice homog�ne
	 * @param d 4�me �l�ment variable de la matrice homog�ne
	 * @param e 5�me �l�ment variable de la matrice homog�ne
	 * @param f 6�me �l�ment variable de la matrice homog�ne
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
	 *  transformation affine repr�sentant une translation de dx unit�s parall�lement � l'abscisse 
	 *  et dy unit�s parall�lement � l'ordonn�e
	 * @param dx unit�s de translation parall�lement � l'abscisse 
	 * @param dy unit�s de translation parall�lement � l'ordonn�e
	 * @return renvoie la matrice de transformation affine de translation
	 */
	public static AffineTransformation newTranslation(double dx, double dy){ 
		AffineTransformation t = new AffineTransformation(1,0,dx,0,1,dy);
		return t;
	}
		
	/**
	 * transformation repr�sentant une rotation d'angle theta (en radians) autour de l'origine
	 * @param theta angle de la rotation
	 * @return renvoie la matrice de transformation affine de rotation
	 */
	public static AffineTransformation newRotation(double theta){ 
		AffineTransformation r= new AffineTransformation(Math.cos(theta),-Math.sin(theta),0,Math.sin(theta),Math.cos(theta),0);
		return r;
	}

	/**
	 * transformation repr�sentant une dilatation d'un facteur sx parall�lement � l'abscisse 
	 * et d'un facteur sy parall�lement � l'ordonn�e
	 * @param sx facteur de dilatation parall�lement � l'abscisse 
	 * @param sy facteur de dilatation parall�lement � l'ordonn�e
	 */
	public static AffineTransformation newScaling(double sx, double sy){ 
		AffineTransformation s = new AffineTransformation(sx,0,0,0,sy,0);
		return s;

	}

	/**
	 * transformation affine repr�sentant une transvection d'un facteur sx parall�lement � l'abscisse
	 * @param sx facteur de transvection
	 * @return renvoie la matrice de transformation affine de transvection (parall�lement � l'axe des abscisses)
	 */
	public static AffineTransformation newShearX(double sx){ 
		AffineTransformation shx= new AffineTransformation(1,sx,0,0,1,0);
		return shx;
	}
	
	/**
	 * transformation affine repr�sentant une transvection d'un facteur sy parall�lement � l'ordonn�e
	 * @param sy facteur de transvection
	 * @return renvoie la matrice de transformation affine de transvection (parall�lement � l'axe des ordonn�es)
	 */
	public static AffineTransformation newShearY(double sy){ 
		AffineTransformation shy= new AffineTransformation(1,0,0,sy,1,0);
		return shy;
	}
	
	/**
	 * retourne le point p transform� par cette transformation
	 * @param p point � transformer
	 * @return renvoie le point transform�
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
	 * @param that transformation affine � composer 
	 * @return renvoie la transformation affine r�sultante
	 */
	public AffineTransformation composeWith(AffineTransformation that){
		AffineTransformation j= new AffineTransformation(that.a+a,that.b+b,that.c+c,that.d+d,that.e+e,that.f+f);
		return j;
	}
	
	
}
