package ch.epfl.flamemaker.ifs;

import java.util.*;

import ch.epfl.flamemaker.geometry2d.*;


public class IFS implements List<AffineTransformation> {
	private List<AffineTransformation> ifsList;
	
	IFS(List<AffineTransformation> transformations) {
		ifsList = transformations;
	}
	
	public IFSAccumulator compute(Rectangle frame, int width, int height, int density) {
		
	}

}
