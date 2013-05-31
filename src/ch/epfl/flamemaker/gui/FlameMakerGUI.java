
package ch.epfl.flamemaker.gui;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Shape;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;


import ch.epfl.flamemaker.color.*;
import ch.epfl.flamemaker.flame.FlameTransformation;
import ch.epfl.flamemaker.flame.Variation;
import ch.epfl.flamemaker.geometry2d.AffineTransformation;
import ch.epfl.flamemaker.geometry2d.Point;
import ch.epfl.flamemaker.geometry2d.Rectangle;


/**
 * 
 * @author younes
 *interface graphique
 */
public class FlameMakerGUI{
	
	private ObservableFlameBuilder BuilderFlame;
	private Color FractalBackground=Color.BLACK;
	private Palette FractalPalette;
	private Rectangle fractalFrame;
	private int fractalDensity;
	
	private int selectedTransIndex;
	private HashSet<Observer> listObs = new HashSet<Observer>();
	
	//bonnus (export d'image)
	int largs;
	int hauts;
	int denss;
	
	//bonus (affichage de matrice)
	double paramA;
	double paramB;
	double paramC;
	double paramD;
	double paramE;
	double paramF;
	
	//bonus (mouvement du plan)
	static Point depart=null; //intialisé quand on presse
	static Point depart1=null; //intialisé quand on presse(mouvement du plan en live)
	static Point arrive=null; //intialisé quand on reste appuyé (mouvement du plan en live) et quand on relache pour l image finale
	static Point differenceFract=null; //intialisé
	static Point differenceTrans=null; //intialisé
	
	//bonus changement de palette
	int paletteIndex=0;
	int RandomValue=3;
	
	//bonus, correction gamma.
	static double gammaValue=1;//default
	
	FlamebuilderPreview swagFractal;
	
	 private final Executor scheduler =Executors.newScheduledThreadPool(1); //20threads max^^
	 
	 static int runningThreads=0;
	
	
	
	/** change l'index de la transformation selectionnee / notifie les observateurs
	 *@param Index le nouvel index
	 *@see FlameMakerGUI#notifyObserver()
	 */
	public void setSelectedTransformationIndex(int Index){
		selectedTransIndex=Index;
		notifyObserver();
	}
	
	/** getter de l'index de la trans selectionnée
	 *@return l'index de la transformation selectionnee
	 *@see FlameMakerGUI#notifyObserver()
	 */
	public int getSelectedTransformationIndex(){
		return selectedTransIndex;
	}
	
	
	/** ajoute un observateur
	 * 
	 *@param o l'observateur a ajouter
	 *@see FlameMakerGUI#listObs
	 */
	public void addObserver(Observer o){
		listObs.add(o);
	}
	
	/** supprime un observateur définie en argument
	 *@param o l'observateur a supprimer
	 *@see FlameMakerGUI#listObs
	 */
	public void removeObserver(Observer o){
		listObs.remove(o);
	}
	
	/** dit a l observeur qu'il faut faire une mise à jour-swag héhé
	 *@see FlameMakerGUI#Observer
	 */
	public void notifyObserver(){
		for(Observer o : listObs){
			o.update();
		}
	}
	
	/** l'interface des strategie
	 */
	public interface Strategy{
		
		/** execute la stategie avec l'arg
		 */
		public void execute(double a);
	}
	
	/** l'interface des observateurs
	 */
	public interface Observer{
		
		/** methode de mise à  jour
		 */
		public void update();
	}
	
	/**
	 * message de warning quand l'image a été sauvé dans un fichier avec succés.
	 */
	static public void ImageSavedWarning(){
		//Boîte du message d'information
		JOptionPane.showMessageDialog(null, "L'image a été calculée et enregistrée", "Fractale Flame", JOptionPane.INFORMATION_MESSAGE);
		   
	}
	
