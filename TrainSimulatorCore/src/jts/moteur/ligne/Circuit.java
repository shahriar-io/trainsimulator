package jts.moteur.ligne;

import java.util.ArrayList;
import java.util.List;

import jts.moteur.ligne.voie.elements.CourbeElementaire;
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
		courbesElementaires.add(CourbeElementaire.createSegment(pointsPassages.get(point1), pointsPassages.get(point2)));
	}
	
}
