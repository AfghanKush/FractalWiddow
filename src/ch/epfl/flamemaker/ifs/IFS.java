package ch.epfl.flamemaker.ifs;

import java.util.*;

import ch.epfl.flamemaker.geometry2d.*;
import java.util.Random;



class Randomint { //class random pour générer des int aléatoires // static?
	static Random randomno = new Random();
 }    


public class IFS{ //implements List<AffineTransformation> { il ne faut pas implémenter la list...
	private List<AffineTransformation> ifsList;
	Random randomno = new Random();
	
	
	IFS(List<AffineTransformation> transformations) {
		ifsList = transformations;
	}
	
	public IFSAccumulator compute(Rectangle frame, int width, int height, int density) {
		Point p = Point.ORIGIN;
		for(int j=0; j<20; j++){
			int i=Randomint.randomno.nextInt(ifsList.size()); //entier aléatoire en 0 et n-1 // dernière valeure non-incluse // le bon n??
			//p = F[i](p)  ----> traduire en java, dans la suite
			p= ifsList.get(i).transformPoint(p);
		}
		
		//nombre d intération = H(en nombre de case) x W (en nombre de case) x Density
		int m = height*width*density;
		
		
		IFSAccumulatorBuilder S=new IFSAccumulatorBuilder(frame,width,height); //ensemble intermediaire pour remplir S
		
		
		for(int j=0; j<m; j++){
			int i=Randomint.randomno.nextInt(ifsList.size()); //entier aléatoire en 0 et n-1 // dernière valeure non-incluse // le bon n??
			//p = F[i](p)  ----> traduire en java
			p= ifsList.get(i).transformPoint(p);
			//S=S+p; ---> traduire en java
			S.hit(p); //on remplie la case touchée
			
		}
		
		return S.build(); //on build l IFSAccumulateur;
	}

}


	
