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
	public FlameTransformation(AffineTransformation affineTransformation, double[] variationWeight)
	{
		if(variationWeight.length !=6) 
		{
			throw new IllegalArgumentException("tableau de poids n'a pas la bonne taille");
		}
		else
		{
		    /*this.variationWeight = new double[variationWeight.length];
			for(int i=0; i<variationWeight.length; i++) {
				this.variationWeight[i] = variationWeight[i];
			}*/
			this.variationWeight=variationWeight;
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
	
	public static class Builder
	{
		private AffineTransformation affineTrans;
		private double [] variationWeight;
		/**
		 * crée le batisseur de la classe FlameTransformation
		 * @param flametrans une FlameTransformation
		 */
		public Builder(FlameTransformation flametrans)
		{
			this.affineTrans=flametrans.affineTransformation;
			
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
		public AffineTransformation Affineget()
		{
			return this.affineTrans;
		}
		
		/**
		 * remplace la trasformationaffine de la Flametransformation correspondante.
		 * @param affinetransform nouvelles valeurs de l'affinetransformation.
		 */
		public void affineIs(AffineTransformation affinetransform)
		{
			this.affineTrans=affinetransform;
		}
		/**
		 * retourne le poids d'index donné
		 * @param index du poid
		 * @return le poid
		 */
		public double[] getVarWeight()
		{
			return this.variationWeight;
		}
		/**
		 * donne une nouvelle valeure au poids a l index donné
		 * @param newVarWeight nouveau poid
		 * @param index index de l emplacement du poid
		 */
		public void setVarWeight(Variation variation, double weight){
			variationWeight[variation.index()] = weight;
		}
		
		public FlameTransformation build()
		{
			return new FlameTransformation(this.affineTrans,this.variationWeight);
		}
	}

	
}
