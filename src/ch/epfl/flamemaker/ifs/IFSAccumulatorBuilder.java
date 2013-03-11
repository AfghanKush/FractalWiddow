package ch.epfl.flamemaker.ifs;

import ch.epfl.flamemaker.geometry2d.*;

public class IFSAccumulatorBuilder {
	private Rectangle frame;
	private int width;
	private int height;
	private boolean[][] tableauIntermediaire;
	
	IFSAccumulatorBuilder(Rectangle frame, int width, int height) {
		if(!(width >= 0 && height >= 0))
			throw new IllegalArgumentException("Hauteur ou largeur invalide");
		this.frame = frame;
		this.width = width;
		this.height = height;
		tableauIntermediaire = new boolean[width][height];
	}
	
	public void hit(Point p) {
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
