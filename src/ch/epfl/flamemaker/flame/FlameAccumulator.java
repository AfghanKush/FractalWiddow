package ch.epfl.flamemaker.flame;


import ch.epfl.flamemaker.color.Color;
import ch.epfl.flamemaker.color.Palette;
import ch.epfl.flamemaker.geometry2d.AffineTransformation;
import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Rectangle;

public class FlameAccumulator {
	private int[][] hitCount; // Tableau de int des cases contenant au moins un point de l'ensemble S
	private double[][] colorIndexSum;
	static int maximum=0; //le nombre maximum de points par case

	
	/**
	 * Constructeur, r�alise une copie profonde des tableau pass� en argument
	 * @param isHit tableau de int des cases contenant au moins un point de l'ensemble S
	 * @param colorIndexSum tableau de double contenant la moyenne des index de couleurs de chaque case
	 */
	FlameAccumulator(int[][] hitCount, double[][] colorIndexSum) {
		this.hitCount = new int[hitCount.length][hitCount[0].length];
		for(int i=0; i<hitCount.length; i++) {
			for(int j=0; j<hitCount[0].length; j++) {
				this.hitCount[i][j] = hitCount[i][j];
			}
		}
		this.colorIndexSum = new double[colorIndexSum.length][colorIndexSum[0].length];
		for(int i=0; i<colorIndexSum.length; i++) {
			for(int j=0; j<colorIndexSum[0].length; j++) {
				this.colorIndexSum[i][j] = colorIndexSum[i][j];
			}
		}
		
	}
	
	/**
	 * calcule la couleur du point au coordonn�e x,y
	 * @param palette
	 * @param background
	 * @param x
	 * @param y
	 * @return la couleur du point au coordonn�es (x,y)
	 */
	public Color color(Palette palette, Color background, int x, int y)
	{
		if ( ! (0 <= x && x < hitCount.length && 0 <= y && y < hitCount[0].length)) 
		{
			throw new IndexOutOfBoundsException("Coodonn�es � tester invaldes (fonction color)");
		}
		
		else
		{
			//calcule la couleur du point au coordonn�e x-y
			int r;
			int g;
			int b;
			
			r= (int)(intensity(x,y)*palette.colorForIndex(colorIndexSum[x][y]).red() + background.red()*(1-intensity(x,y)));
			g= (int)(intensity(x,y)*palette.colorForIndex(colorIndexSum[x][y]).green() + background.green()*(1-intensity(x,y)));
			b= (int)(intensity(x,y)*palette.colorForIndex(colorIndexSum[x][y]).blue() + background.blue()*(1-intensity(x,y)));
			
			return new Color(r,g,b);

		}
	}
	
	/**
	 * M�thode retournant la largeur du tableau isHit
	 * @return renvoie la largueur du tableau
	 */
	public int width() 
	{
		return hitCount.length;
	}
	
	/**
	 * M�thode retournant la hauteur du tableau isHit
	 * @return renvoie la hauteur du tableau
	 */
	public int height() 
	{
		return hitCount[0].length;
	}
	
	
	/**
	 *  M�thode testant si une case contient du tableau contient au moins un point de l'ensemble S
	 * @param x coordonn�e x du point a tester
	 * @param y coordonn�e y du point a tester
	 * @return renvoie true si la case en question contient au moins un point de l'ensemble S
	 */
	public double intensity(int x, int y) 
	{
		if ( ! (0 <= x && x < hitCount.length && 0 <= y && y < hitCount[0].length)) 
		{
			throw new IndexOutOfBoundsException("Coodonn�es � tester invaldes pour intensity");
		}
		
		double g=(double)((Math.log(hitCount[x][y]+1))/(Math.log(FlameAccumulator.maximum+1))); 
		return g;
	}
	
	static class Builder
	{
		private Rectangle frame;
		private int[][] tableauIntermediaire;
		AffineTransformation g;
		private double[][] colorIndexInterm;
		
		/**
		 * Builder de Flameaccumulator, construit le tableau repr�sentant l image et le tableau de couleurs(index).
		 * @param frame carr� representant la taille de l image apr�s transformation.
		 * @param width largeur de base de l image
		 * @param height hauteur de base de l image
		 */
		Builder(Rectangle frame, int width, int height)
		{
			if(!(width >= 0 && height >= 0))
				throw new IllegalArgumentException("Hauteur ou largeur invalide dans builder de flameaccumulator");
			this.frame = frame;
			double ratiow= width/frame.width();
			double ratioh= height/frame.height();
			double diffX= -frame.left();
			double diffY= -frame.bottom();

			this.g= AffineTransformation.newTranslation(diffX, diffY);
			this.g=this.g.composeWith(AffineTransformation.newScaling(ratiow, ratioh));
			
			tableauIntermediaire = new int[width][height];
			colorIndexInterm= new double[width][height];
		}
		
		/**
		 * construit le tableau a deux dimension des points (qui repr�sente l image) en incr�mentant la case touch�
		 * @param p repr�sente le point a incr�menter dans le tableau
		 */
		public void hit(Point p, double colorI) 
		{	
			if(frame.contains(p)) //code plus propre.
			{ 
				Point j=g.transformPoint(p);
				int x= (int) Math.floor(j.x());//arrondi
				int y= (int) Math.floor(j.y());//arrondi
				tableauIntermediaire[x][y]++;
				if(maximum<tableauIntermediaire[x][y]){maximum=tableauIntermediaire[x][y];} //calcule le nombre de points max par case
				
				//colorIndexInterm[x][y]= tableauIntermediaire[x][y]/maximum;	
				if(tableauIntermediaire[x][y] != 0) //r��crire
				{
					this.colorIndexInterm[x][y] = (1d/(tableauIntermediaire[x][y]+1)) * ((this.colorIndexInterm[x][y] *  tableauIntermediaire[x][y]) + colorI); 
				}
				else
				{
					this.colorIndexInterm[x][y] = colorI;
				}//r��crire
				
			}
		}
		/**
		 * cette m�thode construit le nouveau tableau avec les nouvelles valeurs int
		 * @return un tableau "Flameccumulator" avec les nouvelles valeurs.
		 */
		FlameAccumulator build() 
		{
			
			
			FlameAccumulator accumulateur = new FlameAccumulator(tableauIntermediaire,colorIndexInterm );	//rajouter la couleur!
			return accumulateur;
		}
	}
}

