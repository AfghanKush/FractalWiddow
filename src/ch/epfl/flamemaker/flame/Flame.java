package ch.epfl.flamemaker.flame;

import java.util.*;

import ch.epfl.flamemaker.geometry2d.*;

import java.util.Random;


/**
 * class Flame qui calcule l'agorithme du chaos.
 *
 */
public class Flame
{ 
	private List<FlameTransformation> flameList;
	Random randomno = new Random(2013); //2013 pour le rendu
	static int NOMBRE_MAX_DE_POINTS_PAR_CASE=100;
	
	/**
	 * fait une copie profonde de la list de transformation Flame
	 * @param flameList2
	 */
	public Flame(List<FlameTransformation> flameList2) 
	{
		flameList= new ArrayList<FlameTransformation>();
		for(int i=0; i<flameList2.size(); i++){
			flameList.add(flameList2.get(i));
		}

	}
	//reecrire cette m�thode
	public static  double getColorIndexForTransformation (int i) {
		if(i < 2) {
			return i;
		} else {
			return (1+(2*(i-Math.pow(2, Math.ceil(Math.log(i)/Math.log(2))-1) -1))) /(Math.pow(2, Math.ceil(Math.log(i)/Math.log(2))));
		}
	}//r��crire cette m�thode.
	
	/**
	 * calcule, grace a l algorithme du chaos l'ensemble des points qui d�finit la fractale. (calcule aussi l'index de la couleur de chaque point)
	 * @param frame cadre dans le quel on affiche la fractale
	 * @param width largeur 
	 * @param height hauteur
	 * @param density densit� de l'image (nombre de points par cm par ex)
	 * @return
	 */
	public FlameAccumulator compute(Rectangle frame, int width, int height, int density) 
	{
		Point p = Point.ORIGIN;
		double colorI = 0;
		int m = height*width*density;	//nombre d int�ration = H(en nombre de case) x W (en nombre de case) x Density
		FlameAccumulator.Builder S=new FlameAccumulator.Builder(frame,width,height); 
		
		
		//reecrire cette m�thode
		double[] colorsIndexesForTransformations = new double[this.flameList.size()]; // ce tableau va contenir les indexs associ�s � chaque transormation de fa�on � ne pas les recalculer � chaque fois
		colorsIndexesForTransformations[0] = 0d;
		colorsIndexesForTransformations[1] = 1d;
		for(int i = 2 ; i < this.flameList.size() ; i++)
		{
			colorsIndexesForTransformations[i] = getColorIndexForTransformation(i);
		} //r��crire cette m�thode.
		
		
		
		for(int j=0; j<20; j++)
		{
			int i=randomno.nextInt(flameList.size()); //entier al�atoire en 0 et n-1 // derni�re valeure non-incluse
			p= flameList.get(i).transformPoint(p);
			
			int transformationId = randomno.nextInt(this.flameList.size()); // on r�cup�re la transformation � effectuer
			p = this.flameList.get(transformationId).transformPoint(p); //on transforme le point par avec cette transofrmation
			colorI = (1d/2)*(colorI + colorsIndexesForTransformations[transformationId]);
		}
		
		for(int j=0; j<m; j++)
		{
			int i=randomno.nextInt(flameList.size()); //entier al�atoire en 0 et n-1 // derni�re valeure non-incluse
			p= flameList.get(i).transformPoint(p);
			S.hit(p,colorI); //on remplie la case touch�e
			
			int transformationId = randomno.nextInt(this.flameList.size()); // on r�cup�re la transformation � effectuer
			p = this.flameList.get(transformationId).transformPoint(p); //on transforme le point par avec cette transofrmation
			colorI= (1d/2)*(colorI + colorsIndexesForTransformations[transformationId]);
		
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
		 * ajoute une Flametransformation � la liste.
		 * @param transformation nouvelle FlameTransformation.
		 */
		void addTransformation(FlameTransformation transformation)
		{
			flame.flameList.add(transformation);
		}
		
		/**
		 * Renvoie l'affinetransformation de la FlameTransformation d'index: "index"
		 * @param index index de la flametransformation
		 * @return affinetransformation li�� a la Flametransformation.
		 */
		AffineTransformation affineTransformation(int index)
		{
			if(index<0 || index>1)
			{
				throw new IllegalArgumentException(" index invalide pour la m�thode Flame.Builder.affineTrasformation");
			}
			else
			{
				return new FlameTransformation.Builder(flame.flameList.get(index)).Affineget();
				
			}
		}
		/**
		 * modifie l'affine transformation de la Flametransformation a l'index donn� de la liste de Flametransformations.
		 * @param index index de la Flametransformation dans sa liste.
		 * @param newTransformation nouvelle transformation affine! (qui remplacera celle de l index.)
		 */
		void setAffineTransformation(int index, AffineTransformation newTransformation)
		{
			if(index<0 || index>1)
			{
				throw new IllegalArgumentException(" index invalide pour la m�thode Flame.Builder.setAffineTransformation");
			}
			else
			{
				FlameTransformation.Builder g= new FlameTransformation.Builder(flame.flameList.get(index));
				g.affineIs(newTransformation);
				
			}
		}
		/**
		 * retourne le poids de la variation donn�e de la flameTrasformation a l'index donn�
		 * @param index de la Flametransformation	
		 * @param variation la variation donn�e (y en a 6)
		 * @return le poid de la variation
		 */
		double variationWeight(int index, Variation variation)
		{
			if(index<0 || index>1)
			{
				throw new IllegalArgumentException(" index invalide pour la m�thode Flame.Builder.variationWeight");
			}
			else
			{
				return new FlameTransformation.Builder(flame.flameList.get(index)).getVarWeight(index); // � v�rifier!
			}
		}
		/**
		 * donne a la variation donn�e (de la FlameTransformation a l index donn�) une nouvelle valeur
		 * @param index de la Flametransformation
		 * @param variation donn�e (y en a 6)
		 * @param newWeight nouveau poids de la variation
		 */
		void setVariationWeight(int index, Variation variation, double newWeight)
		{
			if(index<0 || index>1)
			{
				throw new IllegalArgumentException(" index invalide pour la m�thode Flame.Builder.setVariationWeight");
			}
			else
			{
				int i=Variation.ALL_VARIATIONS.indexOf(variation);
					
				FlameTransformation.Builder g= new FlameTransformation.Builder(flame.flameList.get(index));
				g.setVarWeight(newWeight, i);
			}
		}
		/**
		 * supprime la FlameTRansformation a index donn�
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



	
