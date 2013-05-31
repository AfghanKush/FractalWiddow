package ch.epfl.flamemaker.color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPalette implements Palette{
	
	Random randomno = new Random();
	private InterpolatedPalette palette;
	
	/**
	 * constructeur de la "RandomPalette", cr�e une palette avec n couleurs al�atoires
	 * @param n nombre de couleur al�atoire
	 */
	public RandomPalette(int n)
	{
		Random rand = new Random();
		List<Color> color = new ArrayList<Color>();
		
		for(int i=0;i<n;i++){
			color.add(new Color(rand.nextInt(255)/255.0,rand.nextInt(255)/255.0, rand.nextInt(255)/255.0));
		}
		this.palette=new InterpolatedPalette(color);
	}
	
	/**
	 * Associe un index a une couleur dans la palette al�atoire.
	 * pour cela on utilise "interpolatedPalette" pour simplifier la tache et �viter la copie de code.
	 * @param index index associ� a la couleur
	 */
	public Color colorForIndex(double index) 
	{
		if(index<0 || index>1)
		{
			throw new IllegalArgumentException(" index invalide pour l interface Palette");
		}
		
		else
		{
			return palette.colorForIndex(index);
		}
	}
}
