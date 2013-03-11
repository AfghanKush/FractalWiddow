package ch.epfl.flamemaker.ifs;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	public static void main(String[] args) throws FileNotFoundException {
		
		
		//dimension de l'image + densité
		int larg=100;
		int haut=100;
		int dens=1;
		
		
		//triangle de Sierpinski, création de la liste des trasformation linéaires.
		
		ArrayList<AffineTransformation> affList = new ArrayList<AffineTransformation>(); 
		
		affList.add( new AffineTransformation(0.5,0,0,0,0.5,0));
		affList.add(new AffineTransformation(0.5,0,0,0,0.5,0));
		affList.add(new AffineTransformation(0.5,0,0.25,0,0.5,0.5));

		//création du point de centre
		Point p1= new Point(0.5,0.5);
		
		//création du rectangle frame (cadre)
		Rectangle frame = new Rectangle(p1,1,1);
		
		//création de l ensemble S (en point binare, noir et blanc)
		IFS ifs= new IFS(affList);
		
		IFSAccumulator R;
		R=ifs.compute(frame, larg, haut, dens);
		
		
		java.io.File fichier = new java.io.File("test1.PBM"); 

		fichier.setWritable(true);
		
		OutputStream output = new FileOutputStream(fichier);
		PrintStream m=new PrintStream(output);
		System.out.println("P1");
		System.out.println(frame.width()+" "+frame.height()); //largeur puis hateur
		int x;
		for(int i=0; i<haut; i++){
			for(int k=0; k<larg; k++){
				if(R.isHit(i,k))x=1; else x=0;
				System.out.print(x);
			}
			System.out.println("");
		}
			
		m.close();
		
	}

}
