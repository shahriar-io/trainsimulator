package jts.moteur.train;

import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Position;
import jts.moteur.ligne.signalisation.Panneau;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;
import jts.moteur.ligne.voie.points.Divergence;
import jts.moteur.ligne.voie.points.PointExtremite;

/**Cette classe représente le chemin sur un circuit ferroviaire que va suivre un train.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Chemin {

	private List<CourbeElementaire> elements;
	private List<Boolean> sensDirects;
	private List<Divergence> divergences;
	private List<Panneau> panneaux;
	
	public Chemin(){
		this.elements = new ArrayList<CourbeElementaire>();
		this.sensDirects = new ArrayList<Boolean>();
		this.divergences = new ArrayList<Divergence>();
		this.panneaux = new ArrayList<Panneau>();
	}
	
	public void recalculerChemin(Position position){
		boolean sensDirect = position.isSensDirect();
		PointExtremite pivot;
		
		elements.clear();
		sensDirects.clear();
		divergences.clear();
		panneaux.clear();
		CourbeElementaire element = position.getElement();
		double longueurTotale = 0;
		while(element != null && longueurTotale<10000){
			longueurTotale += element.getLongueur();
			elements.add(element);
			sensDirects.add(sensDirect);
			if(sensDirect){
				pivot = element.getP2();
			} else {
				pivot = element.getP1();
			}
			if(pivot instanceof Divergence){
				Divergence divergence = (Divergence)pivot;
				divergences.add(divergence);
			}
			if(pivot instanceof Panneau){
				Panneau panneau = (Panneau)pivot;
				panneaux.add(panneau);
			}
			Transition transition;
			transition = pivot.getNextElement(element, false);
			sensDirect = transition.isSensDirect();
			element = transition.getElement();
		}
		System.out.println("Nb éléments dans chemin : " + this.elements.size());
		System.out.println("Nb divergences dans chemin : " + this.divergences.size());
	}
	
	public List<CourbeElementaire> getElements(){ return this.elements; }
	
	public List<Boolean> getSensDirects(){ return this.sensDirects; }
	
	public List<Divergence> getDivergences(){ return this.divergences; }
}
