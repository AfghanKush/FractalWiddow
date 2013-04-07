package ch.epfl.flamemaker.flame;
import ch.epfl.flamemaker.geometry2d.AffineTransformation;
import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Transformation;


public class FlameTransformation implements Transformation,Cloneable
{
	private  AffineTransformation affineTransformation;
	private  double[] variationWeight;
	
	/**
	 * Constucteur de transformation de type Flame
	 * @param affineTransformation transformatoin affine nécessaire à la transformation Flame
	 * @param variationWeight tableau de poids associ
	 */
	FlameTransformation(AffineTransformation affineTransformation, double[] variationWeight)
	{
		if(variationWeight.length !=6) //bon test?
		{
			throw new IllegalArgumentException("tableau de poids n'a pas la bonne taille");
		}
		else
		{
		    this.variationWeight = new double[variationWeight.length];
			for(int i=0; i<variationWeight.length; i++) {
				this.variationWeight[i] = variationWeight[i];
			}
			this.affineTransformation=affineTransformation;
			
		}
	}
	
	/**
	 * Réalise la transformation du point selon la formule 
	 */
	public Point transformPoint(Point p)
	{
		double x = 0;
		double y = 0;
		for(int j=0; j<6; j++)
		{
			x+= variationWeight[j]*Variation.ALL_VARIATIONS.get(j).transformPoint(affineTransformation.transformPoint(p)).x();
			y+= variationWeight[j]*Variation.ALL_VARIATIONS.get(j).transformPoint(affineTransformation.transformPoint(p)).y();
		}
		
		return new Point(x,y);
	}
	
	static class Builder
	{
		FlameTransformation flametrans;
		double [] variationWeight;
		/**
		 * crée le batisseur de la classe FlameTransformation
		 * @param flametrans une FlameTransformation
		 */
		Builder(FlameTransformation flametrans)
		{
			this.flametrans=flametrans;
			
			variationWeight = new double[6]; //réécrire
			for(int i = 0 ; i < 6 ; i++)
			{
				variationWeight[i] = flametrans.variationWeight[i];
			}
		}
		
		/**
		 * renvoie l'affine transformation de la Flametransformation correspondante.
		 * @return l'affine transformation de la Flametransformation correspondante.
		 */
		AffineTransformation Affineget()
		{
			return flametrans.affineTransformation;
		}
		
		/**
		 * remplace la trasformationaffine de la Flametransformation correspondante.
		 * @param affinetransform nouvelles valeurs de l'affinetransformation.
		 */
		void affineIs(AffineTransformation affinetransform)
		{
			this.flametrans.affineTransformation=affinetransform;
		}
		/**
		 * retourne le poids d'index donné
		 * @param index du poid
		 * @return le poid
		 */
		double getVarWeight(int index)
		{
			return flametrans.variationWeight[index];
		}
		/**
		 * donne une nouvelle valeure au poids a l index donné
		 * @param newVarWeight nouveau poid
		 * @param index index de l emplacement du poid
		 */
		void setVarWeight(double newVarWeight, int index)
		{
			flametrans.variationWeight[index]=newVarWeight;
		}
		
		FlameTransformation build()
		{
			return new FlameTransformation(flametrans.affineTransformation,this.variationWeight);
		}
	}

	
}
