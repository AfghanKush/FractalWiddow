package ch.epfl.flamemaker.flame;

import java.util.*;

import ch.epfl.flamemaker.geometry2d.*;
import ch.epfl.flamemaker.gui.FlamebuilderPreview;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * class Flame qui calcule l'agorithme du chaos.
 *
 */
public class Flame
{ 
	static double colorI;
	private final List<FlameTransformation> flameList;
	Random randomno = new Random(2013); //2013 pour le rendu
	static int NOMBRE_MAX_DE_POINTS_PAR_CASE=100;
	
	//-------------------------------------Bonus
	
	int threadsnumber=8;
	private FlameAccumulator.Builder[] S1=new FlameAccumulator.Builder[threadsnumber];
	final  ExecutorService schedulSwag =Executors.newScheduledThreadPool(threadsnumber); //20threads max^^
	
	
	
	/**
	 * fait une copie profonde de la list de transformation Flame
	 * @param flameList2
	 */
	public Flame(List<FlameTransformation> flameList2) 
	{
		flameList= new ArrayList<FlameTransformation>();
		for(int i=0; i<flameList2.size(); i++){
			flameList.add(flameList2.get(i));
		}

	}
	/**
	 * Trouve le bon index de couleur pour la transformation choisie.(i)
	 * @param i index de la transformation
	 * @return
	 */
	public static  double indexColTransformation(int i) {
		if(i >= 2) 
		{
			double x=1+(2*(i-Math.pow(2, Math.ceil(Math.log(i)/Math.log(2))-1) -1));
			double y=Math.pow(2, Math.ceil(Math.log(i)/Math.log(2)));
			
			return (double)x/y;	
		} 
		else 
		{
			return i;
		}
	}//réécrire cette méthode.
	
	
	/**
	 * calcule, grace a l algorithme du chaos l'ensemble des points qui définit la fractale. (calcule aussi l'index de la couleur de chaque point)
	 * @param frame cadre dans le quel on affiche la fractale
	 * @param width largeur 
	 * @param height hauteur
	 * @param density densité de l'image (nombre de points par cm par ex)
	 * @return
	 */
	public FlameAccumulator compute(Rectangle frame, int width, int height, int density) 
	{
		Point p = Point.ORIGIN;
		double colorI = 0.0;
		//final int m = height*width*density;	//nombre d intération = H(en nombre de case) x W (en nombre de case) x Density
		FlameAccumulator.Builder S=new FlameAccumulator.Builder(frame,width,height); 
		
		
		double[] TrfIndex = new double[this.flameList.size()];
		for(int i = 0 ; i < this.flameList.size() ; i++)
		{
			if(i==0)
				TrfIndex[0] = 0.0;
			
			else if(i==1)
				TrfIndex[1]=1.0;
			
			TrfIndex[i] = indexColTransformation(i);
		} 
		
		
		
		for(int j=0; j<20; j++)
		{
			int i=randomno.nextInt(flameList.size()); //entier aléatoire en 0 et n-1 // dernière valeure non-incluse
			p= flameList.get(i).transformPoint(p);

			colorI = (1.0/2.0)*(colorI + TrfIndex[i]);
		}
		
		
		ArrayList<Thread> myThreads = new ArrayList<Thread>();
		
		for(int j=0; j<threadsnumber; j++) //8thread différent vont calculer l'algorithme du chaos.
		{
			final calculator calculFrac = new calculator(height,width,density, frame, TrfIndex, p, colorI); 
			myThreads.add(calculFrac);
			calculFrac.start();
			S1[j]=calculFrac.TreadBuilder();
			//S=calculFrac.TreadBuilder();
		}
		
		boolean stay = false;
		
		do{
			stay = false;
			for(Thread t : myThreads ){
				if(t.getState()!= Thread.State.TERMINATED){
					stay=true;
				}
			}
		}while(stay);
		
		for(int i=0;i<threadsnumber;i++)
			S.AddBuilder(S1[i],i);
		
		return S.build(); //on build l IFSAccumulateur;
	}
	
	private class calculator extends Thread{
		
		Point p = Point.ORIGIN;
		double colorIn;
		final int m;	//nombre d intération = H(en nombre de case) x W (en nombre de case) x Density
		FlameAccumulator.Builder R; 
		double[] TrfIndex= new double[flameList.size()];
		
