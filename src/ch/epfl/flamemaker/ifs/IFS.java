package ch.epfl.flamemaker.ifs;

import java.util.*;

import ch.epfl.flamemaker.geometry2d.*;
import java.util.Random;



class Randomint { static //class random pour g�n�rer des int al�atoires // static?
    Random randomno = new Random();
 }    


public class IFS{ //implements List<AffineTransformation> {
	private List<AffineTransformation> ifsList;
	
	IFS(List<AffineTransformation> transformations) {
		ifsList = transformations;
	}
	
	public IFSAccumulator compute(Rectangle frame, int width, int height, int density) {
		Point p=Point.ORIGIN;
		for(int j=0; j<20; j++){
			int i=Randomint.randomno.nextInt(ifsList.size()); //entier al�atoire en 0 et n-1 // derni�re valeure non-incluse // le bon n??
			//p = F[i](p)  ----> traduire en java
		}
		
		//nombre d int�ration= H(en nombre de case) x W (en nombre de case) x Density
		int m= (int)height*(int)width*(int)density;
		
		
		IFSAccumulator S;
		
		
		for(int j=0; j<m; j++){
			int i=Randomint.randomno.nextInt(ifsList.size()); //entier al�atoire en 0 et n-1 // derni�re valeure non-incluse // le bon n??
			//p = F[i](p)  ----> traduire en java
			//S=S+p; ---> traduire en java
		}
		
		return S;
	}

}


	
