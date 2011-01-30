package jts.moteur.ligne.voie.points;

import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;


/**Cette classe représente un point de passage frontière d'une section.
 * 
 * @author Yannick BISIAUX
 *
 */
public class PointFrontiere extends PointPassage {

	protected PointFrontiere connexion;
	
	public PointFrontiere(){
		super(0, 0, 0);
	}
	
	public PointFrontiere(double x, double y, double phi){
		super(x, y, phi);
	}
	
	public Transition getNextElement(CourbeElementaire elementCourant) {
		CourbeElementaire next = null;
		boolean sensAller = true;
		
		//On ne s'intéresse à l'élément suivant que s'il y a connexion.
		if(connexion != null){
			next = connexion.elementBase;
			//System.out.println("nextlength : " + next.getLongueur());
			if (next.getP1().equals(this)){
				sensAller = true;
			} else {
				sensAller = false;
			}
		}
		
		Transition transition = new Transition(sensAller, next);
		return transition;
	}
	
	public PointFrontiere getConnexion() { return connexion; }

	public void setConnexion(PointFrontiere connexion) { this.connexion = connexion; }
}
