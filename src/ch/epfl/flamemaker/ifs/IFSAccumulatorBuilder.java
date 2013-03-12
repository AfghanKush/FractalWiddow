package ch.epfl.flamemaker.ifs;

import ch.epfl.flamemaker.geometry2d.*;
/**
 * IFSAccumulatorBuilder construit par étape la tableau de booleen qui représente l'ensemble S (transformation affines)
 * @author younes
 *
 */
public class IFSAccumulatorBuilder 
{
	private Rectangle frame;
	private boolean[][] tableauIntermediaire;
	AffineTransformation g;
	
	/**
	 * Constructeur de la classe IFSAccumulatorBuilder, on y calcule une transformation pour le changement de base (plan-->cadre)
	 * @param frame cadre de la fratale
	 * @param width largeur du plan
	 * @param height hauteur du plan
	 */
	IFSAccumulatorBuilder(Rectangle frame, int width, int height) 
	{
		if(!(width >= 0 && height >= 0))
			throw new IllegalArgumentException("Hauteur ou largeur invalide");
		this.frame = frame;
		double ratiow= width/frame.width();
		double ratioh= height/frame.height();
		double diffX= -frame.left();
		double diffY= -frame.bottom();

		this.g= AffineTransformation.newTranslation(diffX, diffY);
		this.g=this.g.composeWith(AffineTransformation.newScaling(ratiow, ratioh));
		
		tableauIntermediaire = new boolean[width][height];
	}
	/**
	 * la méthode hit coche la case dans le tableau de booleen comme touché
	 * @param p point pris en argument, modifié ensuite avec la transformation pour changement de base
	 */
	public void hit(Point p) 
	{	
		if(frame.contains(p))
		{ //code plus propre.
			Point j=g.transformPoint(p);
			int x= (int) Math.floor(j.x());//arrondi
			int y= (int) Math.floor(j.y());//arrondi
			tableauIntermediaire[x][y] = true;
		}
	}
	/**
	 * cette méthode construit le nouveau tableau avec les valeur précédmment cochées
	 * @return un tableau "IFSAccumulator" avec les nouvelles valeurs.
	 */
	IFSAccumulator build() 
	{
		IFSAccumulator accumulateur = new IFSAccumulator(tableauIntermediaire);	
		return accumulateur;
	}

}
