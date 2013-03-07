package ch.epfl.flamemaker.geometry2d;

import static org.junit.Assert.*;

import org.junit.Test;

public class AffineTransformationTest {
	
	private static double DELTA = 0.000000001;

	@Test
	public void testAffineTransformation() {
		new AffineTransformation(1,1,1,1,1,1);
	}
	
	@Test
	public void testNewTranslation() {
		AffineTransformation translation = AffineTransformation.newTranslation(3,3);
		assertEquals(translation.a,1,DELTA);
		assertEquals(translation.b,0,DELTA);
		assertEquals(translation.c,3,DELTA);
		assertEquals(translation.d,0,DELTA);
		assertEquals(translation.e,1,DELTA);
		assertEquals(translation.f,3,DELTA);
	}
	
	@Test
	public void testNewRotation() {
		AffineTransformation rotation = AffineTransformation.newRotation(0.69);
		assertEquals(rotation.a,Math.cos(0.69),DELTA);
		assertEquals(rotation.b,-Math.sin(0.69),DELTA);
		assertEquals(rotation.c,0,DELTA);
		assertEquals(rotation.d,Math.sin(0.69),DELTA);
		assertEquals(rotation.e,Math.cos(0.69),DELTA);
		assertEquals(rotation.f,0,DELTA);
	}
	
	@Test
	public void testNewScaling() {
		AffineTransformation scaling = AffineTransformation.newScaling(2,4);
		assertEquals(scaling.a,2,DELTA);
		assertEquals(scaling.b,0,DELTA);
		assertEquals(scaling.c,0,DELTA);
		assertEquals(scaling.d,0,DELTA);
		assertEquals(scaling.e,4,DELTA);
		assertEquals(scaling.f,0,DELTA);
	}
	
	@Test
	public void testNewShearX() {
		AffineTransformation shearX = AffineTransformation.newShearX(3);
		assertEquals(shearX.a,1,DELTA);
		assertEquals(shearX.b,3,DELTA);
		assertEquals(shearX.c,0,DELTA);
		assertEquals(shearX.d,0,DELTA);
		assertEquals(shearX.e,1,DELTA);
		assertEquals(shearX.f,0,DELTA);	
	}
	
	@Test
	public void testNewShearY() {
		AffineTransformation shearY = AffineTransformation.newShearY(3);
		assertEquals(shearY.a,1,DELTA);
		assertEquals(shearY.b,0,DELTA);
		assertEquals(shearY.c,0,DELTA);
		assertEquals(shearY.d,3,DELTA);
		assertEquals(shearY.e,1,DELTA);
		assertEquals(shearY.f,0,DELTA);	
	}
	
	@Test
	public void testTransformPoint() {
		AffineTransformation transformTest1 = new AffineTransformation(1,2,3,5,5,4);
		Point pointTest1 = new Point(2,3);
		pointTest1 = transformTest1.transformPoint(pointTest1);
		assertEquals(pointTest1.x(),11,DELTA);
		assertEquals(pointTest1.y(),29,DELTA);		
	}
	
	@Test
	public void testTranslationX() {
		AffineTransformation transformTest2 = new AffineTransformation(1,2,3,5,5,4);
		assertEquals(transformTest2.c,3,DELTA);
	}
	
	@Test
	public void testTranslationY() {
		AffineTransformation transformTest3 = new AffineTransformation(1,2,3,5,5,4);
		assertEquals(transformTest3.f,4,DELTA);
	}

}
