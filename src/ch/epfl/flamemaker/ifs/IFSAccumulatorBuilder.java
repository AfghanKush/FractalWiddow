package ch.epfl.flamemaker.ifs;

import ch.epfl.flamemaker.geometry2d.*;

public class IFSAccumulatorBuilder {
	private Rectangle frame;
	private int width;
	private int height;
	
	IFSAccumulatorBuilder(Rectangle frame, int width, int height) {
		if(!(width >= 0 && height >= 0))
			throw new IllegalArgumentException("Hauteur ou largeur invalide");
		this.frame = frame;
		this.width = width;
		this.height = height;
	}
	
	public void hit(Point p) {
		if(!(p.x()<frame.left()||p.x()>frame.right()||p.y()<frame.bottom()||p.y()>frame.top())){
			int x= (int) Math.floor(p.x());//arrondi
			int y= (int) Math.floor(p.y());//arrondi
			
		}
	}
	
	IFSAccumulator build(){
		
		
		return null;
	}

}
