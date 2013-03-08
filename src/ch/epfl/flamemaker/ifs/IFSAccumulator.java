package ch.epfl.flamemaker.ifs;

import java.util.Arrays;

public class IFSAccumulator {
	private boolean[][] isHit; // Tableau de boolean des cases contenant au moins un point de l'ensemble S
	
	/**
	 * Constructeur, r�alise une copie profonde du tableau pass� en argument
	 * @param isHit tableau de boolean des cases contenant au moins un point de l'ensemble S
	 */
	IFSAccumulator(boolean[][] isHit) {
		this.isHit = Arrays.copyOf(isHit, isHit.length);
	}
	
	/**
	 * M�thode retournant la largeur du tableau isHit
	 * @return renvoie la largueur du tableau
	 */
	public int width() {
		return isHit.length;
	}
	
	/**
	 * M�thode retournant la hauteur du tableau isHit
	 * @return renvoie la hauteur du tableau
	 */
	public int height() {
		return isHit[0].length;
	}
	
	
	/**
	 *  M�thode testant si une case contient du tableau contient au moins un point de l'ensemble S
	 * @param x coordonn�e x du point a tester
	 * @param y coordonn�e y du point a tester
	 * @return renvoie true si la case en question contient au moins un point de l'ensemble S
	 */
	public boolean isHit(int x, int y) {
		
		if ( ! (0 <= x && x < isHit.length && 0 <= y && y < isHit[0].length)) {
			throw new IndexOutOfBoundsException("Coodonn�es � tester invaldes");}
		
		else if (0 <= x && x < isHit.length && 0 <= y && y < isHit[0].length) //remplir avec la v�rification de S
			return true; 
		else return false;
	}
	
}

