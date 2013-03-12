package ch.epfl.flamemaker.flame;

import java.util.Arrays;

import ch.epfl.flamemaker.geometry2d.AffineTransformation;
import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Rectangle;

public class FlameAccumulator {
	private int[][] hitCount; // Tableau de int des cases contenant au moins un point de l'ensemble S
	
	/**
	 * Constructeur, r�alise une copie profonde du tableau pass� en argument
	 * @param isHit tableau de int des cases contenant au moins un point de l'ensemble S
	 */
	FlameAccumulator(int[][] hitCount) {
		this.hitCount = Arrays.copyOf(hitCount, hitCount.length); //copie en profondeur??!!
	}
	
	/**
	 * M�thode retournant la largeur du tableau isHit
	 * @return renvoie la largueur du tableau
	 */
	public int width() {
		return hitCount.length;
	}
	
	/**
	 * M�thode retournant la hauteur du tableau isHit
	 * @return renvoie la hauteur du tableau
	 */
	public int height() {
		return hitCount[0].length;
	}
	
	
	/**
	 *  M�thode testant si une case contient du tableau contient au moins un point de l'ensemble S
	 * @param x coordonn�e x du point a tester
	 * @param y coordonn�e y du point a tester
	 * @return renvoie true si la case en question contient au moins un point de l'ensemble S
	 */
	public double intensity(int x, int y) { //a v�rifier
		if ( ! (0 <= x && x < hitCount.length && 0 <= y && y < hitCount[0].length)) {
			throw new IndexOutOfBoundsException("Coodonn�es � tester invaldes");}
		
		else 
		{
			//return Math.log(hitCount[x][y]+1)/Math.log(FlameAccumulator.Builder.maximum+1); 
			//return Math.log(hitCount[x][y]+1)/Math.log(Flame.NOMBRE_MAX_DE_POINTS_PAR_CASE+1);
			return hitCount[x][y];
		}
		
	}
	
	static class Builder
	{
		private Rectangle frame;
		private int[][] tableauIntermediaire;
		AffineTransformation g;
		static int maximum=0;
		
		Builder(Rectangle frame, int width, int height)
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
			
			tableauIntermediaire = new int[width][height];
		}
		
		public void hit(Point p) 
		{	
			if(frame.contains(p)) //code plus propre.
			{ 
				Point j=g.transformPoint(p);
				int x= (int) Math.floor(j.x());//arrondi
				int y= (int) Math.floor(j.y());//arrondi
				tableauIntermediaire[x][y]++;
				if(maximum<tableauIntermediaire[x][y])maximum=tableauIntermediaire[x][y];
			}
		}
		/**
		 * cette m�thode construit le nouveau tableau avec les nouvelles valeurs int
		 * @return un tableau "Flameccumulator" avec les nouvelles valeurs.
		 */
		FlameAccumulator build() 
		{
			FlameAccumulator accumulateur = new FlameAccumulator(tableauIntermediaire);	
			return accumulateur;
		}
	}
}

