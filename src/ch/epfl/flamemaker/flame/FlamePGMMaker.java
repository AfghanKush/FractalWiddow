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
		
		/*//--------------------------------------------------------------------------------->>>>>>>>>>>shark fin
		//dimension de l'image + densité
		int larg=500;
		int haut=400;
		int dens=50;
		
		ArrayList<AffineTransformation> affList = new ArrayList<AffineTransformation>(); 
		ArrayList<FlameTransformation> flameList= new ArrayList<FlameTransformation>();
		
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
		
		//création du point de centre
		Point p1= new Point(-0.25,0);
				
		//création du rectangle frame (cadre)
		Rectangle frame = new Rectangle(p1,5,4);
		//--------------------------------------------------------------------------------->shark fin ---> lafin*/
		
		
		//--------------------------------------------------------------------------------->>>>>>>>>>>turbulence
		//dimension de l'image + densité
		int larg=500;
		int haut=500;
		int dens=5;
		
		//création du point de centre
		Point p1= new Point(0.1,0.1);
						
		//création du rectangle frame (cadre)
		Rectangle frame = new Rectangle(p1,3,3);
		
		ArrayList<AffineTransformation> affList = new ArrayList<AffineTransformation>(); 
		ArrayList<FlameTransformation> flameList= new ArrayList<FlameTransformation>();
		
		//création de la liste de transformation affines utilisées.
		affList.add( new AffineTransformation(0.7124807,-0.4113509,-0.3,0.4113513,0.7124808,-0.7));
		affList.add(new AffineTransformation(0.3731079,-0.6462417,0.4,0.6462414,0.3731076,0.3));
		affList.add(new AffineTransformation(0.0842641,-0.314478,-0.1,0.314478,0.0842641,0.3));
		
		//création des tableau de pondération
		double[] a={0.5,0,0,0.4,0,0};
		double[] b={1,0,0.1,0,0,0};
		double[] c={1,0,0,0,0,0};
		
		//création des Flametransformations...
		flameList.add(new FlameTransformation(affList.get(0),a));
		flameList.add(new FlameTransformation(affList.get(1),b));
		flameList.add(new FlameTransformation(affList.get(2),c));
		
		
		//--------------------------------------------------------------------------------->turbulences ---> lafin*/
		
		
		
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
		//m.println(Flame.NOMBRE_MAX_DE_POINTS_PAR_CASE); //luminosité max variante du prof
		m.println(Flame.NOMBRE_MAX_DE_POINTS_PAR_CASE); //luminosité max variante younes
		

		for(int i=haut-1; i>=0; i--)
		{
			for(int k=0; k<larg; k++)
			{
				double g=R.intensity(k,i)*Flame.NOMBRE_MAX_DE_POINTS_PAR_CASE; //multiplier par nbdecasesblabla si on change de variante
				int j= (int)g;
				m.print(j);
				m.print(" ");
				
			}
			m.println("");
		}


		
		m.close();
		
	}

} 
