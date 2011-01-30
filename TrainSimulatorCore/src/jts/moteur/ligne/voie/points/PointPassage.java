package jts.moteur.ligne.voie.points;

import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.elements.Transition;

/**Cette classe repr�sente un point de passage particulier sur une voie.
 * 
 * @author Yannick BISIAUX
 *
 */
public abstract class PointPassage extends Point {
	
	/**Tout point de passage est reli� � au moins un �l�ment de voie*/
	protected CourbeElementaire elementBase;
	
	/**Tout point de passage peut �tre reli� � un autre �l�ment de voie*/
	protected CourbeElementaire elementConnecte;
	
	/**Inclinaison en roulis de la voie � ce point de passage*/
	protected double phi;
	
	public PointPassage(){
		super(0, 0, 0);
	}
	
	public PointPassage(double x, double y, double phi){
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
	
	public CourbeElementaire getElementConn(){ return this.elementBase; }

	public boolean setElement(CourbeElementaire element){
		boolean set = true;
		if(this.elementBase == null){
			this.elementBase = element;
		} else if(this.elementConnecte == null){
			this.elementConnecte = element;
		} else {
			set = false;
		}
		return set;
	}
	
	public double getPhi(){ return this.phi; }
	
	public void setPhi(double phi){ this.phi = phi; }
}
