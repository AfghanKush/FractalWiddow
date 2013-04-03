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
	 * @param flameList2
	 */
	public Flame(List<FlameTransformation> flameList2) 
	{
		flameList= new ArrayList<FlameTransformation>();
		for(FlameTransformation gh: flameList2){
			flameList.add(gh);
		}

	}
	
	/**
	 * calcule, grace a l algorithme du chaos l'ensemble des points qui définit la fractale.
	 * @param frame cadre dans le quel on affiche la fractale
	 * @param width largeur 
	 * @param height hauteur
	 * @param density densité de l'image (nombre de points par cm par ex)
	 * @return
	 */
	public FlameAccumulator compute(Rectangle frame, int width, int height, int density) 
	{
		Point p = Point.ORIGIN;
		
		for(int j=0; j<20; j++)
		{
			int i=randomno.nextInt(flameList.size()); //entier aléatoire en 0 et n-1 // dernière valeure non-incluse
			p= flameList.get(i).transformPoint(p);
		}
		int m = height*width*density;	//nombre d intération = H(en nombre de case) x W (en nombre de case) x Density
		FlameAccumulator.Builder S=new FlameAccumulator.Builder(frame,width,height); 
		for(int j=0; j<m; j++)
		{
			int i=randomno.nextInt(flameList.size()); //entier aléatoire en 0 et n-1 // dernière valeure non-incluse
			p= flameList.get(i).transformPoint(p);
			S.hit(p); //on remplie la case touchée	
		}
		return S.build(); //on build l IFSAccumulateur;
	}
	
	
	static class Builder
	{
		Flame flame=null;
		Builder(Flame flame)
		{
			this.flame=flame;
		}
		/**
		 * retourne le nombre FlameTransformation dans la liste
		 * @return nombre de FlameTransformation dans la liste
		 */
		int transformationCount()
		{
			return flame.flameList.size();
		}
		
		/**
		 * ajoute une Flametransformation à la liste.
		 * @param transformation nouvelle FlameTransformation.
		 */
		void addTransformation(FlameTransformation transformation)
		{
			flame.flameList.add(transformation);
		}
		
		/**
		 * Renvoie l'affinetransformation de la FlameTransformation d'index: "index"
		 * @param index index de la flametransformation
		 * @return affinetransformation liéé a la Flametransformation.
		 */
		AffineTransformation affineTransformation(int index)
		{
			if(index<0 || index>1)
			{
				throw new IllegalArgumentException(" index invalide pour la méthode Flame.Builder.affineTrasformation");
			}
			else
			{
				return new FlameTransformation.Builder(flame.flameList.get(index)).Affineget();
				
			}
		}
		/**
		 * modifie l'affine transformation de la Flametransformation a l'index donné de la liste de Flametransformations.
		 * @param index index de la Flametransformation dans sa liste.
		 * @param newTransformation nouvelle transformation affine! (qui remplacera celle de l index.)
		 */
		void setAffineTransformation(int index, AffineTransformation newTransformation)
		{
			if(index<0 || index>1)
			{
				throw new IllegalArgumentException(" index invalide pour la méthode Flame.Builder.setAffineTransformation");
			}
			else
			{
				FlameTransformation.Builder g= new FlameTransformation.Builder(flame.flameList.get(index));
				g.affineIs(newTransformation);
				
			}
		}
		/**
		 * retourne le poids de la variation donnée de la flameTrasformation a l'index donné
		 * @param index de la Flametransformation	
		 * @param variation la variation donnée (y en a 6)
		 * @return le poid de la variation
		 */
		double variationWeight(int index, Variation variation)
		{
			if(index<0 || index>1)
			{
				throw new IllegalArgumentException(" index invalide pour la méthode Flame.Builder.variationWeight");
			}
			else
			{
				return new FlameTransformation.Builder(flame.flameList.get(index)).getVarWeight(index); // à vérifier!
			}
		}
		/**
		 * donne a la variation donnée (de la FlameTransformation a l index donné) une nouvelle valeur
		 * @param index de la Flametransformation
		 * @param variation donnée (y en a 6)
		 * @param newWeight nouveau poids de la variation
		 */
		void setVariationWeight(int index, Variation variation, double newWeight)
		{
			if(index<0 || index>1)
			{
				throw new IllegalArgumentException(" index invalide pour la méthode Flame.Builder.setVariationWeight");
			}
			else
			{
				int i=Variation.ALL_VARIATIONS.indexOf(variation);
					
				FlameTransformation.Builder g= new FlameTransformation.Builder(flame.flameList.get(index));
				g.setVarWeight(newWeight, i);
			}
		}
		/**
		 * supprime la FlameTRansformation a index donné
		 * @param index de la FlameTransformation
		 */
		void removeTransformation(int index)
		{
			flame.flameList.remove(index);
		}
		/**
		 * construit et retourne la fractale flame
		 * @return la fractale flame.
		 */
		Flame build()
		{
			return new Flame(flame.flameList);
		}
	}
}



	
