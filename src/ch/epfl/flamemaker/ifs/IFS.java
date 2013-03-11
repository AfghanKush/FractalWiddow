package ch.epfl.flamemaker.ifs;

import java.util.*;

import ch.epfl.flamemaker.geometry2d.*;
import java.util.Random;



class Randomint { //class random pour g�n�rer des int al�atoires // static?
	static Random randomno = new Random();
 }    


public class IFS{ //implements List<AffineTransformation> { il ne faut pas impl�menter la list...
	private List<AffineTransformation> ifsList;
	Random randomno = new Random();
	
	
	IFS(List<AffineTransformation> transformations) {
		ifsList = transformations;
	}
	
	public IFSAccumulator compute(Rectangle frame, int width, int height, int density) {
		Point p = Point.ORIGIN;
		for(int j=0; j<20; j++){
			int i=Randomint.randomno.nextInt(ifsList.size()); //entier al�atoire en 0 et n-1 // derni�re valeure non-incluse // le bon n??
			//p = F[i](p)  ----> traduire en java, dans la suite
			p= ifsList.get(i).transformPoint(p);
		}
		
		//nombre d int�ration = H(en nombre de case) x W (en nombre de case) x Density
		int m = height*width*density;
		
		
		IFSAccumulatorBuilder S=new IFSAccumulatorBuilder(frame,width,height); //ensemble intermediaire pour remplir S
		
		
		for(int j=0; j<m; j++){
			int i=Randomint.randomno.nextInt(ifsList.size()); //entier al�atoire en 0 et n-1 // derni�re valeure non-incluse // le bon n??
			//p = F[i](p)  ----> traduire en java
			p= ifsList.get(i).transformPoint(p);
			//S=S+p; ---> traduire en java
			S.hit(p); //on remplie la case touch�e
			
		}
		
		return S.build(); //on build l IFSAccumulateur;
	}

}


	
