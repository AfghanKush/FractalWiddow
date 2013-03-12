package ch.epfl.flamemaker.flame;

import java.util.Arrays;

public class FlameAccumulator {
	private int[][] isHit; // Tableau de int des cases contenant au moins un point de l'ensemble S
	
	/**
	 * Constructeur, réalise une copie profonde du tableau passé en argument
	 * @param isHit tableau de int des cases contenant au moins un point de l'ensemble S
	 */
	FlameAccumulator(int[][] isHit) {
		this.isHit = Arrays.copyOf(isHit, isHit.length); //copie en profondeur??!!
	}
	
	/**
	 * Méthode retournant la largeur du tableau isHit
	 * @return renvoie la largueur du tableau
	 */
	public int width() {
		return isHit.length;
	}
	
	/**
	 * Méthode retournant la hauteur du tableau isHit
	 * @return renvoie la hauteur du tableau
	 */
	public int height() {
		return isHit[0].length;
	}
	
	
	/**
	 *  Méthode testant si une case contient du tableau contient au moins un point de l'ensemble S
	 * @param x coordonnée x du point a tester
	 * @param y coordonnée y du point a tester
	 * @return renvoie true si la case en question contient au moins un point de l'ensemble S
	 */
	public int isHit(int x, int y) {
		
		if ( ! (0 <= x && x < isHit.length && 0 <= y && y < isHit[0].length)) {
			throw new IndexOutOfBoundsException("Coodonnées à tester invaldes");}
		
		else if (0 <= x && x < isHit.length && 0 <= y && y < isHit[0].length && isHit[x][y] > 0 ) 
			return isHit[x][y]; 
		else return 0;
	}
	
}

