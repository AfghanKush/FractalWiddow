package ch.epfl.flamemaker.geometry2d;

import static org.junit.Assert.*;

import org.junit.Test;


public class PointTest {
	private static double DELTA = 0.000000001;
	
	@Test
	public void testPoint() {
		new Point(1,1);
	}
	
	@Test
	public void testr(){ //améliorer les tests
		Point g= new Point(4,3);
		assertEquals(g.r(), 5, DELTA);
	}
	
	@Test
	public void testTheta(){ //améliorer les tests
		Point g= new Point(4,3);
		assertEquals(g.theta(),0.9272952,DELTA);
		
		
	}

}
