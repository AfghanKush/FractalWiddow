package ch.epfl.flamemaker.flame;

import java.util.Arrays;

import ch.epfl.flamemaker.geometry2d.AffineTransformation;
import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Rectangle;

public class FlameAccumulator {
	private int[][] hitCount; // Tableau de int des cases contenant au moins un point de l'ensemble S
	
	/**
	 * Constructeur, réalise une copie profonde du tableau passé en argument
	 * @param isHit tableau de int des cases contenant au moins un point de l'ensemble S
	 */
	FlameAccumulator(int[][] hitCount) {
		this.hitCount = Arrays.copyOf(hitCount, hitCount.length); //copie en profondeur??!!
	}
	
	/**
	 * Méthode retournant la largeur du tableau isHit
	 * @return renvoie la largueur du tableau
	 */
	public int width() {
		return hitCount.length;
	}
	
	/**
	 * Méthode retournant la hauteur du tableau isHit
	 * @return renvoie la hauteur du tableau
	 */
	public int height() {
		return hitCount[0].length;
	}
	
	
	/**
	 *  Méthode testant si une case contient du tableau contient au moins un point de l'ensemble S
	 * @param x coordonnée x du point a tester
	 * @param y coordonnée y du point a tester
	 * @return renvoie true si la case en question contient au moins un point de l'ensemble S
	 */
	public double intensity(int x, int y) { //a vérifier
		
		if ( ! (0 <= x && x < hitCount.length && 0 <= y && y < hitCount[0].length)) {
			throw new IndexOutOfBoundsException("Coodonnées à tester invaldes");}
		
		else if (0 <= x && x < hitCount.length && 0 <= y && y < hitCount[0].length && hitCount[x][y] > 0 ) 
			return Math.log(hitCount[x][y]+1)/Math.log(Flame.NOMBRE_MAX_DE_POINTS_PAR_CASE+1); 
		else return 0;
	}
	
	static class Builder
	{
		private Rectangle frame;
		private int[][] tableauIntermediaire;
		AffineTransformation g;
		
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
			}
		}
		/**
		 * cette méthode construit le nouveau tableau avec les nouvelles valeurs int
		 * @return un tableau "Flameccumulator" avec les nouvelles valeurs.
		 */
		FlameAccumulator build() 
		{
			FlameAccumulator accumulateur = new FlameAccumulator(tableauIntermediaire);	
			return accumulateur;
		}
	}
}

