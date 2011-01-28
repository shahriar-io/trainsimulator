package jts.moteur.ligne.voie.points;

import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe repr�sente un point de passage d'une courbe �l�mentaire � une autre.
 * 
 * @author Yannick BISIAUX
 *
 */
public class PointPassage extends PointExtremite {
	
	/**Tout point de passage est connect� � une autre courbe elementaire*/
	protected CourbeElementaire elementConnecte;
	
	public PointPassage(){
		this(0, 0, 0);
	}
	
	public PointPassage(double x, double y, double phi){
		super(x, y, phi);
	}

	public Transition getNextElement(CourbeElementaire elementCourant) {
		CourbeElementaire next = null;
		boolean sensAller = true;
		
		if(elementConnecte != null){
			if(elementCourant == elementConnecte){
				next = elementBase;
			} else {
				next = elementConnecte;
			}
			
			if (next.getP1().equals(this)){
				sensAller = true;
			} else {
				sensAller = false;
			}
		}
		
		Transition transition = new Transition(sensAller, next);
		return transition;
	}
}