		public calculator(int height, int width, int density, Rectangle frame, double[] TrfIndex, Point p, double colorI) {
			
			m = height*width*density;
			R=new FlameAccumulator.Builder(frame,width,height);
			this.TrfIndex=TrfIndex;
			this.p=p;
			this.colorIn=colorI;
		}

		@Override
		public void run(){

    	  for(int w=0;  w< m/threadsnumber ;w++){
    		  
	    		  int i=randomno.nextInt(flameList.size()); //entier aléatoire en 0 et n-1 // dernière valeure non-incluse
				  p= flameList.get(i).transformPoint(p);
				  colorIn = (1.0/2.0)*(colorIn + TrfIndex[i]);
				  
				  //System.out.println("Bloque là?");
				  
				  R.hit(p,colorIn); //on remplie la case touchée
			  
    	  	  }	     	  
    	  colorI=colorIn;
	    }
		
		public FlameAccumulator.Builder TreadBuilder(){
			
			return R;
		}	         		       		     
	}
		
	
	
	
	
	static public class Builder
	{
		private ArrayList<FlameTransformation.Builder> transformations;
		
		public Builder(Flame flame)
		{
			this.transformations= new ArrayList<FlameTransformation.Builder>();
			
			for(FlameTransformation transformation: flame.flameList){
				transformations.add(new FlameTransformation.Builder(transformation));
			}
			
		}
		/**
		 * retourne le nombre FlameTransformation dans la liste
		 * @return nombre de FlameTransformation dans la liste
		 */
		public int transformationCount()
		{
			return transformations.size();
		}
		
		/**
		 * ajoute une Flametransformation à la liste.
		 * @param transformation nouvelle FlameTransformation.
		 */
		public void addTransformation(FlameTransformation transformation)
		{
			transformations.add(new FlameTransformation.Builder(transformation));
		}
		
		/**
		 * Renvoie l'affinetransformation de la FlameTransformation d'index: "index"
		 * @param index index de la flametransformation
		 * @return affinetransformation liéé a la Flametransformation.
		 */
		public AffineTransformation affineTransformation(int index)
		{
			if(index<0 || index >transformations.size())
			{
				throw new IllegalArgumentException(" index invalide pour la méthode Flame.Builder.affineTrasformation");
			}
			else
			{
				return transformations.get(index).Affineget();
				
			}
		}
		/**
		 * modifie l'affine transformation de la Flametransformation a l'index donné de la liste de Flametransformations.
		 * @param index index de la Flametransformation dans sa liste.
		 * @param newTransformation nouvelle transformation affine! (qui remplacera celle de l index.)
		 */
		public void setAffineTransformation(int index, AffineTransformation newTransformation)
		{
			if(index<0 || index>transformations.size())
			{
				throw new IllegalArgumentException(" index invalide pour la méthode Flame.Builder.setAffineTransformation");
			}
			else
			{
				transformations.get(index).affineIs(newTransformation);
			}
		}
		/**
		 * retourne le poids de la variation donnée de la flameTrasformation a l'index donné
		 * @param index de la Flametransformation	
		 * @param variationIndex index de la variation donnée (y en a 6)
		 * @return le poid de la variation
		 */
		public double[] variationWeight(int index,Variation variation)
		{
			if(index<0 || index>transformations.size())
			{
				throw new IllegalArgumentException(" index invalide pour la méthode Flame.Builder.variationWeight");
			}
			else
			{
				return transformations.get(index).getVarWeight(); 
			}
		}
		/**
		 * donne a la variation donnée (de la FlameTransformation a l index donné) une nouvelle valeur
		 * @param index de la Flametransformation
		 * @param variation donnée (y en a 6)
		 * @param newWeight nouveau poids de la variation
		 */
		public void setVariationWeight(int index, Variation variation, double newWeight)
		{
			if(index<0 || index>1)
			{
				throw new IllegalArgumentException(" index invalide pour la méthode Flame.Builder.setVariationWeight");
			}
			else
			{
				transformations.get(index).setVarWeight(variation, newWeight); 
			}
		}
		/**
		 * supprime la FlameTRansformation a index donné
		 * @param index de la FlameTransformation
		 */
		
		public void removeTransformation(int index)
		{
			transformations.remove(index);
		}
		/**
		 * construit et retourne la fractale flame
		 * @return la fractale flame.
		 */
		public Flame build()
		{
			List<FlameTransformation> list=new ArrayList<FlameTransformation>();
			
			for(int i=0; i<transformations.size(); i++){
				list.add(transformations.get(i).build());
			}
			
			return new Flame(list);
		}
	}
}



	
