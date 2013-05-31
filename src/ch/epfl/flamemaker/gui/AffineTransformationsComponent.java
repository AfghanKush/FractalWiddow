package ch.epfl.flamemaker.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import ch.epfl.flamemaker.geometry2d.AffineTransformation;
import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Rectangle;

@SuppressWarnings("serial")
public class AffineTransformationsComponent extends JComponent {
	Rectangle r= null;
	int highlightedTransformationIndex=0;
	 Shape[][] s;//tableau de shapes, pour pouvoir les selectionner (Bonus)
	 static boolean mouse; //true if mouse inside component (Bonus)
	 boolean mouseHover[]; //true if mouse inside shape! (Bonus)
	
	private ObservableFlameBuilder builderFlame;
	
	AffineTransformationsComponent(Rectangle rect, ObservableFlameBuilder builderFlame){
		this.r=rect;
		this.builderFlame=builderFlame;
		s=new Shape[builderFlame.transformationCount()][4];
		mouseHover=new boolean[builderFlame.transformationCount()];
	}
	
	public void mouseHov(int i){
		mouseHover[i]=true;
	}
	public void NOmouseHov(int i){
		mouseHover[i]=false;
	}
	
	public Shape[][] getShapes(){ //pour pouvoir appeler les shapes après.
		return s;
	}

	public int getHighlightedTransformationIndex() {
		return highlightedTransformationIndex;
	}



	public void setHighlightedTransformationIndex(int highlightedTransformationIndex) {
		this.highlightedTransformationIndex = highlightedTransformationIndex;
		repaint(); //pour voir la trans selectionnée en rouge.
	}

	
	//-------------------------------------------------------------------------------, bonus
	//methode qui translate le cadre
	public void translateFrame(Point p){
		r=new Rectangle(new Point(r.center().x()+(p.x()*r.width()),r.center().y()+(p.y()*r.height())),r.width(),r.height());
		//System.out.println("le centre r est à x: "+r.center().x()+" y: "+r.center().y());
		repaint();
	}
	
	
	//methode qui zoome le cadre.
	public void zoomFrame(double noches){
		r=new Rectangle(r.center(),r.width()*noches,r.height()*noches);
		repaint();
	}
	
