


package ch.epfl.flamemaker.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ch.epfl.flamemaker.flame.Flame;
import ch.epfl.flamemaker.flame.FlameTransformation;
import ch.epfl.flamemaker.flame.Variation;
import ch.epfl.flamemaker.geometry2d.AffineTransformation;
import ch.epfl.flamemaker.gui.FlameMakerGUI.Observer;

public class ObservableFlameBuilder {
	
	final private List<FlameTransformation.Builder> transs;
	private HashSet<Observer> listObs = new HashSet<Observer>();
	
	/** ajoute un observateur 
	 *@param o l'observateur a ajouter
	 *@see ObservableFlameBuilder#listObs
	 */
	public void addObserver(Observer o){
		listObs.add(o);
	}
	
	/** supprime un observateur de la liste
	 *@param o l'observateur a supprimer
	 *@see ObservableFlameBuilder#listObs
	 */
	public void removeObserver(Observer o){
		listObs.remove(o);
	}
	
	/** avertie les observateurs qu'une remise à jour est necessaire, grace a leur methode update
	 *@see FlameMakerGUI#Observer
	 */
	public void notifyObserver(){
		for(Observer o : listObs){
			o.update();
		}
	}
	
	/** construit un builder avec une liste de flameTransformation (normaaal)
	 *@param trans une liste de flameTransformation
	 */
	public ObservableFlameBuilder (List<FlameTransformation> trans){
		this.transs = new ArrayList<FlameTransformation.Builder>();
		for(int i=0; i<trans.size(); i++){
			this.transs.add(new FlameTransformation.Builder(trans.get(i)));
		}
	}
	/** verifie si l'index est valide
	 * @param ind l'index à tester
	 * @throws IndexOutOfBoundsException leve une exception si l'index ne se trouve pas entre 0 et tranformation.size
	 */
	private void checkIndex(int ind){
		if(ind<0 || ind>=transs.size()){
			throw new IndexOutOfBoundsException("wrong index= "+ind);
		}
	}
	/**
	 * @return retourne la taille du tableau de trans
	 */
	public int transformationCount(){
		return transs.size();
	}
	/** Ajoute une FlameTransformation a la liste
	 *@param trans la FlameTransformation que l'on souhaite ajouter
	 */
	public void addTransformation(FlameTransformation trans){
		this.transs.add(new FlameTransformation.Builder(trans));
		notifyObserver();
	}
	/**
	 * Bonus
	 * Rajoute les paramètre prédéfinis
	 * @param flamouse liste de FlamTrasformation.Builder a rajouter dans la transformation
	 */
	public void addAllTransformation(ArrayList<FlameTransformation> flamouse){
		for(int i=0;i<flamouse.size(); i++){
			this.transs.add(new FlameTransformation.Builder(flamouse.get(i)));
			//notifyObserver();
		}
		
	}
	
	/** Permet d'obtenirla transformation du x
	 *@param x l'index de la tranformation voulu
	 *@see ObservableFlameBuilder#checkIndex(int)
	 */
	public AffineTransformation affineTransformation(int x){
		checkIndex(x);
		return transs.get(x).Affineget();
	}
	/** Permet de modifier la transformation d'index souhaite
	 *@param x l'index de la tranformation que l'on souhaite remplacer
	 *@param newTrans la nouvelle transformation que l'on va mettre Ã  la place
	 *@see ObservableFlameBuilder#checkIndex(int)
	 */
	public void setAffineTransformation(int x, AffineTransformation newTrans){
		checkIndex(x);
		transs.get(x).affineIs(newTrans);
		notifyObserver();
	}
	
	//cherche les paramètre de la matrice
	public double[] getParam(int x){
		checkIndex(x);
		return transs.get(x).Affineget().getMatrix();
	}
	//set les paramètre de la matrice
	public void setParam(int x, int indexParam, double param){
		checkIndex(x);
		
		if(indexParam==0){
			transs.get(x).Affineget().setMatrixA(param);
		}
		else if(indexParam==1){
			transs.get(x).Affineget().setMatrixB(param);
		}
		else if(indexParam==2){
			transs.get(x).Affineget().setMatrixC(param);
				}
		else if(indexParam==3){
			transs.get(x).Affineget().setMatrixD(param);
		}
		else if(indexParam==4){
			transs.get(x).Affineget().setMatrixE(param);
		}
		else if(indexParam==5){
			transs.get(x).Affineget().setMatrixF(param);
		}
		else{
			System.out.println("erreur sur (devrait etre entre 0 et 5) indexParam= "+indexParam);
		}
		
		notifyObserver();
	}
	
	/** Permet d'obtenir le poids d'une variation d'une transformation
	 *@param x l'index de la tranformation voulue
	 *@param var variation dont le poid nous interesse
	 *@see ObservableFlameBuilder#checkIndex(int)
	 */
	public double variationWeight(int x, Variation var){
		checkIndex(x);
		return transs.get(x).getVarWeight()[var.index()];
	}
	/** Permet de modifier le poids d'une variation
	 *@param x l'index de la tranformation
	 *@param var variation dont l'on souhaite modifier le poids
	 *@param newWeight valeur du poids 
	 *@see ObservableFlameBuilder#checkIndex(int)
	 */
	public void setVariationWeight(int x, Variation var, double newWeight){
		checkIndex(x);
		transs.get(x).setVarWeight(var, newWeight);
		notifyObserver();
	}
	/** supprime la FlameTransformation de la liste
	 *@param x l'index de la tranformation que l'on souhaite supprimer
	 *@see ObservableFlameBuilder#checkIndex(int)
	 */
	public void removeTransformation(int x){
		checkIndex(x);
		transs.remove(x);
		notifyObserver();
	}
	/**
	 * fait partie du bonus.
	 * supprime toutes les transformations, aide pour le changement entre "turbulence" et "sharkfin"
	 */
	public void removeAllTransformation(){ //pas besoin de notify pour cette méthode, on veut pas d'affichage.
		
			transs.removeAll(transs);	
	}
	/** 
	 *construit une Flame selon le builder.
	 *
	 *@see FlameTransformation.Builder#build()
	 *@see Flame#Flame(List)
	 */
	public Flame build(){
		
		List<FlameTransformation> transformationList = new ArrayList<FlameTransformation>();
		for(FlameTransformation.Builder i : transs){
			transformationList.add(i.build());
		}
		
		return new Flame(transformationList);
	}
}
