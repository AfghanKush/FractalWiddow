package ch.epfl.flamemaker.color;

import java.util.List;
import java.util.Random;


public class InterpolatedPalette implements Palette{
	
	private List<Color> colorList;
	Random randomno = new Random();
	
	public InterpolatedPalette(List<Color> colorList2)
	{
		this.colorList=colorList2;
	}
	
	
	public Color colorForIndex(double index) 
	{
		if(index<0 || index>1)
		{
			throw new IllegalArgumentException(" index invalide pour l interface Palette");
		}
		
		else
		{
			double r=0;
			double g=0;
			double b=0;
			
			for(int i=0; i<colorList.size();i++)
			{
				if(index<((1/(colorList.size()-1)))*(i+1))
				{
					r=colorList.get(i).red()*(index-(i)*1/colorList.size())*(colorList.size()-1); //couleur derrière l intervalle
					r+=colorList.get(i+1).red()*((i+1)*1/colorList.size()-index)*(colorList.size()-1); //couleur devant l intervalle
				
					g=colorList.get(i).green()*(index-(i)*1/colorList.size())*(colorList.size()-1); //couleur derrière l intervalle
					g+=colorList.get(i+1).green()*((i+1)*1/colorList.size()-index)*(colorList.size()-1); //couleur devant l intervalle
					
					b=colorList.get(i).blue()*(index-(i)*1/colorList.size())*(colorList.size()-1); //couleur derrière l intervalle
					b+=colorList.get(i+1).blue()*((i+1)*1/colorList.size()-index)*(colorList.size()-1); //couleur devant l intervalle	
				
				}
					
			}
			
			return new Color(r,g,b);
		}
	}

}
