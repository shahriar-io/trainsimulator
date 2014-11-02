package jts.ihm.gui.ctrl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import jts.Controleur;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.Circuit;
import jts.moteur.ligne.Ligne;
import jts.moteur.ligne.voie.elements.Arc;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Cubique;
import jts.moteur.ligne.voie.elements.TypeElement;
import jts.moteur.ligne.voie.points.Divergence;
import jts.moteur.ligne.voie.points.PointPassage;
import jts.moteur.train.Train;

/**Ce panel permet de visualiser la situation de la ligne, vue de dessus en 2D.
 * 
 * @author Yannick BISIAUX
 *
 */
@SuppressWarnings("serial")
public class PanelVisuRoute extends JPanel {
	
	private final static int X = 1024;
	private final static int Y = 768;
	private final static Dimension DIMENSION = new Dimension(X, Y);
	
	private static Color BLEU = new Color(0, 0, 255);
	private static Color ROUGE = new Color(255, 0, 0);

	private double xMin = -80;
	private double xMax = 80;
	private double yMin = -60;
	private double yMax = 60;
	
	private int xDecal;
	private int yDecal;
	
	private VisuCtrlMouseListener ml;
	
	private Ligne ligne;
	private RenderingHints rh;
	
	private List<Ellipse2D> ptsExtremites2d;
	private List<Point> ptsExtremites;
	
	public PanelVisuRoute(){
		super();
		this.setLayout(null);
		this.ml = new VisuCtrlMouseListener(this);
		this.addMouseListener(ml);
		this.addMouseWheelListener(ml);
		this.addMouseMotionListener(ml);
		this.rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.ptsExtremites2d = new ArrayList<Ellipse2D>();
		this.ptsExtremites = new ArrayList<Point>();
		
		this.setPreferredSize(DIMENSION);
		this.repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		this.ptsExtremites2d.clear();
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHints(rh);
		if (ligne != null){
			Circuit circuit = ligne.getCircuit();
			for (CourbeElementaire element : circuit.getCourbesElementaires()){
				paintElement(g2d, element);
			}
			for (PointPassage pp : circuit.getPointsPassages()){
				if(pp instanceof Divergence){
					if(((Divergence)pp).isEnDivergence()){
						g2d.setColor(Color.ORANGE);
					} else {
						g2d.setColor(Color.RED);
					}
					g2d.fill(getPoint2D(pp));
				} else if(pp.getElementConnecte() == null){
					g2d.setColor(Color.BLACK);
					Ellipse2D point = getPoint2D(pp);
					this.ptsExtremites2d.add(point);
					g2d.fill(point);
				}
			}
			
			g2d.setColor(Color.BLACK);
			Train trainJ = ligne.getCircuit().getTrainJoueur();
			g2d.fill(getPoint2D(trainJ.getLocomotiveTete().getPosition()));
			
			g2d.setColor(Color.GREEN);
			g2d.fill(getPoint2D(trainJ.getLocomotiveTete().getBogieAvant().getPoint()));
			g2d.fill(getPoint2D(trainJ.getLocomotiveTete().getBogieArriere().getPoint()));
		}
	}
	
	public void paintElement(Graphics2D g2d, CourbeElementaire element){
		g2d.setColor(Color.BLUE);
		double[] pe = getPointD(element.getP1());
		double[] ps = getPointD(element.getP2());

		g2d.setColor(Color.BLUE);
		if(element.getType().equals(TypeElement.ARC)){
			Arc arc = (Arc)element;
			double[] pCentre = getPointD(arc.getCentre());
			double[] ratios = getRatios();
			Arc2D.Double arc2d = new Arc2D.Double(
					pCentre[0] - arc.getRayon()*ratios[0],
					pCentre[1] - arc.getRayon()*ratios[1],
					2*arc.getRayon()*ratios[0],
					2*arc.getRayon()*ratios[1],
					90 - arc.getAngleOrigine()*180/Math.PI, -arc.getOuverture()*180/Math.PI, Arc2D.OPEN);
			g2d.draw(arc2d);
		} else if(element.getType().equals(TypeElement.CUBIQUE)) {
			Cubique cubique = (Cubique)element;
			Point pt = new Point();
			cubique.recupererPoint(pt, 0.3);
			double[] pi1 = getPointD(pt);
			cubique.recupererPoint(pt, 0.7);
			double[] pi2 = getPointD(pt);
			g2d.draw(new Line2D.Double(pe[0], pe[1], pi1[0], pi1[1]));
			g2d.draw(new Line2D.Double(pi1[0], pi1[1], pi2[0], pi2[1]));
			g2d.draw(new Line2D.Double(pi2[0], pi2[1], ps[0], ps[1]));
		} else {
			g2d.draw(new Line2D.Double(pe[0], pe[1], ps[0], ps[1]));
		}
	}
	
