package ch.epfl.flamemaker.color;
import java.util.ArrayList;
import java.util.List;



public class InterpolatedPalette implements Palette
{
	
	private List<Color> colorList;	
	/**
	 * constructeur de la classe "interpolatedPalette"
	 * Il doit y avoir au moins 2 couleur dans la palette sinon il est impossible de former une palette.
	 * @param colorList2 listede couleur prise en argument.
	 */
	public InterpolatedPalette(List<Color> colorList2)
	{
		if (colorList2.size() < 2)
		{
			throw new IllegalArgumentException("il n'y a pas assez de couleurs pour faire une palette");
		}
		colorList= new ArrayList<Color>(colorList2);
	}
	
	
	/**
	 * Donne la couleur dans la palette qui correspond a l'index donné
	 * @param index situant la couleur dans la palette 0 étant le début de la palette (première couleur), 1 étant la fin (dernière couleur)
	 */
	public Color colorForIndex(double index) 
	{
		if(index<0 || index>1)
		{
			throw new IllegalArgumentException(" index invalide pour l interface interpolatedPalette");
		}
		
		else
		{
			double etape = 1.0 /((colorList.size())-1); 
			
			Color couleur1 = null;
			Color couleur2 = null;
			
			Color finalColor=null;
			
			couleur1 = colorList.get((int) Math.floor(index/etape)); //la couleur juste avant
			couleur2 = colorList.get(((int) Math.floor(index/etape) +1)); // la couleur juste après
			double pourcentage = (index - ((int) Math.floor(index/etape))*etape)/etape; //pourcentage a prendre de la couleur
			
			finalColor=couleur1.mixWith(couleur2, pourcentage); //couleur a retourner
			
			
			return finalColor;
		}
	}
}
	
	
