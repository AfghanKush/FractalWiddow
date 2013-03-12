package ch.epfl.flamemaker.ifs;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import ch.epfl.flamemaker.geometry2d.AffineTransformation;
import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Rectangle;

public class TestEt2 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException 
	{
		
		
		//dimension de l'image + densité
		int larg=240;
		int haut=400;
		int dens=150;
		
		ArrayList<AffineTransformation> affList = new ArrayList<AffineTransformation>(); 
		
		//triangle de Sierpinski, création de la liste des trasformation linéaires.

		/*
		affList.add( new AffineTransformation(0.5,0,0,0,0.5,0));
		affList.add(new AffineTransformation(0.5,0,0.5,0,0.5,0));
		affList.add(new AffineTransformation(0.5,0,0.25,0,0.5,0.5));
		*/
		
		
		//frctal fougère
		affList.add( new AffineTransformation(0,0,0,0.16,0,0));
		affList.add(new AffineTransformation(0.2,-0.26,0,0.23,0.22,1.6));
		affList.add(new AffineTransformation(-0.15,0.28,0,0.26,0.24,0.44));
		affList.add(new AffineTransformation(0.85,0.04,0,-0.04,0.85,1.6));
		

		//création du point de centre
		Point p1= new Point(0,4.5);
		
		//création du rectangle frame (cadre)
		Rectangle frame = new Rectangle(p1,6,10);
		
		//création de l ensemble S (en point binare, noir et blanc)
		IFS ifs= new IFS(affList);
		
		IFSAccumulator R;
		R=ifs.compute(frame, larg, haut, dens);
		
		
		java.io.File fichier = new java.io.File("test1.PBM"); 

		fichier.setWritable(true);
		
		OutputStream output = new FileOutputStream(fichier);
		PrintStream m=new PrintStream(output);
		m.println("P1");
		m.println(larg+" "+haut); //largeur puis hateur
		
		int x;
		

		for(int i=haut-1; i>=0; i--)
		{
			for(int k=0; k<larg; k++)
			{
				if(R.isHit(k,i))x=1; else x=0;
				m.print(x);
			}
			m.println("");
		}
			
		m.close();
		
	}

}   //le resultat est bon!
