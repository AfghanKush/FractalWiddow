/*
 *	Author:  Younes
 *	
 */



package ch.epfl.flamemaker.gui;

/**
 * 
 * 
 * 
 * on va séparer les calculs du gui pour pou
 * @see FlameMakerGUI#start
 */
public class FlameMaker {
	public static void main(String[] args) {
		
		
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	
	            	System.out.println("available cores: "+Runtime.getRuntime().availableProcessors());
	                new FlameMakerGUI().start();
	            }
	        });
		
	}
}
