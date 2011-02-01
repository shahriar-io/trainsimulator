package jts.moteur.ligne.voie.points;

import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe repr�sente un point de passage particulier sur une voie.
 * 
 * @author Yannick BISIAUX
 *
 */
public class PointPassage extends PointExtremite {
	
	/**Tout point de passage peut �tre reli� � un autre �l�ment de voie*/
	protected CourbeElementaire elementConnecte;
	
	public PointPassage(){
		super(0, 0, 0, 0);
	}
	
	public PointPassage(double x, double y, double z, double phi){
		super(x, y, z, phi);
	}

	@Override
	public Transition getNextElement(CourbeElementaire elementCourant) {
		CourbeElementaire next = null;
		boolean sensAller = false;
		
		//D'abord, on d�termine l'�l�ment suivant.
		if(elementCourant.equals(elementBase)){
			next = elementConnecte;
		} else if(elementCourant.equals(elementConnecte)) {
			next = elementBase;
		}
		
		//Ensuite on d�termine le prochain sens de parcours
		if(next != null){
			if (next.getP1().equals(this)){
				sensAller = true;
			} else {
				sensAller = false;
			}
		}
		
		Transition transition = new Transition(sensAller, next);
		return transition;
	}
	
	public CourbeElementaire getElementConnecte(){ return this.elementConnecte; }
	
	/**Permet d'ajouter un �l�ment � une divergence. Les �l�ments seront remplis dans l'ordre : elementBase, elementConnecte.
	 * 
	 * @param element
	 */
	public boolean setElement(CourbeElementaire element){
		boolean set = super.setElement(element);
		if(!set){
			if(this.elementConnecte == null){
				this.elementConnecte = element;
				set = true;
			}
		}
		return set;
	}

}
