package ch.epfl.flamemaker.ifs;

import java.util.*;

import ch.epfl.flamemaker.geometry2d.*;
import java.util.Random;


/**
 * class IFS qui calcule l'agorithme du chaos.
 * @author younes
 *
 */
public class IFS{ //implements List<AffineTransformation> { il ne faut pas impl�menter la list...
	private List<AffineTransformation> ifsList;
	Random randomno = new Random();
	
	/**
	 * fait une copie profonde de la list de transformation affines
	 * @param transformations
	 */
	public IFS(List<AffineTransformation> transformations) {
		ifsList = transformations;
	}
	
	/**
	 * calcule, grace a l algorithme du chaos l'ensemble des points qui d�finit la fractale.
	 * @param frame cadre dans le quel on affiche la fractale
	 * @param width largeur 
	 * @param height hauteur
	 * @param density densit� de l'image (nombre de points par cm par ex)
	 * @return
	 */
	public IFSAccumulator compute(Rectangle frame, int width, int height, int density) 
	{
		Point p = Point.ORIGIN;
		
		for(int j=0; j<20; j++)
		{
			int i=randomno.nextInt(ifsList.size()); //entier al�atoire en 0 et n-1 // derni�re valeure non-incluse
			p= ifsList.get(i).transformPoint(p);
		}
		int m = height*width*density;	//nombre d int�ration = H(en nombre de case) x W (en nombre de case) x Density
		IFSAccumulatorBuilder S=new IFSAccumulatorBuilder(frame,width,height); 
		for(int j=0; j<m; j++)
		{
			int i=randomno.nextInt(ifsList.size()); //entier al�atoire en 0 et n-1 // derni�re valeure non-incluse
			//p = F[i](p)  ----> traduire en java
			p= ifsList.get(i).transformPoint(p);
			//S=S+p; ---> traduire en java
			S.hit(p); //on remplie la case touch�e	
		}
		return S.build(); //on build l IFSAccumulateur;
	}
}


	
