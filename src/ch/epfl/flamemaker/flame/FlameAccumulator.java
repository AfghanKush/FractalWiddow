package ch.epfl.flamemaker.flame;

import java.util.Arrays;

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
	public double intensity(int x, int y) {
		
		if ( ! (0 <= x && x < hitCount.length && 0 <= y && y < hitCount[0].length)) {
			throw new IndexOutOfBoundsException("Coodonn�es � tester invaldes");}
		
		else if (0 <= x && x < hitCount.length && 0 <= y && y < hitCount[0].length && hitCount[x][y] > 0 ) 
			return hitCount[x][y]; 
		else return 0;
	}
	
}

