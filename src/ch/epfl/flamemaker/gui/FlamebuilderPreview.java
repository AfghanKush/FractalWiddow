package ch.epfl.flamemaker.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.JComponent;

import ch.epfl.flamemaker.color.Color;
import ch.epfl.flamemaker.color.Palette;
import ch.epfl.flamemaker.flame.FlameAccumulator;
import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Rectangle;

/**
 * @author Younes
 *
 */
@SuppressWarnings("serial")
public class FlamebuilderPreview extends  JComponent{
	ObservableFlameBuilder f;
	Color background;
	Palette p;
	Rectangle r;
	int densite;
	//-----------------------------------------------------bonus
	static boolean finished=false; //classe utilisée sur une seule composante, donc pas de problèmes.
	static boolean begun=false; //classe utilisée sur une seule composante, donc pas de problèmes.
	BufferedImage image;
	BufferedImage savedImage;
	
	//bonus lissage
	public static int lissage=1;
	
	private FlameAccumulator flameaccumulator;
	
	int s=0; //pour savoir a combien de points on est.
	String filename; //nom du fichier enregistré
	//-----------------------------------------------------bonus
	
	
	
	public FlamebuilderPreview(ObservableFlameBuilder flameBuilder, Color background, Palette p, Rectangle r, int densite){
		this.f=flameBuilder;
		this.background=background;
		this.p=p;
		this.r=r;
		this.densite=densite;
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(300,300);
	} 
	
	
	public void changeFileName(String filename){
		this.filename=filename;
	}
	
	/**
	 * Sauvegarde l'image dans le format désiré
	 * @param g
	 * @param fileType
	 * @param outputfile
	 * @throws IOException
	 */
	public void saveImage(String fileType,int larg, int haut, int dens) throws IOException{
		
		
		
		File outputfile = new File(filename+"."+fileType);

		calcFractal(haut,larg);
		
		ImageIO.write(image, fileType, outputfile);
		
		FlameMakerGUI.ImageSavedWarning(); //affiche une boite de dialogue pour dire que l'image a été sauvegardée
	}
	
	
	 private static BufferedImage gammaCorrection(BufferedImage original, double gamma) {
		 
	        int alpha, red, green, blue;
	        int newPixel;
	         
	        double gamma_new = 1 / gamma;
	        int[] gamma_LUT = gamma_LUT(gamma_new);
	         
	        BufferedImage gamma_cor = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
	 
	        for(int i=0; i<original.getWidth(); i++) {
	            for(int j=0; j<original.getHeight(); j++) {
	 
	                // Get pixels by R, G, B
	                alpha = new java.awt.Color(original.getRGB(i, j)).getAlpha();
	                red = new java.awt.Color(original.getRGB(i, j)).getRed();
	                green = new java.awt.Color(original.getRGB(i, j)).getGreen();
	                blue = new java.awt.Color(original.getRGB(i, j)).getBlue();
	                 
	                red = gamma_LUT[red];
	                green = gamma_LUT[green];
	                blue = gamma_LUT[blue];
	                                 
	                // Return back to original format
	                newPixel = colorToRGB(alpha, red, green, blue);
	 
	                // Write pixels into image
	                gamma_cor.setRGB(i, j, newPixel);
	 
	            }
	         
	        }
	        //System.out.println("la correction est appliqué");
	        return gamma_cor;        
	    
	    }
	 
	 //creation de la table LUTgamma
	 private static int[] gamma_LUT(double gamma_new) {
	        int[] gamma_LUT = new int[256];
	         
	        for(int i=0; i<gamma_LUT.length; i++) {
	            gamma_LUT[i] = (int) (255 * (Math.pow((double) i / (double) 255, gamma_new)));
	        }
	         
	        return gamma_LUT;
	    }
	 
	    // Convert R, G, B, Alpha to standard 8 bit
	    private static int colorToRGB(int alpha, int red, int green, int blue) {
	 
	        int newPixel = 0;
	        newPixel += alpha;
	        newPixel = newPixel << 8;
	        newPixel += red; newPixel = newPixel << 8;
	        newPixel += green; newPixel = newPixel << 8;
	        newPixel += blue;
	 
	        return newPixel;
	 
	    }
	
	
	
	//-------------------------------------------------------------------------------, bonus
	
	//methode qui change la densité
	
	public void ChangeDens(int i){
		if(i>50)System.out.println("Densité trop elevée >50");
		this.densite=i;
		f.notifyObserver();
	}
	
	public void updateThis(){
		f.notifyObserver();
	}
	
	//methode qui translate le cadre
	public void translateFrame(Point p){
		r=new Rectangle(new Point(r.center().x()+(p.x()*r.width()),r.center().y()+(p.y()*r.height())),r.width(),r.height());
		f.notifyObserver();
	}
	
	
	//methode qui zoome le cadre.
	public void zoomFrame(double noches){
		r=new Rectangle(r.center(),r.width()*noches,r.height()*noches);
		f.notifyObserver();
	}
	
	
	//methode qui remet le cadre par defaut.
	public void defaultFrame(Rectangle rect){
		r=rect;
		f.notifyObserver();
	}
	
	public void changePaletteR(Palette p){
		this.p=p;
		f.notifyObserver();
	}
	
	public void calcFractal(int height, int width){
		begun=true;//on a commencé a calculer la fractale..
		finished=false;
		
		Rectangle rect = r.expandToAspectRatio(new Rectangle(Point.ORIGIN, width,height).aspectRatio());


		flameaccumulator=f.build().compute(rect, width*lissage, height*lissage, densite);
		
		image= new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width;j++){
				
				
				
				
				
				if(lissage==1){
					image.setRGB(j, i,flameaccumulator.color(p, background, j, height-1-i).asPackedRGB());
				}
				
				
				else{ //aplication de l'antialiasing
						
						double l=1.0;
						Color mixedcolor=Color.BLACK;
						//Color finalcolor=null;
						
						for(int s=i*lissage; s<i*lissage+lissage; s++){
							for(int k=j*lissage; k<j*lissage+lissage; k++){
								
								Color thisColor=flameaccumulator.color(p, background,k, height*lissage-1-s);
								
								mixedcolor=mixedcolor.mixWith(thisColor,1/l);
								l++;
							}
						}
						
						//System.out.println("fin de calcul");
						
						image.setRGB(j, i,mixedcolor.asPackedRGB());
					}
				
				
				
				
				
				
				if((i==height-1)&&(j==width-1))
					finished=true;
				
			}
		}
		
		image=gammaCorrection(image,FlameMakerGUI.gammaValue);
			
	}
	
	public void paintComponent(Graphics g0){

		Graphics2D g = (Graphics2D)g0;
		
		savedImage=image;
		
		g.drawString("Modifer quelque chose pour afficher une fractale", getWidth()-300, getHeight()-15);
		
		//calcFractal();
		if(begun && !finished){
			g.drawImage(savedImage,0,0,null);
			g.setColor(java.awt.Color.RED);
			g.drawString("En chargement...", getWidth()-100, getHeight()-15);
		}
		else{
			
			g.drawImage(image,0,0,null);
			FlameMakerGUI.runningThreads=0;
		}
			

	}

}