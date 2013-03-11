package ch.epfl.flamemaker.ifs;

import ch.epfl.flamemaker.geometry2d.*;

public class IFSAccumulatorBuilder {
	private Rectangle frame;
	
	private boolean[][] tableauIntermediaire;
	AffineTransformation g;
	
	IFSAccumulatorBuilder(Rectangle frame, int width, int height) {
		if(!(width >= 0 && height >= 0))
			throw new IllegalArgumentException("Hauteur ou largeur invalide");
		this.frame = frame;
		double ratiow= width/frame.width();
		double ratioh= height/frame.height();
		double diffX= -frame.left();
		double diffY= -frame.bottom();

		this.g= AffineTransformation.newTranslation(diffX, diffY);
		this.g.composeWith(AffineTransformation.newScaling(ratiow, ratioh));
		
		tableauIntermediaire = new boolean[width][height];
	}
	
	public void hit(Point p) {
		g.transformPoint(p);
		if(frame.contains(p)){ //code plus propre.
			int x= (int) Math.floor(p.x());//arrondi
			int y= (int) Math.floor(p.y());//arrondi
			
			tableauIntermediaire[x][y] = true;
		}
	}
	
	IFSAccumulator build() {
		IFSAccumulator accumulateur = new IFSAccumulator(tableauIntermediaire);	
		return accumulateur;
	}

}
