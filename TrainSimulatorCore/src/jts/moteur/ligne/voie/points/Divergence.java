package jts.moteur.ligne.voie.points;

import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe représente un point de passage particulier, quand la voie se sépare en deux, sur un aiguillage.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Divergence extends PointExtremite {
	
	/**Indique si l'aiguillage va nous dévier*/
	private boolean aiguillageEnDivergence;
	/**Indique si l'aiguillage peut dévier à gauche (sinon à droite)*/
	private boolean typeGauche;
	
	private CourbeElementaire voieNormale;
	private CourbeElementaire voieDeviee;
	
	public Divergence(boolean typeGauche){
		this(0, 0, 0, typeGauche);
	}
	
	public Divergence(double x, double y, double phi, boolean typeGauche){
		super(x, y, phi);
		this.aiguillageEnDivergence = false;
		this.typeGauche = typeGauche;
	}
	
	/**Permet d'ajouter un élément à une divergence. Les éléments seront remplis dans l'ordre : element, voieNormale, voieDeviee.
	 * 
	 * @param element
	 */
	public void setElement(CourbeElementaire element) {
		if(this.elementBase == null){
			this.elementBase = element;
		} else if(this.voieNormale == null){
			this.voieNormale = element;
		} else if(this.voieDeviee == null){
			this.voieDeviee = element;
		}
		
	}

	/**Permet de changer l'aiguillage de place.
	 * 
	 */
	public void switcher(){
		aiguillageEnDivergence = !aiguillageEnDivergence;
		System.out.println("Switch !");
	}

	public Transition getNextElement(CourbeElementaire elementCourant) {
		CourbeElementaire next = null;
		boolean sensAller = false;
		
		//D'abord, on détermine l'élément suivant.
		if(elementCourant.equals(elementBase)){
			if(aiguillageEnDivergence){
				next = voieDeviee;
			} else {
				next = voieNormale;
			}
		} else if((elementCourant.equals(voieNormale))&&(!aiguillageEnDivergence)) {
			next = elementBase;
		} else if((elementCourant.equals(voieDeviee))&&(aiguillageEnDivergence)) {
			next = elementBase;
		}
		
		//Ensuite on détermine le prochain sens de parcours
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
	
	public boolean isTypeGauche(){ return this.typeGauche; }
}
