package jts.moteur.ligne.voie.points;

import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe repr�sente un point de passage particulier, quand la voie se s�pare en deux, sur un aiguillage.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Divergence extends PointExtremite {
	
	/**Indique si l'aiguillage va nous d�vier*/
	private boolean aiguillageEnDivergence;
	/**Indique si l'aiguillage peut d�vier � gauche (sinon � droite)*/
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
	
	/**Permet d'ajouter un �l�ment � une divergence. Les �l�ments seront remplis dans l'ordre : element, voieNormale, voieDeviee.
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
		
		//D'abord, on d�termine l'�l�ment suivant.
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
	
	public boolean isTypeGauche(){ return this.typeGauche; }
}