	public void paintFrontiere(Graphics2D g2d, List<Point> frontiere){
		g2d.setColor(Color.GRAY);
		if (frontiere.size()>2){
			int[] p1 = getPoint(frontiere.get(0));
			int[] p2 = getPoint(frontiere.get(1));
			
			for (int i=0; i<frontiere.size()-1; i++){
				p1 = getPoint(frontiere.get(i));
				p2 = getPoint(frontiere.get(i+1));
				g2d.drawLine(p1[0], p1[1], p2[0], p2[1]);
			}
			p1 = getPoint(frontiere.get(frontiere.size()-1));
			p2 = getPoint(frontiere.get(0));
			g2d.drawLine(p1[0], p1[1], p2[0], p2[1]);
		}
	}
	
	private int[] getPoint(Point p){
		int[] point = new int[2];
		point[0] = (int)((p.getX() - xMin)*X/(xMax - xMin)) + xDecal;
		point[1] = (int)(Y-(p.getY() - yMin)*Y/(yMax - yMin)) + yDecal;
		return point;
	}
	
	private double[] getPointD(Point p){
		double[] point = new double[2];
		point[0] = ((p.getX() - xMin)*X/(xMax - xMin)) + xDecal;
		point[1] = (Y-(p.getY() - yMin)*Y/(yMax - yMin)) + yDecal;
		return point;
	}
	
	private Ellipse2D getPoint2D(Point p){
		Ellipse2D point = new Ellipse2D.Double(
				((p.getX() - xMin)*X/(xMax - xMin)) + xDecal - 3,
				(Y-(p.getY() - yMin)*Y/(yMax - yMin)) + yDecal - 3,
				7,
				7);
		return point;
	}
	
	private double[] getRatios(){
		double[] ratios = new double[2];
		ratios[0] = X/(xMax - xMin);
		ratios[1] = Y/(yMax - yMin);
		return ratios;
	}
	
	public void setLigne(Ligne ligne){
		this.ligne = ligne;
		this.ptsExtremites.clear();
		for(PointPassage pp : ligne.getCircuit().getPointsPassages()){
			if(pp.getElementConnecte() == null){
				this.ptsExtremites.add(pp);
			}
		}
	}
	
	public void clicGauche(int x, int y){
		for(int i=0; i<ptsExtremites2d.size(); i++){
			Ellipse2D pt2d = ptsExtremites2d.get(i);
			if(pt2d.contains(x, y)){
				Point p = this.ptsExtremites.get(i);
				Controleur.LOG.debug("Clic gauche " + p);
			}
		}
	}
	
	protected void appliquerFacteur(double facteur){
		double xCentre = (xMin + xMax)/2;
		double yCentre = (yMin + yMax)/2;
		
		xMax = (xMax - xCentre)*facteur + xCentre;
		xMin = (xMin - xCentre)*facteur + xCentre;
		yMax = (yMax - yCentre)*facteur + yCentre;
		yMin = (yMin - yCentre)*facteur + yCentre;
	}
	
	protected void appliquerDecalage(int x, int y){
		xDecal = x;
		yDecal = y;
	}
	
	protected void appliquerChangementPDV(){
		double dx = (double)xDecal*((xMax - xMin)/X);
		double dy = (double)yDecal*((yMax - yMin)/Y);
		
		xMax -= dx;
		xMin -= dx;
		yMax += dy;
		yMin += dy;
		
		xDecal = 0;
		yDecal = 0;
	}
}
