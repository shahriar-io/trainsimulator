package jts.moteur.ligne;

import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.Arc;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Segment;
import jts.moteur.ligne.voie.points.Divergence;
import jts.moteur.ligne.voie.points.PointExtremite;

/**Cette classe représente le circuit ferroviaire d'une ligne.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Circuit {

	private List<PointExtremite> pointsPassages;
	private List<CourbeElementaire> courbesElementaires;
	private List<Divergence> aiguillages;
	
	public Circuit(){
		this.pointsPassages = new ArrayList<PointExtremite>();
		this.courbesElementaires = new ArrayList<CourbeElementaire>();
		this.aiguillages = new ArrayList<Divergence>();
	}
	
	public void addPointPassage(PointExtremite pointExtremite){
		pointsPassages.add(pointExtremite);
		if(pointExtremite instanceof Divergence){
			aiguillages.add((Divergence)pointExtremite);
		}
	}
	
	public void addSegment(int point1, int point2){
		PointExtremite p1 = pointsPassages.get(point1);
		PointExtremite p2 = pointsPassages.get(point2);
		double theta = Math.asin((p2.getZ() - p1.getZ())/p1.getDistance(p2));
		CourbeElementaire segment = new Segment(p1, p2, theta);
		segment.calculerLongueur();
		courbesElementaires.add(segment);
		p1.setElement(segment);
		p2.setElement(segment);
	}
	
	public void addArc(int point1, int point2, double theta, Point centre, double rayon, double angleOrigine, double ouverture){
		PointExtremite p1 = pointsPassages.get(point1);
		PointExtremite p2 = pointsPassages.get(point2);
		
		//TODO : Doit-on déduire le theta comme pour le segment ?
		CourbeElementaire arc = new Arc(p1, p2, theta, centre, rayon, angleOrigine, ouverture);
		arc.calculerLongueur();
		courbesElementaires.add(arc);
		p1.setElement(arc);
		p2.setElement(arc);
	}
	
	public List<PointExtremite> getPointspassages(){ return this.pointsPassages; }
	
	public List<CourbeElementaire> getCourbesElementaires(){ return this.courbesElementaires; }
	
	public List<Divergence> getAiguillages(){ return this.aiguillages; }
}
