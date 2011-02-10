package jts.ihm.gui.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


import jts.moteur.train.Train;
import jts.util.BasicConvert;

/**Cette classe est la représentation graphique du tableau de bord.
 * 
 * @author Yannick BISIAUX
 *
 */
@SuppressWarnings("serial")
public class PanelTableauBord extends JPanel {

	/*private static final int TABLEAU_WIDTH  = 800;
    private static final int TABLEAU_HEIGHT = 200;*/
    
    private PanelVitesse panelVitesse;
    private PanelCommandeCombinee panelCommande;
    
    //private Train train;
    
    /*private Image imageCV;
    private Image imageCT;
    private RenderingHints rh;
    private BasicStroke formatAiguille;
    private Line2D aiguilleVitesse;
    private Line2D aiguilleTourMin;
    
    private int dimensionCadrans;
    private int xVitesse;
    private int xTour;
    private int bordSuperieurCadrans;
    private int centreXVitesse;
    private int centreXTour;
    private int centreY;
    private int rayon;*/
	
	public PanelTableauBord(){
		super();
	}
	
	public void init(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(null);
		//this.setPreferredSize(new Dimension(TABLEAU_WIDTH, TABLEAU_HEIGHT));
		this.setBackground(new Color(166, 166, 166));
		
		panelVitesse = new PanelVitesse((int)(0.14*width), height/5);
		panelVitesse.setBounds((int)(0.43*width), height/3, (int)(0.14*width), height/5);
		this.add(panelVitesse);
		
		panelCommande = new PanelCommandeCombinee((int)(0.36*width), height/5);
		panelCommande.setBounds((int)(0.32*width), 5*height/6, (int)(int)(0.36*width), height/5);
		this.add(panelCommande);
		
		/*try {
			imageCV = ImageIO.read(new File("images/compteurVitesse_800x600.png"));
		} catch (IOException e) {
			System.out.println("Image compteur vitesse indisponible ou corrompue");
			e.printStackTrace();
		}
		
		try {
			imageCT = ImageIO.read(new File("images/compteTour_800x600.png"));
		} catch (IOException e) {
			System.out.println("Image compte tour indisponible ou corrompue");
			e.printStackTrace();
		}
		
		//Calcul de l'emplacement des éléments
		dimensionCadrans = (19*height)/20;
		xVitesse = width/2 - height/10 - dimensionCadrans;
		xTour = width/2 + height/10;
		bordSuperieurCadrans = height/40;
		centreXVitesse = xVitesse + dimensionCadrans/2;
		centreXTour = xTour + dimensionCadrans/2;
	    centreY = height/2;
		rayon = (int)(height/2.5);
		
		//Préparation des éléments graphiques
		rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		formatAiguille = new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		aiguilleVitesse = new Line2D.Double(centreXVitesse, centreY, centreXVitesse, centreY - rayon);
		aiguilleTourMin = new Line2D.Double(centreXTour, centreY, centreXTour, centreY - rayon);*/
	}
	
	/**Cette méthode permet de faire représenter au tableau de bord l'état actuel du train piloté.
	 * 
	 * @param train
	 */
	public void afficherTrain(Train train){
		//this.train = train;
		this.panelVitesse.setVitesse(BasicConvert.msToKmh(train.getVitesse()));
		this.panelCommande.setCommande(train.getCommandeVolant());
		this.repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		/*double vitesseKmh = 0;
		double nbTour = 0;
		
		if(imageCV != null){
			g2d.drawImage(imageCV, xVitesse, bordSuperieurCadrans, dimensionCadrans, dimensionCadrans, null);
			g2d.drawImage(imageCT, xTour, bordSuperieurCadrans, dimensionCadrans, dimensionCadrans, null);
		}
		
		g2d.setRenderingHints(rh);
		g2d.setColor(Color.WHITE);
		
		double angleAiguille = 0.0036*(vitesseKmh-100)*2*Math.PI;
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(AffineTransform.getRotateInstance(angleAiguille, centreXVitesse, centreY));
		g2d.setStroke(formatAiguille);
		g2d.draw(aiguilleVitesse);
		g2d.setTransform(saveAT);
		g2d.transform(AffineTransform.getRotateInstance(0.00012*(nbTour-3000)*2*Math.PI, centreXTour, centreY));
		g2d.draw(aiguilleTourMin);
		g2d.setTransform(saveAT);*/
	}
}
