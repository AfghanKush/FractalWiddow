package ch.epfl.flamemaker.color;

public class Color 
{
	private double r;
	private double g;
	private double b;
	
	public static Color BLACK=new Color(0,0,0);
	public static Color WHITE=new Color(1,1,1);
	public static Color RED=new Color(1,0,0);
	public static Color GREEN=new Color(0,1,0);
	public static Color BLUE=new Color(0,0,1);
		
		/**
		 * constructeur de Color, produit une couleur
		 * @param r composante rouge de Color
		 * @param g composante verte de Color
		 * @param b composante bleu de Color
		 */
		public Color(double r, double g, double b)
		{
			if((r>1)||(g>1)||(b>1)||(r<0)||(g<0)||(b<0))
				throw new IllegalArgumentException("Coodonnées à tester invaldes dans Color");
			else
			{
				this.r=r;
				this.g=g;
				this.b=b;
			}
		}
		/**
		 * renvoie la composante rouge de la couleur
		 * @return composante rouge de la couleur
		 */
		public double red()
		{
			return this.r;
		}
		
		/**
		 * renvoie la composante verte de lacouleur
		 * @return composante verte de la couleur
		 */
		public double green()
		{
			return this.g;
		}
		
		/**
		 * renvoie la composante bleue de la couleur
		 * @return composante bleue de la couleur
		 */
		public double blue()
		{
			return this.b;
		}

		/**
		 * mélange toutes les composantes de deux couleurs différentes.
		 * @param that deuxième couleur a mélanger avec l'actuelle.
		 * @param proportion proportion du mélange
		 * @return la nouvelle couleur (produit du mélange)
		 */
		public Color mixWith(Color that, double proportion)
		{
			if(proportion<0||proportion>1)
				throw new IllegalArgumentException("la proportion de la couleur est invalide dans Color.mixWith");
			else
				return new Color(r*(1-proportion)+that.red()*proportion,g*(1-proportion)+that.green()*proportion,b*(1-proportion)+that.blue()*proportion);
		}
		/**
		 * Encode la couleur dans un entier par décalage de bits
		 * @return la couleur encodée en un entier
		 */
		public int asPackedRGB()
		{
			int r1=(int)(r*255);
			int g1=(int)(g*255);
			int b1=(int)(b*255);
			
			r1= r1<<16;
			g1=g1<<8;
			return r1&g1&b1;
			
		}
		
		/**
		 * encode la couleur en v-gamma
		 * @param v composante originale
		 * @param max maximum du entier encodé
		 * @return v-gamma composante encodé
		 */
		public static int sRGBEncode(double v, int max)
		{
			double Vs;
			if(v<=0.0031308)
			{
				Vs=v*12.92;
			} 
			else
			{
				Vs=(1.055*Math.pow(v, 1/2.4)-0.055);
			}
			
			return (int)Math.floor(Vs*max);
				
		}
}
