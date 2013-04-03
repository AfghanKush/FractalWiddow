package ch.epfl.flamemaker.color;
import java.util.List;
import java.util.Random;

public class RandomPalette implements Palette{
	
	private List<Color> colorList=null;
	Random randomno = new Random();
	
	RandomPalette(int n)
	{
		for(int i=0; i<n;i++)
		{
			colorList.add(new Color(randomno.nextInt(255),randomno.nextInt(255),randomno.nextInt(255)));
		}	
	}
	
	public Color colorForIndex(double index) 
	{
		if(index<0 || index>1)
		{
			throw new IllegalArgumentException(" index invalide pour l interface Palette");
		}
		
		else
		{
			InterpolatedPalette j=new InterpolatedPalette(colorList);
			return j.colorForIndex(index);
		}
	}
}