	//methode qui remet le cadre par defaut.
		public void defaultFrame(Rectangle rect){
			r=rect;
			repaint();
		}
	public void paintComponent(Graphics g0){
		Graphics2D g2= (Graphics2D) g0;
		Rectangle rect=this.r.expandToAspectRatio(getWidth()/(double)getHeight());
		//antialiasing pour un meilleure rendu
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		//fin de l'antialiasing
		
		//changement de plan, transformation
		//translation
		AffineTransformation translation = AffineTransformation.newTranslation(-rect.left(), -rect.bottom());
		//homothétie
		AffineTransformation scaling = AffineTransformation.newScaling(getWidth()/rect.width(),getHeight()/rect.height());
		//mélange des deux.
		AffineTransformation g =translation.composeWith(scaling);
		

		
		
		//dessiner la grille.
		
		g2.setColor(new Color(0.7f, 0.7f, 0.7f));//gris pour la grille.
		
		
		Point x1xn = new Point(Math.ceil(rect.left()),rect.top());
		Point y1yn = new Point(Math.ceil(rect.left()), rect.bottom());
		
		while(x1xn.x()<=rect.right()){
			g2.draw(new Line2D.Double(g.transformPoint(x1xn).x(),-g.transformPoint(x1xn).y()+this.getHeight(), g.transformPoint(y1yn).x(),-g.transformPoint(y1yn).y()+this.getHeight()));
			x1xn=x1xn.swagAddPoint(new Point(1,0));
			y1yn=y1yn.swagAddPoint(new Point(1,0));
		}
		
		
		x1xn = new Point(rect.left(),Math.ceil(rect.bottom()));
		y1yn = new Point(rect.right(),Math.ceil( rect.bottom()));
		
		while(x1xn.y()<=rect.top()){
			g2.draw(new Line2D.Double(g.transformPoint(x1xn).x(),-g.transformPoint(x1xn).y()+this.getHeight(), g.transformPoint(y1yn).x(),-g.transformPoint(y1yn).y()+this.getHeight()));
			x1xn=x1xn.swagAddPoint(new Point(0,1));
			y1yn=y1yn.swagAddPoint(new Point(0,1));
		}
		
		
		g0.setColor(java.awt.Color.white);
		
		Point xa = new Point(rect.left(),0);
		Point ya = new Point(rect.right(),0);
		
		
		((Graphics2D) g0).draw(new Line2D.Double(g.transformPoint(xa).x(),-g.transformPoint(xa).y()+this.getHeight(), g.transformPoint(ya).x(),-g.transformPoint(ya).y()+this.getHeight()));
		
		xa = new Point(0, rect.top());
		ya = new Point(0, rect.bottom());
		
		((Graphics2D) g0).draw(new Line2D.Double(g.transformPoint(xa).x(),-g.transformPoint(xa).y()+this.getHeight(), g.transformPoint(ya).x(),-g.transformPoint(ya).y()+this.getHeight()));
		
		
		
		//différente largeur pour un jolie rendus.
		//BasicStroke Bigline = new BasicStroke(4.0f); trop grand.
		BasicStroke Mediumline = new BasicStroke(2.0f);
		BasicStroke line = new BasicStroke(1.0f);
		
		g2.setStroke(line);//changer l'epaisseur a normal.
		 
		
		if(mouse){ //montre qu'on est a l'intérieur de la fenêtre.
			g2.setColor(Color.RED);
			g2.draw(new RoundRectangle2D.Double(0,0,this.getWidth()-1,this.getHeight()-1,10,10));
			g2.drawString("Panneau en cours d'édition", 10, 15);
		}
	
		
		
		
		
		
		g2.setStroke(Mediumline);//changer l'epaisseur a big pour les axes.
		g2.setColor(Color.BLACK); //couleur des fonctions affines.
		
		 for(int i=0; i<builderFlame.transformationCount(); i++)
		 {
			 //cooredonnées de la première lignes
			 double x1=g.transformPoint(builderFlame.affineTransformation(i).transformPoint(new Point(-1,0))).x();
			 double y1= g.transformPoint(builderFlame.affineTransformation(i).transformPoint(new Point(-1,0))).y();
			 
			 double x2=g.transformPoint(builderFlame.affineTransformation(i).transformPoint(new Point(1,0))).x();
			 double y2= g.transformPoint(builderFlame.affineTransformation(i).transformPoint(new Point(1,0))).y();
			 
				 //coordonnées de la deuxième ligne.
			 double x3=g.transformPoint(builderFlame.affineTransformation(i).transformPoint(new Point(0,-1))).x();
			 double y3= g.transformPoint(builderFlame.affineTransformation(i).transformPoint(new Point(0,-1))).y();
			 
			 
			 double x4=g.transformPoint(builderFlame.affineTransformation(i).transformPoint(new Point(0,1))).x();
			 double y4=g.transformPoint(builderFlame.affineTransformation(i).transformPoint(new Point(0,1))).y();
			 
			 
			 
			 if(i==highlightedTransformationIndex){
				 g2.setColor(Color.RED);
				
			 }
			 else
				 g2.setColor(Color.BLACK);
			 
			 if(mouseHover[i])
				 g2.setColor(Color.LIGHT_GRAY);
			 
			 s[i][0]=new Line2D.Double(x1,-y1+this.getHeight(), x2,-y2+this.getHeight()); //la tableau va enregistrer les shapes.
			 s[i][1]=new Line2D.Double(x3,-y3+this.getHeight(), x4,-y4+this.getHeight());//ici idem. la deuxième partie du même groupe shapes.
			 s[i][2]=new RoundRectangle2D.Double(x2-4, -y2+this.getHeight()-4, 8, 8,2,2 );
			 s[i][3]=new RoundRectangle2D.Double(x4-4, -y4+this.getHeight()-4, 8, 8,2,2 );
			 
			
			 g2.draw(s[i][0] );
			 g2.draw(s[i][1] );
			 
			 //dessine les flèches.
			 g2.draw(s[i][2]);
			 
			 g2.draw(s[i][3]);
				
			 
			
			 
		 }
		
	}
}