	/**genere l'interface graphique , initialisé avec une fractale predefinie, shark par défaut.
	 */
	@SuppressWarnings("unchecked")
	public void start(){
		
		
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Windows".equals(info.getName())) { //windows c'est plus jolie...
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		
		AffineTransformation swag1 = new AffineTransformation(0.7124807,-0.4113509,-0.3,0.4113513,0.7124808,-0.7);
		AffineTransformation swag2 = new AffineTransformation(0.3731079,-0.6462417,0.4,0.6462414,0.3731076,0.3);
		AffineTransformation swag3 = new AffineTransformation(0.0842641,-0.314478,-0.1,0.314478,0.0842641,0.3);
		double[] w1={0.5,0,0,0.4,0,0};
		double[] w2={1,0,0.1,0,0,0};
		double[] w3={1,0,0,0,0,0};

		FlameTransformation fl1 = new FlameTransformation(swag1,w1);
		FlameTransformation fl2 = new FlameTransformation(swag2,w2);
		FlameTransformation fl3 = new FlameTransformation(swag3,w3);

		ArrayList<FlameTransformation> flameTransArrayList=new ArrayList<FlameTransformation>();
		flameTransArrayList.add(fl1);
		flameTransArrayList.add(fl2);
		flameTransArrayList.add(fl3);

		ArrayList<Color> colorList = new ArrayList<Color>();
		colorList.add(Color.RED);
		colorList.add(Color.GREEN);
		colorList.add(Color.BLUE);
		FractalPalette = new InterpolatedPalette(colorList);

		BuilderFlame = new ObservableFlameBuilder(flameTransArrayList);
		//création du point de centre
		Point p1= new Point(0.1,0.1);
		//création du rectangle frame (cadre)
		fractalFrame = new Rectangle(p1,3,3);
		fractalDensity=10;
		
		//la fenetre principale
		JFrame principalWindow = new JFrame("Flame Maker");
		principalWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		principalWindow.getContentPane().setLayout(new BorderLayout());
		
		
		JPanel panneauSup = new JPanel();
		panneauSup.setLayout(new GridLayout(1,2));
		
		JPanel panneauTransformations = new JPanel();
		
		
		
		panneauTransformations.setLayout(new BorderLayout());
		
		JPanel panneauFractal = new JPanel();
		panneauFractal.setLayout(new BorderLayout());
		
		
		principalWindow.getContentPane().add(panneauSup, BorderLayout.CENTER);
		panneauSup.add(panneauTransformations);
		panneauSup.add(panneauFractal);
		
		//fenetres de la fractales et des transformations
		panneauTransformations.setBorder(BorderFactory.createTitledBorder("Transformations affines"));
		panneauFractal.setBorder(BorderFactory.createTitledBorder("Fractal"));
		
		
		swagFractal = new FlamebuilderPreview(BuilderFlame, FractalBackground, FractalPalette, fractalFrame, fractalDensity);
		panneauFractal.add(swagFractal, BorderLayout.CENTER);
		
		final AffineTransformationsComponent swagTransformation = new AffineTransformationsComponent(fractalFrame,BuilderFlame);
		panneauTransformations.add(swagTransformation, BorderLayout.CENTER);
		
		
		//les observers.
		addObserver(new Observer(){
			@Override
			public void update(){
				swagTransformation.setHighlightedTransformationIndex(selectedTransIndex);
			}
		});
		BuilderFlame.addObserver(new Observer(){
			@Override
			public void update() {
				
				//mettre a jour matrice et weigths
				
				//mettre a jour le composante affine
				swagTransformation.repaint();
				FlamebuilderPreview.begun=false; //n'est pas en cours seulement si update est rappelé
			   	FlamebuilderPreview.finished=false; //n'est pas en cours seulement si update est rappelé

				
				
			  
			   	final Runnable calculFrac = new Runnable() {
			       public void run() { 
			    	  
			    	   if(runningThreads==0)
			    	   	swagFractal.repaint(); //le message d'attente
			    	   
				   		if(!FlamebuilderPreview.begun)swagFractal.calcFractal(swagFractal.getHeight(),swagFractal.getWidth());
				   		swagFractal.repaint(); //la fractale
				   		
				   		runningThreads++;
			       }
				          
				       	
			     };
			     scheduler.execute(calculFrac);
		
			   	
			    
			}	
		});

		
		principalWindow.getContentPane().add(panneauSup, BorderLayout.CENTER);
		
		//la liste de transformation
		JPanel panneauInferieur = new JPanel();
		panneauInferieur.setLayout(new GridLayout(1,3));
		
		final JList<String> swagList = new JList<String>(new TransformListModel());
		swagList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		swagList.setVisibleRowCount(3);
		swagList.setSelectedIndex(0);
		
		swagList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
					setSelectedTransformationIndex(swagList.getSelectedIndex());
										
			}
		});
		//liste de transformations
		JPanel panneauTransList = new JPanel();
		panneauTransList.setBorder(BorderFactory.createTitledBorder("Liste des transformations"));
		panneauTransList.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(swagList);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(1,2));

		
		
		
		
		
		//un petit onglet pour exporter notre fractale.
		
		JPanel exportPane = new JPanel();
		exportPane.setLayout(new GridLayout(3,4));
		exportPane.setBorder(BorderFactory.createTitledBorder("Export de la Fractale Flame en fichier image"));
		
		//textfield pour changer la taille de l'image avant de l exporter.
		
		final JFormattedTextField WidthValue=new JFormattedTextField((new DecimalFormat("#0.##")));
		int Value=300; //par défaut la multiplication de la taille+densité ==1
		WidthValue.setValue(Value);
		
		WidthValue.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				largs=((int)((Number)WidthValue.getValue()).intValue());
				
			}
		});
		
		final JFormattedTextField HeightValue=new JFormattedTextField((new DecimalFormat("#0.##")));
		int Valueh=300; //par défaut la multiplication de la taille+densité ==1
		HeightValue.setValue(Valueh);
		
		HeightValue.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				hauts=((int)((Number)HeightValue.getValue()).intValue());
				
			}
		});
		
		final JFormattedTextField DensValue=new JFormattedTextField((new DecimalFormat("#0.##")));
		int Valued=10; //par défaut la multiplication de la taille+densité ==1
		DensValue.setValue(Valued);
		
		DensValue.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				denss=((int)((Number)DensValue.getValue()).intValue());
				
			}
		});
		JPanel ChangGValue= new JPanel();
		ChangGValue.setLayout(new GridLayout(1,2));
		ChangGValue.add(WidthValue);
		ChangGValue.add(HeightValue);
		ChangGValue.add(DensValue);
		WidthValue.setBorder(BorderFactory.createTitledBorder("Largeur")); 
		HeightValue.setBorder(BorderFactory.createTitledBorder("Hauteur")); 
		DensValue.setBorder(BorderFactory.createTitledBorder("Densité")); 
		exportPane.add(ChangGValue);
		
		
		
		
		//textfield pour changer le nom du fichier.
		final JFormattedTextField TextValue=new JFormattedTextField();
		TextValue.setText("FractaleSwag"); //nom de l'image par défaut
		TextValue.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				swagFractal.changeFileName(TextValue.getText());
				
			}
		});
	
		JPanel ChangTextValue= new JPanel();
		ChangTextValue.setLayout(new GridLayout(1,2));
		ChangTextValue.add(TextValue);
		
		ChangTextValue.setBorder(BorderFactory.createTitledBorder("Change le nom de l'image")); 
		
		exportPane.add(ChangTextValue);
		
		
		
		//finir les exportations.
		JButton JpegExp= new JButton("Exporter en Jpeg"); //exportantion en Jpeg
		JButton BitmapExp= new JButton("Exporter en Bitmap"); //exportation Tiff
		JButton PngExp= new JButton("Exporter en Png"); //exportation png
		JButton GifExp= new JButton("Exporter en Gif"); //exportation swag
		
		exportPane.add(JpegExp);
		JpegExp.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					swagFractal.saveImage("jpg",largs,hauts,denss);
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
				}
			});
		
		exportPane.add(BitmapExp);
		BitmapExp.addActionListener(new ActionListener(){
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							swagFractal.saveImage("bmp",largs,hauts,denss);
						} catch (IOException e1) {
							e1.printStackTrace();
						}	
						}
					});
		exportPane.add(PngExp);
		PngExp.addActionListener(new ActionListener(){
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							swagFractal.saveImage("png",largs,hauts,denss);
						} catch (IOException e1) {
							e1.printStackTrace();
						}	
						}
					});
		exportPane.add(GifExp);
		GifExp.addActionListener(new ActionListener(){
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						try {
							swagFractal.saveImage("gif",largs,hauts,denss);
						} catch (IOException e1) {
							e1.printStackTrace();
						}	
						}
					});
		
		
		
		
		JPanel PreTrans= new JPanel();
		PreTrans.setLayout(new GridLayout(1,2));
		PreTrans.setBorder(BorderFactory.createTitledBorder("Des paramètres additionnels utiles"));
		
		
		
		
		JPanel leftSide2=new JPanel();
		leftSide2.setBorder(BorderFactory.createTitledBorder("Charger une Palette aléatoire."));
		JButton RandomPal=new JButton("RandomPalette");
		JButton normalPal=new JButton("Palette par défaut");
		
		final JFormattedTextField RanValue=new JFormattedTextField((new DecimalFormat("#0.##")));
		int RanValueed=3; //par défaut la multiplication de la taille+densité ==1
		RanValue.setValue(RanValueed);
		
		
		RanValue.setPreferredSize(new Dimension(60,20));
		RanValue.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				RandomValue=((int)((Number)RanValue.getValue()).intValue());
			}
		});
		
		leftSide2.add(RanValue);
		leftSide2.add(RandomPal);
		leftSide2.add(normalPal);
		
		
		normalPal.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<Color> colorList = new ArrayList<Color>();
				colorList.add(Color.RED);
				colorList.add(Color.GREEN);
				colorList.add(Color.BLUE);
				FractalPalette = new InterpolatedPalette(colorList);
				swagFractal.changePaletteR(FractalPalette);
			}
		});
		
		RandomPal.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FractalPalette=new RandomPalette(RandomValue);
				swagFractal.changePaletteR(FractalPalette);
			}
		});

		
		//slider pour la correction gamma.
		
		JSlider slider3 = new JSlider();
	    slider3.setBorder(BorderFactory.createTitledBorder("Algorithme de correction gamma"));
	    slider3.setMajorTickSpacing(10);
	    slider3.setMinorTickSpacing(1);
	    slider3.setMaximum(15);
	    slider3.setMinimum(0);
	    slider3.setPaintTicks(true);
	    slider3.setPaintLabels(true);
	    Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>(); //création de table pour changer les labels du slider.
	    for(int i=0;i<=30;i++){
	    	float label=(float)((i/15d)*3);
	    	labelTable.put( new Integer( i ), new JLabel(""+label) );
	    }
	    slider3.setLabelTable(labelTable);
	    
	    slider3.addChangeListener( new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				JSlider source = (JSlider)arg0.getSource();
				gammaValue=(source.getValue()/15d)*3;
				swagFractal.updateThis();
			}
	    	
	    });
		
		JPanel leftSide=new JPanel();
		leftSide.setBorder(BorderFactory.createTitledBorder("Paramètres à boutons"));
		leftSide.setLayout(new GridLayout(3,1));
		
		
		leftSide.add(leftSide2);
		leftSide.add(slider3);
		
		JPanel rightSide=new JPanel();
		rightSide.setLayout(new GridLayout(3,1));
		rightSide.setBorder(BorderFactory.createTitledBorder("Paramètres à cocher"));
		
		//----------------------cases de lissages.
		JPanel rightSide2=new JPanel();
		rightSide2.setBorder(BorderFactory.createTitledBorder("Paramètre de Antialiasing"));
		
		ButtonGroup ResGroup1 = new ButtonGroup();
		
		JRadioButton Lissage1=new JRadioButton("Antialiasing x4");
		JRadioButton Lissage2=new JRadioButton("Antialiasing x9");
		JRadioButton Lissage3=new JRadioButton("Antialiasing x16");
		
		ResGroup1.add(Lissage1);
		ResGroup1.add(Lissage2);
		ResGroup1.add(Lissage3);
		
		rightSide2.add(Lissage1);
		rightSide2.add(Lissage2);
		rightSide2.add(Lissage3);
		
		
		Lissage1.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				FlamebuilderPreview.lissage=2;
				swagFractal.updateThis();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				//Auto-generated method stub
				
			}
			
		});
		Lissage2.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				FlamebuilderPreview.lissage=3;
				swagFractal.updateThis();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				//Auto-generated method stub
				
			}
			
		});
		Lissage3.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				FlamebuilderPreview.lissage=4;
				swagFractal.updateThis();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				//Auto-generated method stub
				
			}
			
		});
		
		
		JPanel rightSide1=new JPanel();
		rightSide1.setBorder(BorderFactory.createTitledBorder("Paramètre de résolution (1,10,50)"));
		
		ButtonGroup ResGroup = new ButtonGroup();
		
		JRadioButton RapidEdb=new JRadioButton("Basse résolution");
		JRadioButton RapidEdm=new JRadioButton("Moyenne résolution");
		JRadioButton RapidEdh=new JRadioButton("Haute résolution");
		
		RapidEdb.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				swagFractal.ChangeDens(1);
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				//Auto-generated method stub
				
			}
			
		});
		RapidEdm.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				swagFractal.ChangeDens(10);
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				//Auto-generated method stub
				
			}
			
		});
		RapidEdh.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				swagFractal.ChangeDens(50);
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				//Auto-generated method stub
				
			}
			
		});
		
		
		
		ResGroup.add(RapidEdb);
		ResGroup.add(RapidEdm);
		ResGroup.add(RapidEdh);
		
		rightSide1.add(RapidEdb);
		rightSide1.add(RapidEdm);
		rightSide1.add(RapidEdh);
		
		rightSide.add(rightSide1);
		rightSide.add(rightSide2);
		
		//rajout d'options pour le lissage
		
		
		PreTrans.add(leftSide);
		PreTrans.add(rightSide);
		
		//dernier onglets, juste du texte d'explication pour l'instant. NAAAAAAAAAAANANANAANANANANANANANANANANAANANANANANANAANANANANANAANANA
		JEditorPane Explpane = new JEditorPane();
		Explpane.setBorder(BorderFactory.createTitledBorder("Explication du programme."));
		Explpane.setEditorKit(new HTMLEditorKit());	
		Explpane.setText("<b>Programme créé par Louhichi Younes & Jimi Vaubien</b>, <br><br><i>Amusez vous bien!!" +
				"Ici vous pouvez écrire du texte, et même ...euhhh dessiner des fractales?</i>, <br><br><br><br><u>Swag!</u>");


		//nananaanana revenir ici pour rajouter des onglets!
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Paramètres",panneauInferieur); //fenêtre de modifications principales.
		tabbedPane.addTab("Paramètre prédéfinis",PreTrans);
		tabbedPane.addTab("Exporter l'image", exportPane);
		tabbedPane.addTab("Crédits",Explpane); //les crédits
	
		principalWindow.getContentPane().add(tabbedPane, BorderLayout.PAGE_END);
		panneauInferieur.add(panneauTransList);
		panneauTransList.add(scroll, BorderLayout.CENTER);
		panneauTransList.add(buttonPane, BorderLayout.PAGE_END);
		
		//rajout de la matrice
		
		//liste des argument de la transformation.
		JPanel transformationMatrice=new JPanel();
		transformationMatrice.setBorder(BorderFactory.createTitledBorder("Matrice de la transformation"));
		transformationMatrice.setLayout(new GridLayout(3,3));
		
		
		
		//chaque case de la matrice.
		//case1
		final JFormattedTextField case1=new JFormattedTextField((new DecimalFormat("#0.##############")));
		case1.setBorder(BorderFactory.createTitledBorder("Paramètre 'a'"));
		
		case1.setValue(BuilderFlame.getParam(selectedTransIndex)[0]);
		
		case1.addPropertyChangeListener("value",new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				//if(!evt.getNewValue().equals(BuilderFlame.getParam(selectedTransIndex)[0]))
					BuilderFlame.setParam(swagList.getSelectedIndex(), 0, ((Number)case1.getValue()).doubleValue());
					
			}
		});
		
		//case2
		final JFormattedTextField case2=new JFormattedTextField((new DecimalFormat("#0.##############")));
		case2.setBorder(BorderFactory.createTitledBorder("Paramètre 'b'"));
		
		case2.setValue(BuilderFlame.getParam(selectedTransIndex)[1]);
		
		case2.addPropertyChangeListener("value",new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				//if(!evt.getNewValue().equals(BuilderFlame.getParam(selectedTransIndex)[1]))
					BuilderFlame.setParam(swagList.getSelectedIndex(), 1, ((Number)case2.getValue()).doubleValue());
			}
		});
		
		//case3
		final JFormattedTextField case3=new JFormattedTextField((new DecimalFormat("#0.##############")));
		case3.setBorder(BorderFactory.createTitledBorder("Paramètre 'c'"));
		
		case3.setValue(BuilderFlame.getParam(selectedTransIndex)[2]);
		
		case3.addPropertyChangeListener("value",new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				//if(!evt.getNewValue().equals(BuilderFlame.getParam(selectedTransIndex)[2]))
					BuilderFlame.setParam(swagList.getSelectedIndex(), 2, ((Number)case3.getValue()).doubleValue());
			}
		});
		
		//case4
		final JFormattedTextField case4=new JFormattedTextField((new DecimalFormat("#0.##############")));
		case4.setBorder(BorderFactory.createTitledBorder("Paramètre 'd'"));
		
		case4.setValue(BuilderFlame.getParam(selectedTransIndex)[3]);
		
		case4.addPropertyChangeListener("value",new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				//if(!evt.getNewValue().equals(BuilderFlame.getParam(selectedTransIndex)[3]))
					BuilderFlame.setParam(swagList.getSelectedIndex(), 3, ((Number)case4.getValue()).doubleValue());
			}
		});
		
		//case5
		final JFormattedTextField case5=new JFormattedTextField((new DecimalFormat("#0.##############")));
		case5.setBorder(BorderFactory.createTitledBorder("Paramètre 'e'"));
		
		case5.setValue(BuilderFlame.getParam(selectedTransIndex)[4]);
		
		case5.addPropertyChangeListener("value", new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				//if(!evt.getNewValue().equals(BuilderFlame.getParam(selectedTransIndex)[4]))
					BuilderFlame.setParam(swagList.getSelectedIndex(), 4, ((Number)case5.getValue()).doubleValue());
			}
		});
		
		//case6
		final JFormattedTextField case6=new JFormattedTextField((new DecimalFormat("#0.##############")));
		case6.setBorder(BorderFactory.createTitledBorder("Paramètre 'f'"));
		
		case6.setValue(BuilderFlame.getParam(selectedTransIndex)[5]);
		//TODO
		case6.addPropertyChangeListener("value",new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				//if(!evt.getNewValue().equals(BuilderFlame.getParam(selectedTransIndex)[5]))
					BuilderFlame.setParam(swagList.getSelectedIndex(), 5, ((Number)case6.getValue()).doubleValue());
			}
		});
		
		
		listObs.add( new Observer(){

			@Override
			public void update() {
				// TODO Auto-generated method stub
				//change la valeur de la matrice de la transformation selectionné
				double[] tab=new double[BuilderFlame.transformationCount()];
				tab=BuilderFlame.getParam(swagList.getSelectedIndex());
				
				case1.setValue(tab[0]);
				case2.setValue(tab[1]);
				case3.setValue(tab[2]);
				case4.setValue(tab[3]);
				case5.setValue(tab[4]);
				case6.setValue(tab[5]);
			}
			
		});
		
		transformationMatrice.add(case1);
		transformationMatrice.add(case2);
		transformationMatrice.add(case3);
		transformationMatrice.add(case4);
		transformationMatrice.add(case5);
		transformationMatrice.add(case6);
		
		
		panneauInferieur.add(transformationMatrice);
		
		
		//onglet pour passer directement vers "turbulence" ou "shark fin"
				JButton TurbButton= new JButton("Configurer la Turbulence");
				TurbButton.addActionListener(new ActionListener(){
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						((TransformListModel) swagList.getModel()).removeAllTransformation();

						AffineTransformation a1 = new AffineTransformation(0.7124807,-0.4113509,-0.3,0.4113513,0.7124808,-0.7);
						AffineTransformation a2 = new AffineTransformation(0.3731079,-0.6462417,0.4,0.6462414,0.3731076,0.3);
						AffineTransformation a3 = new AffineTransformation(0.0842641,-0.314478,-0.1,0.314478,0.0842641,0.3);
						double[] t1={0.5,0,0,0.4,0,0};
						double[] t2={1,0,0.1,0,0,0};
						double[] t3={1,0,0,0,0,0};

						FlameTransformation A1 = new FlameTransformation(a1,t1);
						FlameTransformation A2 = new FlameTransformation(a2,t2);
						FlameTransformation A3 = new FlameTransformation(a3,t3);

						ArrayList<FlameTransformation> flameTransformationList=new ArrayList<FlameTransformation>();
						flameTransformationList.add(A1);
						flameTransformationList.add(A2);
						flameTransformationList.add(A3);
						
						//création du point de centre
						Point p1= new Point(0.1,0.1);
						//création du rectangle frame (cadre)
						fractalFrame = new Rectangle(p1,3,3);
						
						
						
						
						
						//pour que l'image change, et que le builder soit modifié
						((TransformListModel) swagList.getModel()).addAllTransformation(flameTransformationList);
						
						
						//change la valeur de la matrice de la transformation selectionné
						double[] tab=new double[BuilderFlame.transformationCount()];
						tab=BuilderFlame.getParam(swagList.getSelectedIndex());
						
						case1.setValue(tab[0]);
						case2.setValue(tab[1]);
						case3.setValue(tab[2]);
						case4.setValue(tab[3]);
						case5.setValue(tab[4]);
						case6.setValue(tab[5]);
						
						
						//remet le cadre correctement
						swagFractal.defaultFrame(fractalFrame);
						swagTransformation.defaultFrame(fractalFrame);
						}
					});
				
				JButton SharkButton=new JButton("Configurer la Shark Fin");
				SharkButton.addActionListener(new ActionListener(){
							
					@Override
					public void actionPerformed(ActionEvent e) {
						
						((TransformListModel) swagList.getModel()).removeAllTransformation();//efface les vieilles trasnformations.
						
						
						ArrayList<AffineTransformation> affList = new ArrayList<AffineTransformation>(); 
						ArrayList<FlameTransformation> flameList= new ArrayList<FlameTransformation>();
						
						//création de la liste de transformation affines utilisées.
						affList.add( new AffineTransformation(-0.4113504,-0.7124804,-0.4,0.7124795,-0.4113508,0.8));
						affList.add(new AffineTransformation(-0.3957339,0,-1.6,0,-0.3957337,0.2));
						affList.add(new AffineTransformation(0.4810169,0,1,0,0.4810169,0.9));
						
						//création des tableau de pondération
						double[] a={1,0.1,0,0,0,0};
						double[] b={0,0,0,0,0.8,1};
						double[] c={1,0,0,0,0,0};
						
						//création des Flametransformations...
						flameList.add(new FlameTransformation(affList.get(0),a));
						flameList.add(new FlameTransformation(affList.get(1),b));
						flameList.add(new FlameTransformation(affList.get(2),c));
						
						//création du point de centre
						Point p1= new Point(-0.25,0);
								
						//création du rectangle frame (cadre)
						fractalFrame = new Rectangle(p1,5,4);
						
						
						
						//pour que l'image change, et que le builder soit modifié
						((TransformListModel) swagList.getModel()).addAllTransformation(flameList);
						
						
						
						//change la valeur de la matrice de la transformation selectionné
						double[] tab=new double[BuilderFlame.transformationCount()];
						tab=BuilderFlame.getParam(swagList.getSelectedIndex());
						
						case1.setValue(tab[0]);
						case2.setValue(tab[1]);
						case3.setValue(tab[2]);
						case4.setValue(tab[3]);
						case5.setValue(tab[4]);
						case6.setValue(tab[5]);
						
						
						//remet le cadre correctement
						swagFractal.defaultFrame(fractalFrame);
						swagTransformation.defaultFrame(fractalFrame);
						}
					});
				
				

				JPanel leftSide1=new JPanel();
				leftSide1.add(TurbButton);
				leftSide1.add(SharkButton);
				leftSide1.setBorder(BorderFactory.createTitledBorder("Changer entre 'Shark Fin' et 'Turbulence'"));
				leftSide.add(leftSide1);
		
		//création des boutons add et remove
		JButton addTransfoButton = new JButton("add transformation");
		addTransfoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				((TransformListModel) swagList.getModel()).addTransformation();
				swagList.setSelectedIndex(swagList.getModel().getSize()-1);
				}
			});
		JButton removeTransfoButton = new JButton("remove transformation");
		removeTransfoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(swagList.getModel().getSize()!=1){
				int listSize = swagList.getModel().getSize();
				int index = swagList.getSelectedIndex();
				swagList.setSelectedIndex(index==listSize-1 ? index-1 : index+1);
				((TransformListModel) swagList.getModel()).removeTransformation(index);
				}
			}
		});
		
		buttonPane.add(addTransfoButton);
		buttonPane.add(removeTransfoButton);
		
		//panneau qui édite les transformationFlame.
		JPanel panneauEditFractal = new JPanel();
		panneauEditFractal.setBorder(BorderFactory.createTitledBorder("Transformation selectionnée"));
		panneauEditFractal.setLayout(new BoxLayout(panneauEditFractal, BoxLayout.PAGE_AXIS));
		JPanel panneauTransEdit = new JPanel();
		
		GroupLayout gr1 = new GroupLayout(panneauTransEdit);
		panneauTransEdit.setLayout(gr1);
		panneauEditFractal.add(panneauTransEdit);
		panneauInferieur.add(panneauEditFractal);
		
		GroupLayout.Group h1 = gr1.createSequentialGroup();
		GroupLayout.Group v1 = gr1.createSequentialGroup();
		gr1.setHorizontalGroup(h1);
		gr1.setVerticalGroup(v1);
		
		//positionnements
		GroupLayout.Group[][] groupTable1 = new GroupLayout.Group[6][2];
		for(int i = 0;i<6;i++){
			if(i==0)
				groupTable1[i][0]=gr1.createParallelGroup(GroupLayout.Alignment.TRAILING);
			else
				groupTable1[i][0]=gr1.createParallelGroup();
		}
		
		for(int i = 0;i<4;i++)
		groupTable1[i][1]=gr1.createParallelGroup();
		
		for(int i=0;i<6;i++)
			h1.addGroup(groupTable1[i][0]);
			
		for(int i=0;i<4;i++)
			v1.addGroup(groupTable1[i][1]);
			
		final Component[][] componentTable1 = new Component[6][4];
		
		componentTable1[0][0]=new JLabel("Translation");
		componentTable1[0][1]=new JLabel("Rotation"); 
		componentTable1[0][2]=new JLabel("Dilatation"); 
		componentTable1[0][3]=new JLabel("Transvection");
		
		//panneau d'éditions directe (en décimale)
		for(int i =0; i<4; i++){
		componentTable1[1][i]=new JFormattedTextField(new DecimalFormat("#0.##")); 
		((JFormattedTextField) componentTable1[1][i]).setColumns(3);
		((JFormattedTextField) componentTable1[1][i]).setHorizontalAlignment(SwingConstants.RIGHT);
		}
		
		((JFormattedTextField) componentTable1[1][0]).setValue(0.1);
		((JFormattedTextField) componentTable1[1][1]).setValue(15);
		
		((JFormattedTextField) componentTable1[1][2]).setValue(1.05);
		((JFormattedTextField) componentTable1[1][2]).setInputVerifier(new InputVerifier(){//---bloquage de la valeur zero pour le facteur de dilataton---

			@Override
			public boolean verify(JComponent input) {
				try {
					if(((JFormattedTextField) componentTable1[1][2]).getText().equals("0"))
						((JFormattedTextField) componentTable1[1][2]).setText(((JFormattedTextField) componentTable1[1][2]).getFormatter().valueToString(((JFormattedTextField) componentTable1[1][2]).getValue()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
		
		((JFormattedTextField) componentTable1[1][3]).setValue(0.1);
		
		//caractères utilisés pour les éditions "faciles" (rotation, translations, homothéties etc)
		final String buttonName[]={"←","↺","+ ↔","←","→","↻","- ↔","→","↑",null,"+ ↕","↑","↓",null,"- ↕","↓"};
		
		final class Translation implements Strategy{
			private int bbb;
			private boolean ccc;
			
			Translation(int a, boolean b){
				bbb=a;
				ccc=b;
			}
			@Override
			public void execute(double a) {
				if(ccc){
					BuilderFlame.setAffineTransformation(selectedTransIndex, AffineTransformation.newTranslation(bbb*a,0).composeWith(BuilderFlame.affineTransformation(selectedTransIndex)));
					//met a jour le tableau de matrices.
					double[] tab=new double[BuilderFlame.transformationCount()];
					tab=BuilderFlame.getParam(swagList.getSelectedIndex());
					
					case1.setValue(tab[0]);
					case2.setValue(tab[1]);
					case3.setValue(tab[2]);
					case4.setValue(tab[3]);
					case5.setValue(tab[4]);
					case6.setValue(tab[5]);
				}
				else{
					BuilderFlame.setAffineTransformation(selectedTransIndex, AffineTransformation.newTranslation(0,bbb*a).composeWith(BuilderFlame.affineTransformation(selectedTransIndex)));
					//met a jour le tableau de matrices.
					double[] tab=new double[BuilderFlame.transformationCount()];
					tab=BuilderFlame.getParam(swagList.getSelectedIndex());
					
					case1.setValue(tab[0]);
					case2.setValue(tab[1]);
					case3.setValue(tab[2]);
					case4.setValue(tab[3]);
					case5.setValue(tab[4]);
					case6.setValue(tab[5]);
				}
			}
		}
		
		final class Rotation implements Strategy{
			private double swag;
			
			Rotation(double a){
				swag=a;
			}

			@Override
			public void execute(double a) {
				BuilderFlame.setAffineTransformation(selectedTransIndex, BuilderFlame.affineTransformation(selectedTransIndex).composeWith(AffineTransformation.newRotation(swag*a)));				
				//met a jour le tableau de matrices.
				double[] tab=new double[BuilderFlame.transformationCount()];
				tab=BuilderFlame.getParam(swagList.getSelectedIndex());
				
				case1.setValue(tab[0]);
				case2.setValue(tab[1]);
				case3.setValue(tab[2]);
				case4.setValue(tab[3]);
				case5.setValue(tab[4]);
				case6.setValue(tab[5]);
			}
		}
		
		final class Scaling implements Strategy{
			private Boolean hhh, inv;
			
			Scaling(boolean b, boolean c){
				hhh=b;
				inv=c;
			}

			@Override
			public void execute(double a) {
				if(hhh)
					if(!inv)
						BuilderFlame.setAffineTransformation(selectedTransIndex, AffineTransformation.newScaling(a,1).composeWith(BuilderFlame.affineTransformation(selectedTransIndex)));
					else
						BuilderFlame.setAffineTransformation(selectedTransIndex, AffineTransformation.newScaling(1/a,1).composeWith(BuilderFlame.affineTransformation(selectedTransIndex)));
				else
					if(!inv)
						BuilderFlame.setAffineTransformation(selectedTransIndex, AffineTransformation.newScaling(1,a).composeWith(BuilderFlame.affineTransformation(selectedTransIndex)));
					else
						BuilderFlame.setAffineTransformation(selectedTransIndex, AffineTransformation.newScaling(1,1/a).composeWith(BuilderFlame.affineTransformation(selectedTransIndex)));

				//met a jour le tableau de matrices.
				double[] tab=new double[BuilderFlame.transformationCount()];
				tab=BuilderFlame.getParam(swagList.getSelectedIndex());
				
				case1.setValue(tab[0]);
				case2.setValue(tab[1]);
				case3.setValue(tab[2]);
				case4.setValue(tab[3]);
				case5.setValue(tab[4]);
				case6.setValue(tab[5]);

			}
		}
		
		final class Transvection implements Strategy{
			private int swag;
			private boolean ccc;
			
			Transvection(int a, boolean b){
				swag=a;
				ccc=b;
			}

			@Override
			public void execute(double a) {
				if(ccc)
					BuilderFlame.setAffineTransformation(selectedTransIndex, AffineTransformation.newShearX(swag*a).composeWith(BuilderFlame.affineTransformation(selectedTransIndex)));
				else
					BuilderFlame.setAffineTransformation(selectedTransIndex, AffineTransformation.newShearY(swag*a).composeWith(BuilderFlame.affineTransformation(selectedTransIndex)));				
			
				//met a jour le tableau de matrices.
				double[] tab=new double[BuilderFlame.transformationCount()];
				tab=BuilderFlame.getParam(swagList.getSelectedIndex());
				
				case1.setValue(tab[0]);
				case2.setValue(tab[1]);
				case3.setValue(tab[2]);
				case4.setValue(tab[3]);
				case5.setValue(tab[4]);
				case6.setValue(tab[5]);
			}
		}
		//modifications apportées
		final Strategy[] strategyTab[]= new Strategy[4][];
		
		strategyTab[0]=new Strategy[]{new Translation(-1, true),new Rotation(Math.PI/180.0),new Scaling(true, false), new Transvection(-1, true)};
		strategyTab[1]=new Strategy[]{new Translation(1, true),new Rotation(-Math.PI/180.0),new Scaling(true, true), new Transvection(-1, true)};
		strategyTab[2]=new Strategy[]{new Translation(1, false),null,new Scaling(false, false), new Transvection(1, true)};
		strategyTab[3]=new Strategy[]{new Translation(-1, true),null,new Scaling(false, true), new Transvection(1, false)};

		//créations des boutons.
		for(int i=0, a=0; i<4;i++)
			for(int j=0; j<4;j++,a++){
				final int x=i;
				final int y=j;
				componentTable1[i+2][j]=new JButton(buttonName[a]);
				((JButton) componentTable1[i+2][j]).addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						strategyTab[x][y].execute(((Number)((JFormattedTextField)componentTable1[1][y]).getValue()).doubleValue());
					}
				});
			}
		
		//rajout des boutons.
		for(int i=0;i<6;i++){
			
			for(int j=0;j<4;j++){
				if(!((i==4 && j==1)||(i==5 && j==1))){
					groupTable1[i][0].addComponent(componentTable1[i][j]);
					groupTable1[j][1].addComponent(componentTable1[i][j]);
				}
			}
		}
		
		//panneau qui modifie les poids des variations.
		JPanel panneauEditWeight = new JPanel();
		panneauEditFractal.add(new JSeparator());
		GroupLayout gr2 = new GroupLayout(panneauEditWeight);
		gr2.setAutoCreateContainerGaps(true);
		gr2.setAutoCreateGaps(true);
		
		panneauEditWeight.setLayout(gr2);
		panneauEditFractal.add(panneauEditWeight);
		GroupLayout.Group hagar2 = gr2.createSequentialGroup();
		GroupLayout.Group veigar2 = gr2.createSequentialGroup();
		gr2.setHorizontalGroup(hagar2);
		gr2.setVerticalGroup(veigar2);
		((SequentialGroup) veigar2).addPreferredGap(ComponentPlacement.UNRELATED);
		
		GroupLayout.Group[][] grpTab2 = new GroupLayout.Group[6][2];
		for(int i = 0;i<6;i++)
		grpTab2[i][0]=gr2.createParallelGroup(GroupLayout.Alignment.TRAILING);
		
		for(int i = 0;i<2;i++)
		grpTab2[i][1]=gr2.createParallelGroup();
		
		for(int i=0;i<6;i++)
			hagar2.addGroup(grpTab2[i][0]);
			
		for(int i=0;i<2;i++)
			veigar2.addGroup(grpTab2[i][1]);
		
		final Component[][] componentTable2 = new Component[6][2];
		
		for(int i=0, indice=0 ; i<2;i++){
			for(int j=0;j<5; j=j+2, indice++ ){
				componentTable2[j][i]=new JLabel(Variation.ALL_VARIATIONS.get(indice).name());
			}
		}
		//creation des panneau d'éditions directe (par décimale)
		for(int i=0, indice=0 ; i<2;i++){
			for(int j=1;j<6; j=j+2, indice++){
				final int index = indice;
				final int x = i;
				final int y = j;
				
				componentTable2[j][i]=new JFormattedTextField(new DecimalFormat("#0.##")); 
				((JFormattedTextField) componentTable2[j][i]).setColumns(3);
				((JFormattedTextField) componentTable2[j][i]).setHorizontalAlignment(SwingConstants.RIGHT);
				((JFormattedTextField) componentTable2[j][i]).setValue(BuilderFlame.variationWeight(selectedTransIndex, Variation.ALL_VARIATIONS.get(indice)));
				((JFormattedTextField) componentTable2[j][i]).addPropertyChangeListener("value", new PropertyChangeListener(){
//TODO
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if(!evt.getNewValue().equals(BuilderFlame.variationWeight(swagList.getSelectedIndex(), Variation.ALL_VARIATIONS.get(index))))
							BuilderFlame.setVariationWeight(swagList.getSelectedIndex(), Variation.ALL_VARIATIONS.get(index), ((Number)((JFormattedTextField)componentTable2[y][x]).getValue()).doubleValue());
					}
				});
			}
		}
		
		swagList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {

				for(int i=0, indice=0 ; i<2;i++){
					for(int j=1;j<6; j=j+2, indice++){
						((JFormattedTextField) componentTable2[j][i]).setValue(BuilderFlame.variationWeight(swagList.getSelectedIndex(), Variation.ALL_VARIATIONS.get(indice)));
					}
				}
			}
		});
		
		
		for(int i=0;i<6;i++){
			for(int j=0;j<2;j++){
					grpTab2[i][0].addComponent(componentTable2[i][j]);
					grpTab2[j][1].addComponent(componentTable2[i][j]);
				}
		}
		
		//bonus, modification du cadre...+recalcul de la fractale.
		
				panneauTransformations.addMouseMotionListener(new MouseMotionListener(){

					
					public void updateMap(MouseEvent arg0){
						
						arrive=new Point(arg0.getLocationOnScreen().getX(), arg0.getLocationOnScreen().getY());
						differenceTrans=new Point((depart1.x()-arrive.x())/swagTransformation.getWidth(),(arrive.y()-depart1.y())/swagTransformation.getHeight());
						depart1=new Point(arg0.getLocationOnScreen().getX(), arg0.getLocationOnScreen().getY());
						
						swagTransformation.translateFrame(differenceTrans);
					}
					
					@Override
					public void mouseDragged(MouseEvent arg0) {//Ici on fait l'animation du déplacement du plan! (comme google maps.)
						
						updateMap(arg0);
						
					}
	

					@Override
					public void mouseMoved(MouseEvent arg0) {
					java.awt.Point p=arg0.getPoint();
					Shape[][] s=swagTransformation.getShapes();//on retrouve nos "objets" dessinés
					
					for(int i=0; i<s.length; i++){
							if(s[i][0].getBounds2D().createUnion(s[i][1].getBounds2D().createUnion(s[i][2].getBounds2D().createUnion(s[i][3].getBounds2D()))).contains(p.getX(),p.getY())){ //on prend la rectangle le plus petit qui enclose(plus simple a viser avec la sourie...
								
								//une methode qui colore gentillement la shape pour montrer qu'on la "hover"
								swagTransformation.mouseHov(i);
								swagTransformation.repaint();
							}
							else
							
								if(!s[i][0].getBounds2D().createUnion(s[i][1].getBounds2D().createUnion(s[i][2].getBounds2D().createUnion(s[i][3].getBounds2D()))).contains(p.getX(),p.getY()))
								{swagTransformation.NOmouseHov(i);
								swagTransformation.repaint();}
							
						
					}
					}
					
				});
	
				panneauTransformations.addMouseListener(new MouseListener(){
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
						//déjà géré dans le dragged, si on draggue rien, la point d'origine du rectangle frame et
						//de nouveau pris en compte (draggue d'un point au même==click)
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
					
						//dessine un rectangle, pour montrer qu'on est dans la fenêtre
						
						AffineTransformationsComponent.mouse=true;
						swagTransformation.repaint();
						//System.out.println("entrée");
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
					
						//enlève le rectangle 
						
						AffineTransformationsComponent.mouse=false;
						swagTransformation.repaint();
						//System.out.println("sortie");
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						depart=new Point(arg0.getLocationOnScreen().getX(), arg0.getLocationOnScreen().getY());
						depart1=new Point(arg0.getLocationOnScreen().getX(), arg0.getLocationOnScreen().getY());
						
						//System.out.println("pressé");
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						arrive=new Point(arg0.getLocationOnScreen().getX(), arg0.getLocationOnScreen().getY());
						differenceFract=new Point((depart.x()-arrive.x())/swagFractal.getWidth(),(arrive.y()-depart.y())/swagFractal.getHeight());

						swagFractal.translateFrame(differenceFract);
						
						//System.out.println("relaché");
					}
					
					
				});
				
				panneauTransformations.addMouseWheelListener(new MouseWheelListener(){ //ici on zoome sur le cadre, pour agrandir l'image.


					@Override
					public void mouseWheelMoved(MouseWheelEvent arg0) {
					
						int notche=arg0.getWheelRotation();
						
						if(notche>0){ //quand on dézoome
							//swagFractal.zoomFrame(1+0.1*notches);
							swagTransformation.zoomFrame(1+0.1);
							swagFractal.zoomFrame(1+0.1);
							
						}
						else{ //quand on zoome
							//swagFractal.zoomFrame(1-0.1*notches);
							swagTransformation.zoomFrame(1-0.1);
							swagFractal.zoomFrame(1-0.1);
							
						}
						
						
						
						
					}
				
				});
							
		//swagFractal.repaint();
		principalWindow.pack();
		principalWindow.setVisible(true);

	}
	
	/**
	 * 
	 * ajouter et supprimer des transformations
	 * faire la liste du nombre d éléments
	 * 
	 * on redefinie les methodes heritees de AbstractListModel 
	 */
	@SuppressWarnings({ "serial", "rawtypes" })
	public class TransformListModel extends AbstractListModel{
		
		@Override
		public int getSize() {
			return BuilderFlame.transformationCount();
		}

		public void addAllTransformation(ArrayList<FlameTransformation> flameList) {
			BuilderFlame.addAllTransformation(flameList);
			
			//fireIntervalAdded(this, getSize()-1, getSize()-1); provoque une erreure, je ne sais pas pourquoi.
		}

		public void removeAllTransformation() {
			BuilderFlame.removeAllTransformation();	
		}

		@Override
		public Object getElementAt(int index) {
			return "Transformation N°"+(index+1);

		}
		/** ajoute une transformation neutre Ã  la liste de transformation de la fractale
		 *@see FlameMakerGUI#BuilderFlame
		 */
		public void addTransformation(){
			BuilderFlame.addTransformation(new FlameTransformation(AffineTransformation.IDENTITY, new double[]{1,0,0,0,0,0}));
			fireIntervalAdded(this, getSize()-1, getSize()-1);
		}
		
		/** supprime la transformation Ã  l'index donnee de la liste de transformation de la fractale
		 * @param index l'index de la transformation a supprimer
		 *@see FlameMakerGUI#BuilderFlame
		 */
		public void removeTransformation(int index){
			BuilderFlame.removeTransformation(index);
			fireIntervalRemoved(this, index , index);
		}
	}	
}
