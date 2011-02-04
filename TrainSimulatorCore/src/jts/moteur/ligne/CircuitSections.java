package jts.moteur.ligne;

import java.util.ArrayList;
import java.util.List;

import jts.log.Log;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.moteur.ligne.voie.points.PointExtremite;
import jts.moteur.train.Train;

/**Cette classe représente le circuit ferroviaire d'une ligne sous forme de sections.
 * 
 * @author Yannick BISIAUX
 *
 */
public class CircuitSections {

	private List<Section> sections;
	
	private Train trainJoueur;
	
	public CircuitSections(){
		this.sections = new ArrayList<Section>();
	}
	
	public void init(int numeroSection, double abscisse, boolean sensDirect){
		this.trainJoueur.initTrain(sections.get(numeroSection).getElements().get(1), abscisse, sensDirect);
	}
	
	/**Translate les éléments dans leur position absolue si nécessaire.
	 * 
	 */
	public void rendreAbsolu(){
		for (Section section : sections){
			section.rendreAbsolu();
		}
	}
	
	public boolean essayerLienBidirectionnel(Section s1, Section s2){
		boolean reussi1;
		boolean reussi2;
		reussi1 = essayerLierUnidirectionnel(s1,s2);
		reussi2 = essayerLierUnidirectionnel(s2,s1);
		return reussi1 || reussi2;
	}
	
	private boolean essayerLierUnidirectionnel(Section s1, Section s2){
		boolean reussi = false;
		for (PointExtremite pp1 : s1.getPointsPassages()){
			if(pp1 instanceof PointFrontiere){
				PointFrontiere pf1 = (PointFrontiere)pp1;
				for (PointExtremite pp2 : s2.getPointsPassages()){
					if(pp2 instanceof PointFrontiere){
						PointFrontiere pf2 = (PointFrontiere)pp2;
						if (pf1.getDistance(pf2)<Point.DISTANCE_NULLE){
							if (pf1.getConnexion() == null){
								pf1.setConnexion(pf2);
								reussi = true;
								Log.getInstance().logWarning("Lien établit en " + pf1.getX() + "/" + pf1.getY(), false);
							}
						}
					}
				}
			}
		}
		return reussi;
	}

	public List<Section> getSections() { return sections; }

	public void addSection(Section section) { this.sections.add(section); }
	
	public Train getTrainJoueur() { return trainJoueur; }
	
	public void setTrainJoueur(Train train){ this.trainJoueur = train; }
}
