package ch.epfl.flamemaker.flame;

import java.util.*;

import ch.epfl.flamemaker.geometry2d.*;
import java.util.Random;


/**
 * class Flame qui calcule l'agorithme du chaos.
 * @author younes
 *
 */
public class Flame
{ 
	private List<FlameTransformation> flameList;
	Random randomno = new Random();
	static int NOMBRE_MAX_DE_POINTS_PAR_CASE=100;
	
	/**
	 * fait une copie profonde de la list de transformation Flame
	 * @param transformations
	 */
	public Flame(ArrayList<FlameTransformation> transformations) 
	{
		flameList = transformations;

	}
	
	/**
	 * calcule, grace a l algorithme du chaos l'ensemble des points qui d�finit la fractale.
	 * @param frame cadre dans le quel on affiche la fractale
	 * @param width largeur 
	 * @param height hauteur
	 * @param density densit� de l'image (nombre de points par cm par ex)
	 * @return
	 */
	public FlameAccumulator compute(Rectangle frame, int width, int height, int density) 
	{
		Point p = Point.ORIGIN;
		
		for(int j=0; j<20; j++)
		{
			int i=randomno.nextInt(flameList.size()); //entier al�atoire en 0 et n-1 // derni�re valeure non-incluse
			p= flameList.get(i).transformPoint(p);
		}
		int m = height*width*density;	//nombre d int�ration = H(en nombre de case) x W (en nombre de case) x Density
		FlameAccumulator.Builder S=new FlameAccumulator.Builder(frame,width,height); 
		for(int j=0; j<m; j++)
		{
			int i=randomno.nextInt(flameList.size()); //entier al�atoire en 0 et n-1 // derni�re valeure non-incluse
			//p = F[i](p)  ----> traduire en java
			p= flameList.get(i).transformPoint(p);
			//S=S+p; ---> traduire en java
			S.hit(p); //on remplie la case touch�e	
		}
		return S.build(); //on build l IFSAccumulateur;
	}
}


	
