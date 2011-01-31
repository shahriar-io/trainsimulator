package jts.moteur.ligne.voie.points;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe repr�sente un point particulier sur une voie.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class PointExtremite extends Point {
	
	/**Tout point de passage est reli� � au moins un �l�ment de voie*/
	protected CourbeElementaire elementBase;
	
	/**Inclinaison en roulis de la voie � ce point de passage*/
	protected double phi;
	
	public PointExtremite(){
		super(0, 0, 0);
	}
	
	public PointExtremite(double x, double y, double phi){
		super(x, y);
		this.phi = phi;
	}
	
	/**Permet de r�cup�rer l'�l�ment suivant acc�d� par ce point depuis l'�l�ment courant.
	 * 
	 * @param elementCourant �l�ment sur lequel se trouve le train.
	 * @return <code>Transition</code> : le nouvel �l�ment et son sens de parcours.
	 */
	public abstract Transition getNextElement(CourbeElementaire elementCourant);
	
	public CourbeElementaire getElementBase(){ return this.elementBase; }

	public boolean setElement(CourbeElementaire element){
		boolean set = true;
		if(this.elementBase == null){
			this.elementBase = element;
		} else {
			set = false;
		}
		return set;
	}
	
	public double getPhi(){ return this.phi; }
	
	public void setPhi(double phi){ this.phi = phi; }
}
