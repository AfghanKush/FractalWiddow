package ch.epfl.flamemaker.flame;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import ch.epfl.flamemaker.geometry2d.AffineTransformation;
import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Rectangle;

public class FlamePGMMaker {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException 
	{
		
		
		//dimension de l'image + densité
		int larg=500;
		int haut=400;
		int dens=50;
		
		ArrayList<AffineTransformation> affList = new ArrayList<AffineTransformation>(); 
		ArrayList<FlameTransformation> flameList= new ArrayList<FlameTransformation>();
		
	
		//--------------------------------------------------------------------------------->>>>>>>>>>>shark fin
		//création de la liste de transformation affines utilisées.
		affList.add( new AffineTransformation(-0.4113504,-0.7124804,-0.4,0.7124795,-0.4113508,0.8));
		affList.add(new AffineTransformation(-0.3957339,0,-1.6,0,-0.3957337,0.2));
		affList.add(new AffineTransformation(0.4810169,0,1,0,0.4810169,0.9));
		
		//création des tableau de pondération
		double[] a={1,0.1,0,0,0,0};
		double[] b={0,0,0,0,0.8,1};
		double[] c={1,0,0,0,0,0};
		
		//création des Flametransformations...
		flameList.add(new FlameTransformation(affList.get(0),a));
		flameList.add(new FlameTransformation(affList.get(1),b));
		flameList.add(new FlameTransformation(affList.get(2),c));
		//--------------------------------------------------------------------------------->shark fin ---> lafin
		
		

		//création du point de centre
		Point p1= new Point(-0.25,0);
		
		//création du rectangle frame (cadre)
		Rectangle frame = new Rectangle(p1,5,4);
		
		//création de l ensemble S (en point binare, noir et blanc)
		Flame flame= new Flame(flameList);
		
		FlameAccumulator R;
		R=flame.compute(frame, larg, haut, dens);
		
		
		java.io.File fichier = new java.io.File("Shark_fin.PGM"); 

		fichier.setWritable(true);
		
		OutputStream output = new FileOutputStream(fichier);
		PrintStream m=new PrintStream(output);
		m.println("P2");
		m.println(larg+" "+haut); //largeur puis hateur
		m.println(FlameAccumulator.Builder.maximum); //luminosité max
		

		for(int i=haut-1; i>=0; i--)
		{
			for(int k=0; k<larg; k++)
			{
				m.print((int)R.intensity(k,i));
				m.print(" ");
				
			}
			m.println("");
		}
		System.out.println(FlameAccumulator.Builder.maximum);


		
		m.close();
		
	}

} 
