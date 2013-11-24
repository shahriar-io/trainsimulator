package jts.ihm.gui.ctrl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.JPanel;

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
	
	//private JLabel plus;
	//private JLabel moins;
	
	private Ligne ligne;
	private RenderingHints rh;
	
	public PanelVisuRoute(){
		super();
		this.setLayout(null);
		this.ml = new VisuCtrlMouseListener(this);
		this.addMouseListener(ml);
		this.addMouseWheelListener(ml);
		this.addMouseMotionListener(ml);
		this.rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.setPreferredSize(DIMENSION);
		this.repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHints(rh);
		if (ligne != null){
			Circuit circuit = ligne.getCircuit();
			for (CourbeElementaire element : circuit.getCourbesElementaires()){
				paintElement(g2d, element);
			}
			for (PointPassage pp : circuit.getPointsPassages()){
				//int[] point = getPoint(pp);
				//Point2D p2d = new Point2D.Double(x, y);
				if(pp instanceof Divergence){
					if(((Divergence)pp).isEnDivergence()){
						g2d.setColor(Color.ORANGE);
					} else {
						g2d.setColor(Color.RED);
					}
					//dessinerPoint(g2d, point[0], point[1]);
					g2d.fill(getPoint2D(pp));
				} else if(pp.getElementConnecte() == null){
					g2d.setColor(Color.BLACK);
					//dessinerPoint(g2d, point[0], point[1]);
					g2d.fill(getPoint2D(pp));
				}
			}
			
			g2d.setColor(Color.BLACK);
			Train trainJ = ligne.getCircuit().getTrainJoueur();
			//int[] pointLoco = getPoint(trainJ.getLocomotiveTete().getPosition());
			//dessinerPoint(g2d, pointLoco[0], pointLoco[1]);
			g2d.fill(getPoint2D(trainJ.getLocomotiveTete().getPosition()));
			
			g2d.setColor(Color.GREEN);
			//int[] pointBAv = getPoint(trainJ.getLocomotiveTete().getBogieAvant().getPoint());
			//dessinerPoint(g2d, pointBAv[0], pointBAv[1]);
			g2d.fill(getPoint2D(trainJ.getLocomotiveTete().getBogieAvant().getPoint()));
			
			//int[] pointBAr = getPoint(trainJ.getLocomotiveTete().getBogieArriere().getPoint());
			//dessinerPoint(g2d, pointBAr[0], pointBAr[1]);
			g2d.fill(getPoint2D(trainJ.getLocomotiveTete().getBogieArriere().getPoint()));
			/*g2d.setColor(Color.BLUE);
			int[] pointProjete = getPoint(voiture.getPositionProjetee());
			dessinerPoint(g2d, pointProjete[0], pointProjete[1]);
			paintFrontiere(g2d, voiture.getCarrosserie());*/
			
			/*for (Voiture voiturePNJ : scene.getVoitures()){
				g2d.setColor(Color.YELLOW);
				int[] pointVoitureAvPNJ = getPoint(voiturePNJ.getPositionAvant());
				dessinerPoint(g2d, pointVoitureAvPNJ[0], pointVoitureAvPNJ[1]);
				paintFrontiere(g2d, voiturePNJ.getCarrosserie());
			}*/
		}
	}
	
	public void paintElement(Graphics2D g2d, CourbeElementaire element){
		g2d.setColor(Color.BLUE);
		double[] pe = getPointD(element.getP1());
		double[] ps = getPointD(element.getP2());
		//GradientPaint rougeBarre = new GradientPaint(pe[0], pe[1], BLEU,  ps[0], ps[1], ROUGE);
		//g2d.setPaint(rougeBarre);
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
			//g2d.drawLine(pe[0], pe[1], pi1[0], pi1[1]);
			g2d.draw(new Line2D.Double(pe[0], pe[1], pi1[0], pi1[1]));
			//g2d.drawLine(pi1[0], pi1[1], pi2[0], pi2[1]);
			g2d.draw(new Line2D.Double(pi1[0], pi1[1], pi2[0], pi2[1]));
			//g2d.drawLine(pi2[0], pi2[1], ps[0], ps[1]);
			g2d.draw(new Line2D.Double(pi2[0], pi2[1], ps[0], ps[1]));
		} else {
			//g2d.drawLine(pe[0], pe[1], ps[0], ps[1]);
			g2d.draw(new Line2D.Double(pe[0], pe[1], ps[0], ps[1]));
		}
	}
	
	public void paintFrontiere(Graphics2D g2d, List<Point> frontiere){
		g2d.setColor(Color.GRAY);
		if (frontiere.size()>2){
			int[] p1 = getPoint(frontiere.get(0));
			int[] p2 = getPoint(frontiere.get(1));
			//int[] p3;
			//g2d.drawLine(p1[0], p1[1], p2[0], p2[1]);
			for (int i=0; i<frontiere.size()-1; i++){
				p1 = getPoint(frontiere.get(i));
				p2 = getPoint(frontiere.get(i+1));
				//p3 = getPoint(frontiere.get(i+2));
				g2d.drawLine(p1[0], p1[1], p2[0], p2[1]);
				//g2d.drawLine(p2[0], p2[1], p3[0], p3[1]);
			}
			p1 = getPoint(frontiere.get(frontiere.size()-1));
			p2 = getPoint(frontiere.get(0));
			g2d.drawLine(p1[0], p1[1], p2[0], p2[1]);
		}
	}
	
	/*private void dessinerPoint(Graphics2D g2d, int x, int y){
		g2d.fillOval(x-3, y-3, 7, 7);
	}*/
	
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
	
	protected void getPoint(Point p, int x, int y){
		//int[] point = new int[2];
		/*p.
		point[0] = (int)((p.x - xMin)*X/(xMax - xMin));
		point[1] = (int)(Y-(p.y - yMin)*Y/(yMax - yMin));*/
		//return point;
	}
	
	public void setLigne(Ligne ligne){
		this.ligne = ligne;
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
