package ch.epfl.flamemaker.flame;
import ch.epfl.flamemaker.geometry2d.AffineTransformation;
import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Transformation;


public class FlameTransformation implements Transformation,Cloneable
{
	private AffineTransformation affineTransformation;
	private double[] variationWeight;
	
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
			this.variationWeight=variationWeight.clone();
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
		for(int j=0; j<5; j++)
		{
			x+= variationWeight[j]*Variation.ALL_VARIATIONS.get(j).transformPoint(affineTransformation.transformPoint(p)).x();
			y+= variationWeight[j]*Variation.ALL_VARIATIONS.get(j).transformPoint(affineTransformation.transformPoint(p)).y();
		}
		
		return new Point(x,y);
	}
	
}
