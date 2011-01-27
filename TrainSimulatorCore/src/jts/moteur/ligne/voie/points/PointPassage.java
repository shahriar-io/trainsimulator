package jts.moteur.ligne.voie.points;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe représente un point de passage particulier sur une voie.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class PointPassage extends Point {
	
	/**Tout point de passage est relié à au moins un élément de voie*/
	protected CourbeElementaire elementBase;
	
	/**Inclinaison en roulis de la voie à ce point de passage*/
	protected double phi;
	
	public PointPassage(){
		super(0, 0, 0);
	}
	
	public PointPassage(double x, double y, double phi){
		super(x, y);
		this.phi = phi;
	}
	
	/**Permet de récupérer l'élément suivant accédé par ce point depuis l'élément courant.
	 * 
	 * @param elementCourant élément sur lequel se trouve le train.
	 * @return <code>Transition</code> : le nouvel élément et son sens de parcours.
	 */
	public abstract Transition getNextElement(CourbeElementaire elementCourant);
	
	public CourbeElementaire getElement(){ return this.elementBase; }

	public void setElement(CourbeElementaire element){ this.elementBase = element; }
	
	public double getPhi(){ return this.phi; }
	
	public void setPhi(double phi){ this.phi = phi; }
}
